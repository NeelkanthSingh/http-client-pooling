package org.atomic.http_client_pooling.controller;

import org.atomic.http_client_pooling.model.SmsResponse;
import org.atomic.http_client_pooling.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

  @Autowired
  private SmsService smsService;

  @GetMapping("/")
  public ResponseEntity<SmsResponse> getAllSms() throws Exception {
    return smsService.getAllSms();
  }
}
