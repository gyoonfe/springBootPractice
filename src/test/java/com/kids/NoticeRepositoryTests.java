package com.kids;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.test.context.TestPropertySource;

import com.kids.entity.Notice;
import com.kids.entity.QNotice;
import com.kids.repository.NoticeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
public class NoticeRepositoryTests {

	@Autowired
	NoticeRepository noticeRepository;
	
	@PersistenceContext
	EntityManager en; // 동적 쿼리를 생성하기 위해서 EntityManager를 주입함.
	
	@Test
	public void createNotice() {
//		Notice notice = new Notice();
//		notice.setSubject("Test_Title1");
//		notice.setContent("Test_Content1");
//		notice.setHit(0);
		Notice notice = Notice.builder().subject("Test_Title1").content("Test_Content1").hit(0).build();
		
		Notice saveNotice = noticeRepository.save(notice);
		
		System.out.println(saveNotice.toString());
	}
	
	@Test
	public void findNoticeByIdx() {
//		Notice saveNotice = noticeRepository.findNoticeByIdx(1L);
		Notice saveNotice = noticeRepository.findByIdx(1L);
		System.out.println("-----------------------------------------------");
		System.out.println(saveNotice.toString());
	}
	
	@Test
	public void findByIdxGreaterThanAndIdxLessThan() {
		List<Notice> notice_list = noticeRepository.findByIdxGreaterThanAndIdxLessThan(0L, 5L);
		System.out.println("-----------------------------------------------");
		for( Notice notice : notice_list) {
			System.out.println(notice.toString());
		}
	}
	
	@Test
	public void findBySubjectLike() {
		List<Notice> notice_list = noticeRepository.findBySubjectContaining("Title");
		System.out.println("-----------------------------------------------");
		for( Notice notice : notice_list) {
			System.out.println(notice.toString());
		}
	}
	
//	@Test
//	public void findByOrderByIdxDesc() {
//		List<Notice> notice_list = noticeRepository.findByOrderByIdxDesc();
//		System.out.println("-----------------------------------------------");
//		for( Notice notice : notice_list) {
//			System.out.println(notice.toString());
//		}
//	}
	
	@Test
	public void findByMyTitle() {
		List<Notice> notice_list = noticeRepository.findByMyTitle("Title");
		System.out.println("-----------------------------------------------");
		for( Notice notice : notice_list) {
			System.out.println(notice.toString());
		}
	}
	
	// 정적 쿼리 : 어떤 조건 상황에서도 변경되지 않는 쿼리
	// 동적 쿼리 : 조건이나 상황에 따라 변경되는 쿼리
	@Test
	public void queryDSL() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(en); // EntityManager를 주입하여 쿼리문을 동적으로 생성
		QNotice qnotice = QNotice.notice; // Plugin 을 통해서 만들어진 QNotice 객체
		JPAQuery<Notice> query = queryFactory
							     .selectFrom(qnotice)
							     .where(qnotice.subject.like("%" + "Title" + "%"))
							     .orderBy(qnotice.idx.desc());
		// List<Notice> notice_list = query.fetch(); 
		
		// T fetchOne() : 조회 대상이 1건인 경우
		// T fetchFirst() : 조회 대상 중에 1건만 
		// Long fetchCount() : 조회 대상의 개수를 반환
		// QueryResult<T> fetchResults() : 조회한 리스트와 전체 개수를 반환
		
		// 아니면 이렇게 바로 fetch()를 하여 List형태로 반환시킬 수 있음.
		List<Notice> notice_list = queryFactory
							     .selectFrom(qnotice)
							     .where(qnotice.subject.like("%" + "Title" + "%"))
							     .orderBy(qnotice.idx.desc())
							     .fetch();
		for( Notice notice : notice_list ) {
			System.out.println("IDX : " + notice.getIdx());
			System.out.println("SUBJECT : " + notice.getSubject());
		}
	}
	
	@Test // QuerydslPredicateExecutor<Notice> 를 이용하여 검색해보자 !
	public void querydslPredicateExecutor() {
		BooleanBuilder booleanBuilder = new BooleanBuilder(); // 조건에 들어갈 쿼리문을 만들어주는 객체
		QNotice qnotice = QNotice.notice;
		// 조건 추가
		booleanBuilder.and(qnotice.subject.like("%" + "Title" + "%"));
		
		List<Notice> notice_list = (List<Notice>) noticeRepository.findAll(booleanBuilder);
		for( Notice notice : notice_list ) {
			System.out.println("IDX : " + notice.getIdx());
			System.out.println("SUBJECT : " + notice.getSubject());
		}
	}
	
}
