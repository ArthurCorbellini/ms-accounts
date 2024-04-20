package com.artcorb.accounts.service.client.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.artcorb.accounts.dto.CardDto;
import com.artcorb.accounts.service.client.CardsFeignClient;

@Component
public class CardsFallback implements CardsFeignClient {

  @Override
  public ResponseEntity<CardDto> fetchCardDetails(String correlationId, String mobileNumber) {
    // TODO
    // if the CardsMicroservice is down, the response of this request will be null. In real project,
    // implement some logic to override this behavior, like send values from the cache, or read the
    // data from a different database, or throw an error to the client
    return null;
  }

}
