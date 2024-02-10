package com.corbellini.accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.corbellini.accounts.constants.AccountConstants;
import com.corbellini.accounts.dto.CustomerDto;
import com.corbellini.accounts.entity.Account;
import com.corbellini.accounts.entity.Customer;
import com.corbellini.accounts.exception.CustomerAlreadyExistsException;
import com.corbellini.accounts.mapper.CustomerMapper;
import com.corbellini.accounts.repository.AccountRepository;
import com.corbellini.accounts.repository.CustomerRepository;
import com.corbellini.accounts.service.IAccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

  private AccountRepository accountRepository;
  private CustomerRepository customerRepository;

  @Override
  public void createAccount(CustomerDto customerDto) {
    Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

    if (customerRepository.findByMobileNumber(customerDto.getMobileNumber()).isPresent())
      throw new CustomerAlreadyExistsException(
          "Customer already registered with given mobileNumber " + customerDto.getMobileNumber());

    customer.setCreatedAt(LocalDateTime.now());
    customer.setCreatedBy("Anonymous");
    Customer savedCustomer = customerRepository.save(customer);
    accountRepository.save(createNewAccount(savedCustomer));
  }

  /**
   * @param customer - Customer Object
   * @return the new account details
   */
  private Account createNewAccount(Customer customer) {
    Account newAccount = new Account();
    newAccount.setCustomerId(customer.getCustomerId());
    newAccount.setAccountNumber(1000000000L + new Random().nextInt(900000000));
    newAccount.setAccountType(AccountConstants.SAVINGS);
    newAccount.setBranchAddress(AccountConstants.ADDRESS);
    newAccount.setCreatedAt(LocalDateTime.now());
    newAccount.setCreatedBy("Anonymous");

    return newAccount;
  }

}
