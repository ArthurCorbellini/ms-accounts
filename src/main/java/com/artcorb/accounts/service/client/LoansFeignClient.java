package com.artcorb.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import com.artcorb.accounts.dto.LoanDto;
import com.artcorb.accounts.util.FilterUtil;

@FeignClient("loans")
public interface LoansFeignClient {

  @GetMapping(value = "/api/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LoanDto> fetchLoanDetails(
      @RequestHeader(FilterUtil.CORRELATION_ID) String correlationId,
      @RequestParam String mobileNumber);

}
