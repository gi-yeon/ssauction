package com.ssafy.ssauction.service.board;


import com.ssafy.ssauction.domain.board.Board;
import com.ssafy.ssauction.domain.board.BoardRepository;
import com.ssafy.ssauction.web.dto.board.BoardDtoReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BoardServiceImpl implements BoardService {


    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Page<Board> boardList(PageRequest pageRequest) {
        return boardRepository.findAll(pageRequest);
    }

    @Override
    public Board boardDetail(long boardNo) {
        return boardRepository.findById(boardNo).orElse(null);
    }

    @Override
    public Board boardRegister(BoardDtoReq boardDtoReq) {
        Board board = new Board();
        board.setBoardTitle(boardDtoReq.getBoardTitle());
        board.setBoardContent(boardDtoReq.getBoardContent());
        board.setBoardType(boardDtoReq.getBoardType());
        board.setBoardReply(0);

        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        board.setBoardRegDate(sDate.format(new Date()));

        board.setUserNo(boardDtoReq.getUserNo());
        board.setUserNickname(boardDtoReq.getUserNickname());

        return boardRepository.save(board);
    }

    @Override
    public boolean boardModify(BoardDtoReq boardDtoReq) {
        Board board = boardRepository.findById(boardDtoReq.getBoardNo()).orElse(null);

        if(board == null)
            return false;
        else {
            board.setBoardTitle(boardDtoReq.getBoardTitle() + "(수정됨)");
            board.setBoardContent(boardDtoReq.getBoardContent());
            boardRepository.save(board);
            return true;
        }
    }

    @Override
    public boolean boardDelete(long boardNo) {
        Board board = boardRepository.findById(boardNo).orElse(null);

        if(board == null)
            return false;
        else {
            boardRepository.delete(board);
            return true;
        }
    }
}
