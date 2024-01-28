package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;
	@Test
	void contextLoads() {
		//System.out.println("contextLoads() 실행");

	}

	@Test
	void testJpa() {
		// System.out.println("testJpa() 실행");
		Question q1 = new Question();
		q1.setSubject("sbb가 뭔가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다. 잘 가르켜 주세요");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);	// insert into QUESTION~~~

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);  // 두번째 질문 저장
	}

	@Test
	void testJpaFindAll() {
		// sql -> select * from question;
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 뭔가요?", q.getSubject());
		Question q2 = all.get(1);
		assertEquals("스프링부트 모델 질문입니다.", q2.getSubject());
	}

	@Test
	void testJpaFindById() {
		// sql -> select * from question where id = 1
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 뭔가요?", q.getSubject());
		}

		Optional<Question> oq2 = this.questionRepository.findById(2);
		if (oq2.isPresent()) {
			Question q = oq2.get();
			assertEquals("스프링부트 모델 질문입니다.", q.getSubject());
		}
	}

	@Test
	void testJpaFindBySubject() {
		// sql -> select * from question where subject = 'sbb가 뭔가요?'
		Question q = this.questionRepository.findBySubject("sbb가 뭔가요?");
		assertEquals(1, q.getId());

		// title query 만들어보기
	}
}
