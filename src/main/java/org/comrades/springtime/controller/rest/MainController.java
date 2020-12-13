package org.comrades.springtime.controller.rest;


import org.comrades.springtime.module.Dot;
import org.comrades.springtime.module.User;
import org.comrades.springtime.module.requested.DotDto;
import org.comrades.springtime.servise.DotService;
import org.comrades.springtime.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/main/app/**")
public class MainController {

    private final DotService dotService;
    private final UserService userService;

    @Autowired
    public MainController(DotService dotService, UserService userService) {
        this.dotService = dotService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody DotDto dotDto) {
        Map<Object, Object> response = new HashMap<>();

        Dot dot = new Dot();

        dot.setX(dotDto.getX());
        dot.setY(dotDto.getY());
        dot.setR(dotDto.getR());

        try {
            dotService.validate(dot);
        }catch (NumberFormatException | NullPointerException ex) {
            response.put("perpose", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
        }

        User user = userService.getCurrentUser();
        dot.setUser(user);

        dotService.checkDots(dot);
        dotService.saveDot(dot);

        dot.setUser(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(dot);
    }

    @PostMapping("/clear")
    public ResponseEntity clear() {
        User user = userService.getCurrentUser();
        dotService.clearByUser(user);

        return ResponseEntity.ok("");
    }

    @PostMapping("/dots/all")
    public ResponseEntity getEveryUserDot() {
        User user = userService.getCurrentUser();
        List<Dot> dotList = dotService.getDotsByUser(user);

        for(Dot dot : dotList) {
            dot.setUser(null);
        }
        return ResponseEntity.ok(dotList);
    }
}
