package com.artcorb.accounts.service.client.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.artcorb.accounts.dto.LoanDto;
import com.artcorb.accounts.service.client.LoansFeignClient;

@Component
public class LoansFallback implements LoansFeignClient {

  @Override
  public ResponseEntity<LoanDto> fetchLoanDetails(String correlationId, String mobileNumber) {
    // TODO
    // if the LoansMicroservice is down, the response of this request will be null. In real project,
    // implement some logic to override this behavior, like send values from the cache, or read the
    // data from a different database, or throw an error to the client
    return null;
  }

}
