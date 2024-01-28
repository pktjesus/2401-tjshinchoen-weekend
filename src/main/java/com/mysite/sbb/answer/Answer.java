package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     // 답변 번호
    private String content; // 답변 내용
    private LocalDateTime createDate; // 답변 생성날짜
    @ManyToOne
    private Question question;
}
