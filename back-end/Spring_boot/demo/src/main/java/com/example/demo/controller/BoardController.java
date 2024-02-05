package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.service.CommentService;


import java.util.List;

@Controller
@Slf4j
public class BoardController {

    @Autowired // 스프링 부트가 미리 생성해놓은 리파지터리 객체를 가져옴(DI)
    private BoardRepository boardRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/board/new")
    public String newArticleForm() {
        return "board/new";
    }


    @PostMapping("/board/create")
    public String createArticle(BoardDto dto) {
        log.info(dto.toString());

        Board board = dto.toEntity();
        log.info(board.toString());

        Board saved = boardRepository.save(board);
        log.info(saved.toString());

        return "redirect:/board/" + saved.getId();
    }

    @GetMapping("/board/{id}") // 해당 URL요청을 처리 선언
    public String show(@PathVariable Long id,
                       Model model) { // URL에서 id를 변수로 가져옴
        log.info("id = " + id);
        Board boardEntity = boardRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);

        // 2: 가져온 데이터를 모델에 등록!
        model.addAttribute("article", boardEntity);
        model.addAttribute("commentDtos", commentsDtos);
        return "board/show";
    }
    @GetMapping("/boards")
    public String boards(Model model) {
        // 1: 모든 Article을 가져온다!
        List<Board> boardEntity = boardRepository.findAll();
        // 2: 가져온 Article 묶음을 뷰로 전달!
        model.addAttribute("boardList", boardEntity);
        // 3: 뷰 페이지를 설정!
        return "board/indices";
    }
    @GetMapping("/board/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Board boardEntity = boardRepository.findById(id).orElse(null);
        // 모델에 데이터 등록
        model.addAttribute("board", boardEntity);
        // 뷰 페이지 설정
        return "board/edit";
    }
    @PostMapping("/board/update")
    public String update(BoardDto dto) {
        log.info(dto.toString());

        // 1: DTO를 엔티티로 변환
        Board boardEntity = dto.toEntity();

        log.info(boardEntity.toString());
        // 2: 엔티티를 DB로 저장
        // 2-1: DB에서 기존 데이터를 가져옴
        Board target = boardRepository.findById(boardEntity.getId())
                .orElse(null);
        // 2-2: 기존 데이터가 있다면, 값을 갱신
        if (target != null) {
            boardRepository.save(boardEntity);
        }
        // 3: 수정 결과 페이지로 리다이렉트
        return "redirect:/board/" + boardEntity.getId();
    }
    @GetMapping("/board/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");
        // 1: 삭제 대상을 가져옴
        Board target = boardRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2: 대상을 삭제
        if (target != null) {
            boardRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }
        // 3: 결과 페이지로 리다이렉트
        return "redirect:/boards";
    }
}
