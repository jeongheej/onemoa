package com.web.contoller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.dto.QuestionReplyDTO;
import com.web.entity.QuestionReply;
import com.web.repository.QuestionReplyRepository;
import com.web.service.QuestionReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/reply")
@Log4j2
@RequiredArgsConstructor
public class QuestionReplyController {
	
	private final QuestionReplyService replyService;
	private final QuestionReplyRepository questionReplyRepository;
	
	@ResponseBody
    @PostMapping("/post")
    public HashMap<String, Object> commentPost(@RequestParam Long qno,
                              @RequestParam String content,
                              Principal principal) throws Exception {
        Long depth = 0L;
      
       Long qgroup = questionReplyRepository.lastqrno()+1;
        return replyService.commentPost(qno, content, depth, qgroup, principal.getName());
    }
	
	@ResponseBody
    @PostMapping("/recomment/post")
    public HashMap<String, Object> recommentPost(@RequestParam Long qno,
                              @RequestParam String content,@RequestParam Long qgroup,
                              Principal principal) throws Exception {
        Long depth = 1L;
      
    
        return replyService.commentPost(qno, content, depth, qgroup, principal.getName());
    }
	
	@PutMapping("/{qrno}")
	public ResponseEntity<String> modify(@RequestBody QuestionReplyDTO replyDTO) {
		replyService.modify(replyDTO);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	
		
	@DeleteMapping("/{qrno}")
	public ResponseEntity<String> remove(@PathVariable("qrno") Long qrno) {
		replyService.remove(qrno);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	@DeleteMapping("/rereply/{qrno}")
	public ResponseEntity<String> reremove(@PathVariable("qrno") Long qrno) {
		replyService.recoremove(qrno);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	@GetMapping(value = "/question/{qno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuestionReplyDTO>> getListByBoard(@PathVariable("qno") Long qno) {
		return new ResponseEntity<>(replyService.getList(qno), HttpStatus.OK);
		

	}
}
