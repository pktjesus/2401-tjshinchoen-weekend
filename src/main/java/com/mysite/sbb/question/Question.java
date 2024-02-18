package com.mysite.sbb.question;

// version 3.0이상
import com.mysite.sbb.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
// version 2.7이하 -> import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Question {
    @Id     // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     // 질문번호
    @Column(length = 200)
    private String subject; // 제목
    @Column(columnDefinition = "TEXT")
    private String content; // 내용
    private LocalDateTime createDate;   // 작성날짜

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // -> default fetch-type lazy -> 지연 로딩
    private List<Answer> answerList = new ArrayList<>();
}
