package com.example.demo.repository;

import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
    @Query(value =
            "SELECT * " +
                    "FROM comment " +
                    "WHERE board_id = :boardId",
            nativeQuery = true)
    List<Comment> findByBoardId(Long boardId);
    List<Comment> findByNickname(String nickname);
}
