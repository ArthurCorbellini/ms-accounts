package com.corbellini.accounts.entity;

import com.corbellini.accounts.entity.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity {

  @Id
  private Long accountNumber;

  private Long customerId;

  private String accountType;

  private String branchAddress;

}
