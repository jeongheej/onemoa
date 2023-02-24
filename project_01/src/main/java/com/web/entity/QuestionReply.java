package com.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questionreply")
@ToString(exclude = "writer")
@Builder
public class QuestionReply extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long qrno;
	
	@Column(nullable = false)
	private String content;
	
	
	@Column(nullable = false)
	private Long depth;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qno")
	private Question qno;
	
	
	private Long qgroup;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email")
	private Member writer;
	
	public void changeContent(String content) {
		this.content = content;
	}

}
