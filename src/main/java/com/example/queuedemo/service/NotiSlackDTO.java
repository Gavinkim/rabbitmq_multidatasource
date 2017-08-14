package com.example.queuedemo.service;

import lombok.Data;

/**
 * Created by gavin on 2017. 7. 3..
 * 슬랙 발송 객체
 */
@Data
public class NotiSlackDTO {
    String channel;
    String username;
    String text;
}
