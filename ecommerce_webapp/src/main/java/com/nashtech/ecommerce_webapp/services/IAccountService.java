package com.nashtech.ecommerce_webapp.services;

import com.nashtech.ecommerce_webapp.models.Account;

import java.util.List;

public interface IAccountService {
    List<Account> findAndPaginate(int pageNo, int pageSize);
}
