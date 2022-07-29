package com.ssafy.ssauction.auth;

import com.ssafy.ssauction.service.users.UsersService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Autowired
    private UsersService usersService;

    //secretkey
    private Key accessKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private Key refreshKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //유효시간 30분
    private long tokenValidTime = 1000 * 60 * 30L;

    private final UserDetailsService userDetailsService;

    //secretkey Base64 인코딩
    String encodedAccessString = Base64.getEncoder().encodeToString(accessKey.getEncoded());
    String encodedRefreshString = Base64.getEncoder().encodeToString(refreshKey.getEncoded());

    //key 변환
    //base64를 byte[]로 변환
    byte[] decodedAccessByte = Base64.getDecoder().decode(encodedAccessString.getBytes());
    byte[] decodedRefreshByte = Base64.getDecoder().decode(encodedRefreshString.getBytes());

    SecretKey keyForAccessToken = Keys.hmacShaKeyFor(decodedAccessByte);
    SecretKey keyForRefreshToken = Keys.hmacShaKeyFor(decodedRefreshByte);


    //JWT 토큰 생성
    public String createAccessToken(String claimId, String data) {

        return Jwts.builder()
                .claim(claimId, data)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(keyForAccessToken)
                .compact();

    }

    //refreshToken으로 accessToken 재생성
    public String recreateAccessToken(String claimId, String data) {

        return Jwts.builder()
                .claim(claimId, data)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(keyForAccessToken)
                .compact();

    }

    //refresh token 생성 (유효시간 3일)
    public String createRefreshToken(String userEmail) {
        return Jwts.builder()
                .claim("userEmail", userEmail)
                .setSubject(userEmail)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime * 48 * 3))
                .signWith(keyForRefreshToken)
                .compact();
    }

    //토큰에서의 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 회원 정보 추출
    private String getUserEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(keyForAccessToken).build().parseClaimsJws(token).getBody().getSubject();
    }

    //Request의 Header에서 token 값을 가져온다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    //accessToken의 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(keyForAccessToken).build().parseClaimsJws(token);
            System.out.println("1");
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("token expired");
            return false;
        } catch (PrematureJwtException e) {
            System.out.println("premature");
            return false;
        } catch (ClaimJwtException e) {
            System.out.println("claimJwtException");
            return false;
        } catch (MalformedJwtException e) {
            System.out.println("malformed");
            return false;
        } catch (SignatureException e) {
            System.out.println("signature");
            return false;
        } catch (UnsupportedJwtException e) {
            System.out.println("unsupported");
            return false;
        }
    }

    //refreshToken의 유효성 검증 ->유효하다면 새로운 accessToken 생성, 유효하지
    public String validateRefreshToken(String userEmail) {
        String refreshToken = usersService.findByUserEmail(userEmail).getUserRefreshToken();


        System.out.println(refreshToken + " it's refreshToken");

        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(keyForRefreshToken).build().parseClaimsJws(refreshToken);
            System.out.println("validateRefreshToken");

            String newAccessToken = recreateAccessToken(userEmail, claims.getBody().getSubject());
            System.out.println(newAccessToken);
            return recreateAccessToken(userEmail, claims.getBody().getSubject());
        } catch (SignatureException e) {
            return null;
        }
    }

}
