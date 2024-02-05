package com.example.demo.service;


import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public List<CommentDto> comments(Long boardId) {
        // 조회: 댓글 목록
//        List<Comment> comments = commentRepository.findByBoardId(articleId);
//        // 변환: 엔티티 -> DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for (int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
//        // 반환
//        return dtos;
        return commentRepository.findByBoardId(boardId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());

    }

    @Transactional
    public CommentDto create(Long boardId, CommentDto dto) {
//        // 게시글 조회 및 예외 발생
//        Board article = boardRepository.findById(boardId)
//                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니."));
//        // 댓글 엔티티 생성
//        Comment comment = Comment.createComment(dto, article);
//        // 댓글 엔티티를 DB로 저장
//        Comment created = commentRepository.save(comment);
//        // DTO로 변경하여 반환
//        return CommentDto.createCommentDto(created);
        log.info("입력값 => {}", boardId);
        log.info("입력값 => {}", dto);
        // 게시글 조회(혹은 예외 발생)
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니."));
        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, board);
        // 댓글 엔티티를 DB로 저장
        Comment created = commentRepository.save(comment);
        // DTO로 변경하여 반환
//        return CommentDto.createCommentDto(created);
        CommentDto createdDto = CommentDto.createCommentDto(created);
        log.info("반환값 => {}", createdDto);
        return createdDto;
        //return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));
        // 댓글 수정
        target.patch(dto);
        // DB로 갱신
        Comment updated = commentRepository.save(target);
        // 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 댓글 조회(및 예외 발생)
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));
        // 댓글 삭제
        commentRepository.delete(target);
        // 삭제 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target);
    }
}
