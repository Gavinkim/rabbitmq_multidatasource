package com.example.queuedemo.service;

import com.example.queuedemo.model.Member;
import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gs on 2017. 8. 11..
 */
@Component
public class MemberMQService {

    public static final Logger log =  LoggerFactory.getLogger(MemberMQService.class);

    private List<Member> memberList = new ArrayList<>();

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry; // 리스너를 컨트롤 할 수 있음.

    @Autowired
    MemberService memberService;

    @Value("${alimtalk.rabbitmq.member.exchange}")
    private String MEMBER_EXCHANGE;
    @Value("${alimtalk.rabbitmq.member.routingkey}")
    private String MEMBER_ROUTING_KEY;


    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    @Autowired
    private MappingJackson2MessageConverter mappingJackson2MessageConverter;


    @Async(value = "threadPoolTaskExecutor")
    @RabbitListener(queues =  "${alimtalk.rabbitmq.member.queue}")
    public void memberReceiveMessage( Member member)throws ShutdownSignalException,InterruptedException,SocketException,ListenerExecutionFailedException {
        try {
            if(memberList.size()>=10){
                rabbitListenerEndpointRegistry.stop();
                memberService.sendNotiToSlack("큐 리스너 정지 ");
                memberService.saveToMysql(memberList);
                memberList.clear();
                rabbitListenerEndpointRegistry.start();
                memberService.sendNotiToSlack("큐 리스너 시작");
            }else{
                memberList.add(member);
            }
        } catch (ListenerExecutionFailedException | ShutdownSignalException e){
            e.printStackTrace();
        }catch ( Exception e){
            e.printStackTrace();
        }
    }

    //save to queue
    public void saveToQueue(List<Member> members){
        try {
            this.rabbitMessagingTemplate.setMessageConverter(this.mappingJackson2MessageConverter);
            for (Member member: members) {
                this.rabbitMessagingTemplate.convertAndSend(MEMBER_EXCHANGE ,MEMBER_ROUTING_KEY, member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
