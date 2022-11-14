package com.kids.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.kids.dto.NoticeFormDto;
import com.kids.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>
										, QuerydslPredicateExecutor<Notice>
										, NoticeRepositoryCustom {
	// CRUD : insert(save), update(save), select(findAll), delete(delete)

	
	/***** 1. Naming Rule 이용하는 문법 : find + (엔티티명) + By + 변수명 *****/
	
	// SELECT * FROM tbk_board WHERE idx = 1
	Notice findNoticeByIdx(Long idx); 
	Notice findByIdx(Long idx); // 엔티티명 생략 가능 !
	
	// SELECT * FROM tbk_board WHERE idx > 0 AND idx < 5
	List<Notice> findByIdxGreaterThanAndIdxLessThan(Long idx1, Long idx2);	
	
	// SELECT * FROM tbk_board WHERE LIKE subject "% %"
	List<Notice> findBySubjectContaining(String subject); // Like = 정확히 일치하는 것만 검색, Containing = 포함하는 것들 모두 검색
	
	// SELECT * FROM tbk_board ORDER BY idx DESC
	Iterable<NoticeFormDto> findByOrderByIdxDesc();

	
	
	/***** 2. @Query 문법 : SQL과 유사한 JPQL 이라는 객체지향 쿼리 언어를 통해 복잡한 쿼리도 처리가 가능하다 *****/

    @Query("SELECT n FROM Notice n WHERE n.subject LIKE %:myTitle% ORDER BY n.idx DESC") // nativeQuery = false
    // @Query(value = "SELECT * FROM tbk_board n WHERE n.subject LIKE %:myTitle% ORDER BY n.idx DESC", nativeQuery = true)
    List<Notice> findByMyTitle(@Param("myTitle") String myTitle);
	
    @Modifying
	@Query("UPDATE Notice n SET n.hit = n.hit+1 WHERE n.idx = :idx")
	void updateHit(@Param("idx") Long idx);
    
	
}
