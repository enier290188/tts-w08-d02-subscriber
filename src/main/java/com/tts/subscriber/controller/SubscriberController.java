package com.tts.subscriber.controller;

import com.tts.subscriber.entity.SubscriberEntity;
import com.tts.subscriber.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(value = "/crud/subscriber")
    public String list(Model model) {
        List<SubscriberEntity> subscriberEntities = new ArrayList<>();
        this.subscriberRepository.findAll().forEach(subscriberEntities::add);
        model.addAttribute("subscribers", subscriberEntities);
        return "subscriber/crud/subscriber/list";
    }

    @GetMapping(value = "/crud/subscriber/create")
    public String createShow(Model model) {
        SubscriberEntity subscriberEntity = new SubscriberEntity();
        model.addAttribute("error", false);
        model.addAttribute("subscriber", subscriberEntity);
        return "subscriber/crud/subscriber/create";
    }

    @PostMapping(value = "/crud/subscriber/create")
    public String create(@Valid SubscriberEntity subscriber, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", false);
            model.addAttribute("subscriber", subscriber);
            return "subscriber/crud/subscriber/create";
        }
        try {
            subscriberRepository.save(subscriber);
            return "redirect:/crud/subscriber/";
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Subscriber is null or empty");
            return "subscriber/crud/subscriber/create";
        }
    }

    @GetMapping(value = "/crud/subscriber/{id}/detail")
    public String detailShow(@PathVariable("id") Long id, Model model) {
        SubscriberEntity subscriberEntity = subscriberRepository.findById(id).orElse(null);
        if (subscriberEntity == null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "We cannot find a Subscriber with ID: " + id);
        } else {
            model.addAttribute("error", false);
            model.addAttribute("subscriber", subscriberEntity);
        }
        return "subscriber/crud/subscriber/detail";
    }

    @GetMapping(value = "/crud/subscriber/{id}/update")
    public String updateShow(@PathVariable("id") Long id, Model model) {
        SubscriberEntity subscriberEntity = subscriberRepository.findById(id).orElse(null);
        if (subscriberEntity == null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "We cannot find a Subscriber with ID: " + id);
        } else {
            model.addAttribute("error", false);
            model.addAttribute("subscriber", subscriberEntity);
        }
        return "subscriber/crud/subscriber/update";
    }

    @PostMapping(value = "/crud/subscriber/{id}/update")
    public String update(@PathVariable("id") Long id, @Valid SubscriberEntity subscriber, BindingResult result, Model model) {
        SubscriberEntity subscriberEntity = subscriberRepository.findById(id).orElse(null);
        if (subscriberEntity == null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "We cannot find a Subscriber with ID: " + id);
        } else {
            model.addAttribute("error", false);
            subscriber.setId(subscriberEntity.getId());
            subscriber.setSignedUp(subscriberEntity.getSignedUp());
            if (!result.hasErrors()) {
                subscriberRepository.save(subscriber);
            }
            model.addAttribute("subscriber", subscriber);
        }
        return "subscriber/crud/subscriber/update";
    }

    @GetMapping(value = "/crud/subscriber/{id}/delete")
    public String deleteShow(@PathVariable("id") Long id, Model model) {
        SubscriberEntity subscriberEntity = subscriberRepository.findById(id).orElse(null);
        if (subscriberEntity == null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "We cannot find a Subscriber with ID: " + id);
        } else {
            model.addAttribute("error", false);
            model.addAttribute("subscriber", subscriberEntity);
        }
        return "subscriber/crud/subscriber/delete";
    }

    @PostMapping(value = "/crud/subscriber/{id}/delete")
    public String delete(@PathVariable("id") Long id, Model model) {
        SubscriberEntity subscriberEntity = subscriberRepository.findById(id).orElse(null);
        if (subscriberEntity == null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "We cannot find a Subscriber with ID: " + id);
            return "subscriber/crud/subscriber/delete";
        } else {
            subscriberRepository.delete(subscriberEntity);
            return "redirect:/crud/subscriber/";
        }
    }
}
