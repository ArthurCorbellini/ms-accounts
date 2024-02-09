package com.corbellini.accounts.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ResponseErrorDto {

  private String apiPath;
  private HttpStatus errorCode;
  private String errorMessage;
  private LocalDateTime errorTime;
}
