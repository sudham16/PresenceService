package com.controller;

import com.service.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PresenceController {

    @Autowired
    public PresenceService presenceService;

    public PresenceController(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @GetMapping(path = "/prometheus",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getQueueContent(@RequestHeader HttpHeaders headers) throws Exception {
        return this.presenceService.getQueueContentFromFile();
    }

    @GetMapping(path = "/prometheus/refresh",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String resetWebsocket(@RequestHeader HttpHeaders headers) throws Exception {
        return this.presenceService.refreshQueueContent();
    }
}
