package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.board.Board;
import com.ssafy.ssauction.service.board.BoardService;
import com.ssafy.ssauction.web.dto.board.BoardDtoReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> boardList(@RequestParam("page") int page,
                                                         @RequestParam("size") int size) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("boardNo").descending());
        resultMap.put("list", boardService.boardList(pageRequest));
        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/detail")
    public ResponseEntity<Map<String, Object>> boardDetail(@RequestParam("boardNo") long boardNo) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        Board board = boardService.boardDetail(boardNo);

        if(board == null) {
            resultMap.put("message", "해당 글을 찾을 수 없습니다.");
            status = HttpStatus.NOT_FOUND;
        } else {
            resultMap.put("board", board);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> boardRegister(@RequestBody BoardDtoReq boardDtoReq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        Board board = boardService.boardRegister(boardDtoReq);

        if(board == null) {
            resultMap.put("message", "글 등록에 실패하였습니다.");
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> boardModify(@RequestBody BoardDtoReq boardDtoReq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        if(boardService.boardModify(boardDtoReq)) {
            status = HttpStatus.OK;
        } else {
            resultMap.put("message", "존재하지 않는 게시글 입니다");
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> boardDelete(@RequestParam long boardNo) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        if(boardService.boardDelete(boardNo)) {
            status = HttpStatus.OK;
        } else {
            resultMap.put("message", "존재하지 않는 게시글 입니다");
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(resultMap, status);
    }
}
