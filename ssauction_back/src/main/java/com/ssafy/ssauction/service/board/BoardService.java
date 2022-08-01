package com.ssafy.ssauction.service.board;

import com.ssafy.ssauction.domain.board.Board;
import com.ssafy.ssauction.domain.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Page<Board> boardList(PageRequest pageRequest) {
        return boardRepository.findAll(pageRequest);
    }
}
