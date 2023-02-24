package com.web.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.web.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
	
	
	private Long qno;
	private String title;
	private String content;
	private String qpassword;
	private String secretYn;
	private String writer;
	private Integer qvisitcount = 0;
	private String writerEmail;
	private String writerName;
	private String role;
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	private Integer replyCount;
	
}
