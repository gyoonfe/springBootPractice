package com.kids.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbk_board")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	@Column(nullable = false)
	private Long idx;
	
	@Column(nullable = false)
	private String subject;
	
	@Column(nullable = false)
	private String content;
	
	private int hit;
	
	private String delyn;
	
	
}
