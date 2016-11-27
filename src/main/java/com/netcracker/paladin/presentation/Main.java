package com.netcracker.paladin.presentation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ivan on 27.11.16.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        SwingEmailSender swingEmailSender = context.getBean(SwingEmailSender.class);
        swingEmailSender.launch();
    }
}
