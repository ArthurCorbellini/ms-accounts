package com.corbellini.accounts.service;

import com.corbellini.accounts.dto.CustomerDto;

public interface IAccountService {

  /**
   * 
   * @param customerDto - CustomerDto Object
   */
  void createAccount(CustomerDto customerDto);
}
