package com.sse.sseexample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequestMapping("/")
@RestController
@CrossOrigin
public class EventController {
    private final ExecutorService nonBlockingService = Executors
            .newCachedThreadPool();

    @GetMapping("/sse-streams")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();
        nonBlockingService.execute(() -> {
            try {
                emitter.send("/sse" + " @ " + new Date());
                Thread.sleep(1000);
                emitter.send("/sse" + " @ " + new Date());
                Thread.sleep(1000);
                emitter.send("/sse" + " @ " + new Date());
                Thread.sleep(1000);
                emitter.send("/sse" + " @ " + new Date());
                // we could send more events
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }
}




