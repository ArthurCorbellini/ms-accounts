package com.corbellini.accounts.service.impl;

import java.util.Random;
import org.springframework.stereotype.Service;
import com.corbellini.accounts.constants.AccountConstants;
import com.corbellini.accounts.dto.AccountDto;
import com.corbellini.accounts.dto.CustomerDto;
import com.corbellini.accounts.entity.Account;
import com.corbellini.accounts.entity.Customer;
import com.corbellini.accounts.exception.CustomerAlreadyExistsException;
import com.corbellini.accounts.exception.ResourceNotFoundException;
import com.corbellini.accounts.mapper.AccountMapper;
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

    Customer savedCustomer = customerRepository.save(customer);
    accountRepository.save(createNewAccount(savedCustomer));
  }

  /**
   * 
   * @param customer - Customer Object
   * @return the new account details
   */
  private Account createNewAccount(Customer customer) {
    Account newAccount = new Account();
    newAccount.setCustomerId(customer.getCustomerId());
    newAccount.setAccountNumber(1000000000L + new Random().nextInt(900000000));
    newAccount.setAccountType(AccountConstants.SAVINGS);
    newAccount.setBranchAddress(AccountConstants.ADDRESS);

    return newAccount;
  }

  @Override
  public CustomerDto fetchAccount(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

    Account account = accountRepository.findByCustomerId(customer.getCustomerId())
        .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId",
            customer.getCustomerId().toString()));

    CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
    customerDto.setAccountDto(AccountMapper.mapToAccountsDto(account, new AccountDto()));

    return customerDto;
  }

  @Override
  public boolean updateAccount(CustomerDto customerDto) {
    AccountDto accountDto = customerDto.getAccountDto();

    if (accountDto == null)
      return false;

    Account account = accountRepository.findById(accountDto.getAccountNumber())
        .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber",
            accountDto.getAccountNumber().toString()));

    account = accountRepository.save(AccountMapper.mapToAccount(accountDto, account));

    Long customerId = account.getCustomerId();
    Customer customer = customerRepository.findById(customerId).orElseThrow(
        () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));

    customerRepository.save(CustomerMapper.mapToCustomer(customerDto, customer));

    return true;
  }

  @Override
  public boolean deleteAccount(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

    accountRepository.deleteByCustomerId(customer.getCustomerId());
    customerRepository.deleteById(customer.getCustomerId());

    return true;
  }
}