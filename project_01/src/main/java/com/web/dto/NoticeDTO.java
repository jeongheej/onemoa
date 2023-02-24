package com.web.dto;

import java.time.LocalDateTime;

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
public class NoticeDTO {
	
	private Long nno;
	private String title;
	private String content;
	private Integer rvisitcount = 0;
	private String writerName;
	private String writerEmail;
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	
}
