package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

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

	@Test
	void testJpaFindBySubjectAndContent() {
		Question q = this.questionRepository.findBySubjectAndContent(
				"sbb가 뭔가요?","sbb에 대해서 알고 싶습니다. 잘 가르켜 주세요");
		assertEquals(1, q.getId());
	}

	@Test
	void testJpaFindByIdLessThan() {
		// query method 공식문서 -> https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
		// < LessThan
		List<Question> qList = this.questionRepository.findByIdLessThan(2);
		// <= LessThanEqual
		//List<Question> qList = this.questionRepository.findByIdLessThanEqual(2);
		// >= GreaterThanEqual
		assertEquals(1, qList.size());
	}

	@Test
	void testJpaQuestionUpdate() {
		// 1. update할 row데이터를 가져옵니다.
		Optional<Question> oq = this.questionRepository.findById(1);
		// 2. 데이터 존재 여부 확인
		assertTrue(oq.isPresent()); // row데이터 존재
		// 3. 실제 수정할 객체를 가져와서 값을 수정
		Question q = oq.get();
		q.setSubject("sbb가 무엇인가요???????");
		q.setContent("아... sbb 어렵다.");
		// 4. 수정한 값을 DB에 적용
		this.questionRepository.save(q);	// insert가 아닌 update가 실행
	}

	@Test
	void testJpaQuestionDelete() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Test
	void testJpaAnswerInsert() {
		// 1. 질문 데이터 가져오기
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		// 2. 답변 데이터 저장하기
		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setCreateDate(LocalDateTime.now());
		a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		this.answerRepository.save(a);
	}

	@Test
	void testJpaAnswerSelect() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	@Test
	@Transactional(readOnly = true)
	void testJpaAnswerSelect2() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
}
