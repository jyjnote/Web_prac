package com.example.demo.api;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    // GET
    @GetMapping("/api/boards")
    public List<Board> index() {
        //return boardRepository.findAll();
        return boardService.indices();
    }
    @GetMapping("/api/board/{id}")
    public Board show(@PathVariable Long id) {
        //return boardRepository.findById(id).orElse(null);
        return boardService.show(id);
    }
    @PostMapping("/api/board/create")
    public ResponseEntity<Board> create(@RequestBody BoardDto dto) {
       // Board board = dto.toEntity();
       // return boardRepository.save(board);

        Board created = boardService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    // PATCH
    @PatchMapping("/api/board/{id}")
    public ResponseEntity<Board> update(@PathVariable Long id,
                                        @RequestBody BoardDto dto) {
        // 1: DTO -> 엔티티
//        Board board = dto.toEntity();
//        log.info("id: {}, board: {}", id, board.toString());
//        // 2: 타겟 조회
//        Board target = boardRepository.findById(id).orElse(null);
//        // 3: 잘못된 요청 처리
//        if (target == null || id != board.getId()) {
//            // 400, 잘못된 요청 응답!
//            log.info("잘못된 요청! id: {}, board: {}", id, board.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        // 4: 업데이트 및 정상 응답(200)
//        target.patch(board);
//        Board updated = boardRepository.save(target);
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
        Board updated = boardService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    // DELETE
    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<Board> delete(@PathVariable Long id) {
//        // 대상 찾기
//        Board target = boardRepository.findById(id).orElse(null);
//        // 잘못된 요청 처리
//        if (target == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        // 대상 삭제
//        boardRepository.delete(target);
//        return ResponseEntity.status(HttpStatus.OK).build();
        Board deleted = boardService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Board>> transactionTest(@RequestBody List<BoardDto> dtos) {
        List<Board> createdList = boardService.createArticles(dtos);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
