package com.nashtech.ecommerce_webapp.services;

import com.nashtech.ecommerce_webapp.jwt.details.UserDetailsImpl;
import com.nashtech.ecommerce_webapp.models.Account;
import com.nashtech.ecommerce_webapp.repositories.AccountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService{
    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @PersistenceContext
    private EntityManager em;



    //private methods
    private boolean isExistedAccount(Account account){
        Optional<Account> existed = getAccountByEmail(account.getEmail());
        return existed.isPresent();
    }

    //TODO: Create new account
//    public boolean createAccount(Account account){
//        Account insertedAcc = repository.save(account);
//        return insertedAcc != null ? true: false;
//    }
    @Transactional
    public boolean createAccount(Account account){
        String nativeQuery = "INSERT INTO Account (email, password, phoneNumber, address, fullname, birthdate, roleID, status) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        return em.createNativeQuery(nativeQuery)
                .setParameter(1, account.getEmail())
                .setParameter(2, account.getPassword())
                .setParameter(3, account.getPhoneNumber())
                .setParameter(4, account.getAddress())
                .setParameter(5, account.getFullName())
                .setParameter(6, account.getBirthDate())
                .setParameter(7, account.getRoleID())
                .setParameter(8, account.getStatus())
                .executeUpdate() > 0;
    }
    //TODO: Read account
    //Get all account
    public List<Account> getAccounts(){
        return repository.findAll();
    }
    //Get all accounts then paging
    @Override
    public List<Account> findAndPaginate(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Account> pageResult = repository.findAll(paging);
        return pageResult.toList();
    }
    //Get account by email
    @Transactional
    public Optional<Account> getAccountByEmail(String email){
        return repository.findById(email);
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
                    "roleID = ? , status = ? WHERE email = ?";
            int impactedRow = em.createNativeQuery(nativeQuery)
                    .setParameter(1, account.getPassword())
                    .setParameter(2, account.getPhoneNumber())
                    .setParameter(3, account.getAddress())
                    .setParameter(4, account.getFullName())
                    .setParameter(5, account.getBirthDate())
                    .setParameter(6, account.getRoleID())
                    .setParameter(7, account.getStatus())
                    .setParameter(8, account.getEmail())
                    .executeUpdate();
            return impactedRow > 0;
        }
        return false;
    }
    //TODO: Delete account
    @Transactional
    public boolean deleteAccount(String email){
        Optional<Account> result = getAccountByEmail(email);
        if (result.isPresent()){
            String nativeQuery = "DELETE FROM Account WHERE email = ?";
            int impactedRow = em.createNativeQuery(nativeQuery)
                    .setParameter(1, email)
                    .executeUpdate();
            return impactedRow > 0;
        }
        return false;
    }
}
