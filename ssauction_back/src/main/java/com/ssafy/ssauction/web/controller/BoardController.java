package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> boardList(@RequestParam("page") int page,
                                                         @RequestParam("size") int size) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("boardNo").descending());
        resultMap.put("list", boardService.boardList(pageRequest));
        return new ResponseEntity<>(resultMap, status);
    }
}
