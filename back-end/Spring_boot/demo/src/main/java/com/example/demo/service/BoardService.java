package com.example.demo.service;


import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public List<Board> indices() {
        return boardRepository.findAll();
    }
    public Board show(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Board create(BoardDto dto) {
        Board board = dto.toEntity();
        if (board.getId() != null) {
            return null;
        }
        return boardRepository.save(board);
    }

    public Board update(Long id, BoardDto dto) {
        // 1: DTO -> 엔티티
        Board board = dto.toEntity();
        log.info("id: {}, board: {}", id, board.toString());
        // 2: 타겟 조회
        Board target = boardRepository.findById(id).orElse(null);
        // 3: 잘못된 요청 처리
        if (target == null || id != board.getId()) {
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, board: {}", id, board.toString());
            return null;
        }
        // 4: 업데이트
        target.patch(board);
        Board updated = boardRepository.save(target);
        return updated;
    }

    public Board delete(Long id) {
        // 대상 찾기
        Board target = boardRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if (target == null) {
            return null;
        }
        // 대상 삭제
        boardRepository.delete(target);
        return target;
    }

    public List<Board> createArticles(List<BoardDto> dtos) {
        // dto 묶음을 entity 묶음으로 변환
        List<Board> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        // entity 묶음을 DB로 저장
        articleList.stream()
                .forEach(article -> boardRepository.save(article));
        // 강제 예외 발생
        boardRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );
        // 결과값 반환
        return articleList;
    }
}
