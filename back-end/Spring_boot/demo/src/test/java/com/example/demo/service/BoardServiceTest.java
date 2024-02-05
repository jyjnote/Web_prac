package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardService boardService;


    @Test
    @Transactional
    void indices() {
        Board a = new Board(1L, "가가가가", "1111");
        Board b = new Board(2L, "나나나나", "2222");
        Board c = new Board(3L, "다다다다", "3333");
        List<Board> expected = new ArrayList<Board>(Arrays.asList(a, b, c));
        // 실제
        List<Board> boards = boardService.indices();
        // 검증
        assertEquals(expected.toString(), boards.toString());
    }

    @Test
    @Transactional
    void create_성공____title과_content만_있는_dto_입력() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        BoardDto dto = new BoardDto(null, title, content);
        Board expected = new Board(4L, title, content);
        // 실제
        Board article = boardService.create(dto);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void create_실패____id가_포함된_dto_입력() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        BoardDto dto = new BoardDto(4L, title, content);
        Board expected = null;
        // 실제
        Board article = boardService.create(dto);
        // 비교
        assertEquals(expected, article);
    }
}