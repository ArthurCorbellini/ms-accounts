package com.artcorb.accounts.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.artcorb.accounts.dto.AccountDto;
import com.artcorb.accounts.dto.CardDto;
import com.artcorb.accounts.dto.CustomerDetailsDto;
import com.artcorb.accounts.dto.LoanDto;
import com.artcorb.accounts.entity.Account;
import com.artcorb.accounts.entity.Customer;
import com.artcorb.accounts.exception.ResourceNotFoundException;
import com.artcorb.accounts.mapper.AccountMapper;
import com.artcorb.accounts.mapper.CustomerMapper;
import com.artcorb.accounts.repository.AccountRepository;
import com.artcorb.accounts.repository.CustomerRepository;
import com.artcorb.accounts.service.ICustomerService;
import com.artcorb.accounts.service.client.CardsFeignClient;
import com.artcorb.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

  private AccountRepository accountRepository;
  private CustomerRepository customerRepository;
  private CardsFeignClient cardsFeignClient;
  private LoansFeignClient loansFeignClient;

  @Override
  public CustomerDetailsDto fetchCustomerDetails(String correlationId, String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
    CustomerDetailsDto customerDetailsDto =
        CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());

    Account account = accountRepository.findByCustomerId(customer.getCustomerId())
        .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId",
            customer.getCustomerId().toString()));
    customerDetailsDto.setAccountDto(AccountMapper.mapToAccountsDto(account, new AccountDto()));

    // Will connect with Eureka Server and try to get the loans instance details. For this, it will
    // performe some load balancing and invoke the actual mocroservice api and we'll get the
    // response.
    ResponseEntity<LoanDto> loanDtoResponseEntity =
        loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
    customerDetailsDto
        .setLoanDto(loanDtoResponseEntity != null ? loanDtoResponseEntity.getBody() : null);

    ResponseEntity<CardDto> cardDtoResponseEntity =
        cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
    customerDetailsDto
        .setCardDto(cardDtoResponseEntity != null ? cardDtoResponseEntity.getBody() : null);

    return customerDetailsDto;
  }

}
