package com.ssafy.ssauction.service.board;

import com.ssafy.ssauction.domain.board.Board;
import com.ssafy.ssauction.web.dto.board.BoardDtoReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

public interface BoardService {

    Page<Board> boardList(PageRequest pageRequest, String searchType, String search);

    Board boardDetail(long boardNo);

    Board boardRegister(BoardDtoReq boardDtoReq);

    boolean boardModify(BoardDtoReq boardDtoReq);

    boolean boardDelete(long boardNo);
}
