package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor        // 매개변수가 있는 생성자 추가
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model
            , @RequestParam(value = "page", defaultValue = "0") int page
            , @RequestParam(value = "kw", defaultValue = "") String kw
    ) {
        Page<Question> paging = questionService.getList(page, kw);
        //model.addAttribute("questionList", questionList);   // thylemeaf에 데이터를 전달하는 방법
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);

        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);   // thylemeaf에 데이터를 전달하는 방법

        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);

        return "redirect:/question/list";   // 질문 저장 후에 질문 목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModfiy(@PathVariable("id") Integer id, QuestionForm questionForm, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        // 로그인한 유저의 작성한 유저에 한해서만 수정가능 하도록 방어코드 작성
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());

        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(
            @PathVariable("id") Integer id,
            @Valid QuestionForm questionForm,
            BindingResult bindingResult,
            Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        Question question = this.questionService.getQuestion(id);
        // 로그인한 유저의 작성한 유저에 한해서만 수정가능 하도록 방어코드 작성
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        // 실제 게시판 수정로직
        this.questionService.modify(question, questionForm);

        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(@PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        // 로그인한 유저의 작성한 유저에 한해서만 수정가능 하도록 방어코드 작성
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);

        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}
