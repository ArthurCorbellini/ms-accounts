package com.artcorb.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import com.artcorb.accounts.dto.CardDto;
import com.artcorb.accounts.util.FilterUtil;

@FeignClient("cards")
public interface CardsFeignClient {

  @GetMapping(value = "/api/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CardDto> fetchCardDetails(
      @RequestHeader(FilterUtil.CORRELATION_ID) String correlationId,
      @RequestParam String mobileNumber);

}
