package com.ssafy.ssauction.web.dto.board;

import com.ssafy.ssauction.domain.board.BoardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDtoRes {

    private long boardNo;
    private String boardTitle;
    private String boardContent;
    private BoardType boardType;
    private String boardReply;
    private String boardRegDate;

    private long userNo;
    private String userNickname;
}
