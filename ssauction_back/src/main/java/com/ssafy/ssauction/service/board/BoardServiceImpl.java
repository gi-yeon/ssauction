package com.ssafy.ssauction.service.board;


import com.ssafy.ssauction.domain.board.Board;
import com.ssafy.ssauction.domain.board.BoardRepository;
import com.ssafy.ssauction.domain.comments.CommentsRepository;
import com.ssafy.ssauction.web.dto.board.BoardDtoReq;
import com.ssafy.ssauction.web.dto.board.BoardDtoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BoardServiceImpl implements BoardService {


    private final BoardRepository boardRepository;

    private final CommentsRepository commentsRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, CommentsRepository commentsRepository) {
        this.boardRepository = boardRepository;
        this.commentsRepository = commentsRepository;
    }

    @Override
    public Page<Board> boardList(PageRequest pageRequest, String searchType, String search) {
        if(searchType == null)
            return boardRepository.findAll(pageRequest);
        else if("title".equals(searchType))
            return boardRepository.findByBoardTitleContaining(pageRequest, search);
        else
            return boardRepository.findByUserNickname(pageRequest, search);
    }

    @Override
    public BoardDtoRes boardDetail(long boardNo) {
        BoardDtoRes boardDtoRes = new BoardDtoRes();
        Board board = boardRepository.findById(boardNo).orElse(null);

        if(board != null) {
            boardDtoRes.setBoardNo(board.getBoardNo());
            boardDtoRes.setBoardTitle(board.getBoardTitle());
            boardDtoRes.setBoardContent(board.getBoardContent());
            boardDtoRes.setBoardType(board.getBoardType());
            boardDtoRes.setBoardRegDate(board.getBoardRegDate());
            boardDtoRes.setBoardReply(board.getBoardReply());
            boardDtoRes.setUserNo(board.getUserNo());
            boardDtoRes.setUserNickname(board.getUserNickname());
            boardDtoRes.setComments(commentsRepository.findByBoardNo(boardNo));
        }
        return boardDtoRes;
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
