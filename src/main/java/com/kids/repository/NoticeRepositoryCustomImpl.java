package com.kids.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.kids.dto.NoticeSearchFormDto;
import com.kids.dto.QNoticeSearchFormDto;
import com.kids.entity.QNotice;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;


public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public NoticeRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em); // 동적쿼리를 생성하기 위한 클래스
	}
	
	@Override
	public Page<NoticeSearchFormDto> getSubNoticePage(Pageable pageable) {
		
		QNotice qnotice = QNotice.notice; 
		
		List<NoticeSearchFormDto> content = queryFactory
				 					  .select(
				 							  new QNoticeSearchFormDto(
			 									  	  qnotice.idx,
			 									  	  qnotice.subject,
			 									  	  qnotice.content,
			 									  	  qnotice.regdtm
				 							  )
				 					  ).from(qnotice)
				 					  // .where(searchByLike(NoticeSearchFormDto.getSubject())) // 검색 조건을 여기에 추가하면 된다 !
				 					  .orderBy(qnotice.idx.desc())
				 					  .offset(pageable.getOffset()) // 데이터를 가지고 올 시작 인덱스
				 					  .limit(pageable.getPageSize())
				 					  .fetch(); // 조회 대상 리스트 반환
		
		Long totalCount = queryFactory.select(Wildcard.count)
									  .from(qnotice)
									  .fetchOne(); // 조회 대상이 1건이면 타입 반환
				
		return new PageImpl<>(content, pageable, totalCount);
	}
	
}
