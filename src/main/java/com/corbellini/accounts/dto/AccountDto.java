package com.corbellini.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountDto {

  @NotNull(message = "Account number can not be a null or empty")
  private Long accountNumber;

  @NotEmpty(message = "Account type can not be a null or empty")
  private String accountType;

  @NotEmpty(message = "Branch address can not be a null or empty")
  private String branchAddress;
}
