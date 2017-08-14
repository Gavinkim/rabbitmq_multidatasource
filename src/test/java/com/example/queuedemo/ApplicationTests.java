package com.example.queuedemo;

import com.example.queuedemo.model.Member;
import com.example.queuedemo.service.MemberMQService;
import com.example.queuedemo.service.MemberService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private MemberMQService memberMQService;

	@Autowired
	private MemberService memberService;

	@Test
	@Ignore
	public void saveToQueueTest() {
		memberMQService.saveToQueue(generateMembers());
	}

	@Test
	@Ignore
	public void saveToDB() throws Exception{
		memberService.saveToMysql(generateMembers());
		memberService.saveToPostgresql(generateMembers());
	}

	private List<Member> generateMembers(){
		List<Member> members = new ArrayList<>();
		String name = "Test_";
		for (int i = 0; i < 13; i++) {
			Member member = new Member();
			member.setName(name+i);
			member.setAge(i);
			members.add(member);
		}
		return members;
	}

	@Test
	@Ignore
	public void sendSlack() {
		memberService.sendNotiToSlack("aasdfa");
	}
}
