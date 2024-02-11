package com.corbellini.accounts.service;

import com.corbellini.accounts.dto.CustomerDto;

public interface IAccountService {

  /**
   * 
   * @param customerDto - CustomerDto Object
   */
  void createAccount(CustomerDto customerDto);

  /**
   * 
   * @param mobileNumber - Input Mobile Number
   * @return Accounts Details based on a given mobileNumber
   */
  CustomerDto fetchAccount(String mobileNumber);
}
