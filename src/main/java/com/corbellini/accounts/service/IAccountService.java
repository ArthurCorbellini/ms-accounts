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

  /**
   * 
   * @param customerDto - CustomerDto Object
   * @return boolean indicating if the update of Account details is successful or not
   */
  boolean updateAccount(CustomerDto customerDto);
}
