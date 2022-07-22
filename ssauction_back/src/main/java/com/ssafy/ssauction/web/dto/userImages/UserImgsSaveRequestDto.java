package com.ssafy.ssauction.web.dto.userImages;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class UserImgsSaveRequestDto {

    private String userImgName;

    private String userImgUri;

    private Long userNo;

    private Timestamp userImgRegDate;

    @Builder
    public UserImgsSaveRequestDto(String userImgName, String userImgUri, Long userNo) {
        this.userImgName = userImgName;
        this.userImgUri = userImgUri;
        this.userNo = userNo;
        this.userImgRegDate = new Timestamp(System.currentTimeMillis());
    }

    public UserImgs toEntity(){
        return UserImgs.builder()
                .userImgName(userImgName)
                .userImgUri(userImgUri)
                .userImgRegDate(userImgRegDate)
                .userNo(userNo)
                .userImgUpdateDate(null)
                .build();
    }
}
