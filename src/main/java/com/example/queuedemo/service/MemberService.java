package com.example.queuedemo.service;

import com.example.queuedemo.model.Member;
import com.example.queuedemo.repository.mysql.MysqlMemberRepository;
import com.example.queuedemo.repository.postgresql.PostgreMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by gs on 2017. 8. 11..
 */
@Component
public class MemberService {

    private String sendSlackUrl  = "https://hooks.slack.com/services/xxxxxxxxxxx";
    @Autowired
    private PostgreMemberRepository postgreMemberRepository;

    @Autowired
    private MysqlMemberRepository mysqlMemberRepository;

    public void saveToMysql(List<Member> memberList) throws Exception{
        mysqlMemberRepository.save(memberList);
    }

    public void saveToPostgresql(List<Member> memberList) throws Exception{
        postgreMemberRepository.save(memberList);
    }

    public void sendNotiToSlack(String msg) {
        NotiSlackDTO notiDTO = new NotiSlackDTO();
        notiDTO.setChannel("#parcelab-issue");
        notiDTO.setUsername("parcelab-issue");
        notiDTO.setText(msg);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(sendSlackUrl,notiDTO,String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }
}
