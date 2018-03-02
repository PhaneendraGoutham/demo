package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class RetrievePropertyController {

  public RetrievePropertyController() {
    System.out.println("constructed");
  }
  
  @Autowired
  MyProperties myProperties;

  @Value("${your.message}")
  private String message;

  @ResponseBody
  @GetMapping("/get")
  public ResponseEntity<?> get() {
    return new ResponseEntity<String>(message + " -- " + myProperties.getMessage(), HttpStatus.OK);
  }

  public String getMessage() {
    return message;
  }

}
