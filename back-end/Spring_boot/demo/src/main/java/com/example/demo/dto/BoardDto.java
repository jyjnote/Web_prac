package com.example.demo.dto;

import com.example.demo.entity.Board;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class BoardDto {

    private Long id;
    private String title;
    private String content;

    public Board toEntity() {
        return new Board(id, title, content);
    }
}
