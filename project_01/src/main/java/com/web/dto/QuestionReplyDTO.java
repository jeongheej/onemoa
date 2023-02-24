package com.web.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.web.entity.Member;
import com.web.entity.Question;
import com.web.entity.QuestionReply;

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
public class QuestionReplyDTO {
	
	
	private Long qrno;
	private String content;
	private Long depth;
	private Long qno;
	private Long qgroup;
	private String writerName;
	private String writerEmail;
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	
}
