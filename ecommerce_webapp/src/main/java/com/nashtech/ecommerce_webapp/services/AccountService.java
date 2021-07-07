package com.nashtech.ecommerce_webapp.services;

import com.nashtech.ecommerce_webapp.models.Account;
import com.nashtech.ecommerce_webapp.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @PersistenceContext
    private EntityManager em;

    //private methods
    private boolean isExistedAccount(Account account){
        Account existed = getAccountByEmail(account.getEmail());
        return existed != null;
    }

    //TODO: Create new account
    @Transactional
    public boolean createAccount(Account account){
        String nativeQuery = "INSERT INTO Account (email, password, phoneNumber, address, fullname, birthdate, roleID, statusID) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        return em.createNativeQuery(nativeQuery).executeUpdate() > 0;
    }
    //TODO: Read account
    //Get all accounts
    @Transactional
    public List<Account> getAccounts(){
        List<Account> result = repository.findAll();
        return !result.isEmpty() ? result : null;
    }
    //Get account by email
    @Transactional
    public Account getAccountByEmail(String email){
        Optional<Account> result = repository.findById(email);
        return result.orElse(null);
    }
    //Get accounts like name, sort by birthdate
    @Transactional
    public List<Account> getAccountsLikeName(String name){
        List<Account> result = repository.getAccountLikeName(name);
        return !result.isEmpty()
                ? result
                : null;
    }
    //TODO: Update account
    @Transactional
    public boolean updateAccount(Account account){
        boolean isExisted = isExistedAccount(account);
        if (isExisted){
            String nativeQuery = "UPDATE Account SET password = ?, " +
                    "phoneNumber = ?, address = ?, fullname = ?, birthdate = ?, " +
                    "roleID = ? , statusID = ? WHERE email = ?";
            int impactedRow = em.createNativeQuery(nativeQuery).executeUpdate();
            return impactedRow > 0;
        }
        return false;
    }
    //TODO: Delete account
    @Transactional
    public boolean deleteAccount(Account account){
        boolean isExisted = isExistedAccount(account);
        if (isExisted){
            String nativeQuery = "DELETE FROM Account WHERE email = ?";
            int impactedRow = em.createNativeQuery(nativeQuery).executeUpdate();
            return impactedRow > 0;
        }
        return false;
    }




}
