package com.tts.subscriber.controller;

import com.tts.subscriber.entity.SubscriberEntity;
import com.tts.subscriber.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SubscriberController {
    @Autowired
    private SubscriberRepository subscriberRepository;

    @GetMapping(value = "/")
    public String index(SubscriberEntity subscriberEntity, Model model) {
        model.addAttribute("subscriber", subscriberEntity);
        return "subscriber/index";
    }

    @PostMapping(value = "/")
    public String addNewSubscriber(SubscriberEntity subscriberEntity, Model model) {
        subscriberRepository.save(new SubscriberEntity(subscriberEntity.getFirstName(), subscriberEntity.getLastName(), subscriberEntity.getUserName(), subscriberEntity.getSignedUp()));
        model.addAttribute("firstName", subscriberEntity.getFirstName());
        model.addAttribute("lastName", subscriberEntity.getLastName());
        model.addAttribute("userName", subscriberEntity.getUserName());
        return "subscriber/result";
    }
}
