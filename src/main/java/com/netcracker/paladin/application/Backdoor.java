package com.netcracker.paladin.application;

import com.netcracker.paladin.infrastructure.repositories.PublicKeyEntryRepository;
import com.netcracker.paladin.infrastructure.repositories.PublicKeyEntryRepositoryImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ivan on 27.11.16.
 */
public class Backdoor {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        PublicKeyEntryRepository repository = context.getBean(PublicKeyEntryRepositoryImpl.class);
        System.out.println(repository.findByEmail("azanoviv02@gmail.com").getOwnPublicKey());
    }
}
