package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.DispatcherServlet;

@RestController
public class RefreshController {

  @Autowired
  private RefreshEndpoint refreshEndpoint;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @ResponseBody
  @GetMapping("/refresh")
  public ResponseEntity<?> refresh() {
    return new ResponseEntity<String>(refreshEndpoint.refresh().toString(), HttpStatus.OK);
  }

  @ResponseBody
  @GetMapping("/refresh2")
  public ResponseEntity<?> refresh2() {

    ApplicationContext appContext = (ApplicationContext) RequestContextHolder.currentRequestAttributes()
        .getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);

    RetrievePropertyController get = appContext.getBean(RetrievePropertyController.class);

    System.out.println("Before: " + get.getMessage());

    applicationEventPublisher.publishEvent(new RefreshEvent(appContext, "Refresh", "Refresh triggered"));

    System.out.println("After: " + get.getMessage());

    return new ResponseEntity<String>("refeshed", HttpStatus.OK);
  }

}
