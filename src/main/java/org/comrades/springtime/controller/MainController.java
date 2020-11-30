package org.comrades.springtime.controller;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/**")
public class MainController {
//    @Autowired
//    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "login";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
//        Iterable<Message> messages = messageRepo.findAll();

//        model.put("messages", messages);

        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
//        Message message = new Message(text, tag);

//        messageRepo.save(message);

//        Iterable<Message> messages = messageRepo.findAll();

//        model.put("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
//        Iterable<Message> messages;

//        if (filter != null && !filter.isEmpty()) {
//            messages = messageRepo.findByTag(filter);
//        } else {
//            messages = messageRepo.findAll();
//        }

//        model.put("messages", messages);

        return "main";
    }
}
