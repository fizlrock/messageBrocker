package com.fizlrock.denly.services;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HiberService {

  @PostConstruct
  void afterConstruct() {
    log.warn("Доброе утро!");

  }

}
