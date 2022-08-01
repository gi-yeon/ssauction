package com.ssafy.ssauction.domain.board;

import com.ssafy.ssauction.domain.users.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    @Column(name = "board_title", nullable = false)
    private String boardTitle;
    @Column(name = "board_content", nullable = false)
    private String boardContent;
    @Column(name = "board_regdate", nullable = false)
    private String boardRegDate;
    @Column(name = "board_type", nullable = false)
    private BoardType boardType;
    @Column(name = "board_reply", nullable = false)
    private int boardReply;

    @Column(name = "user_no", nullable = false)
    private Long userNo;
}
