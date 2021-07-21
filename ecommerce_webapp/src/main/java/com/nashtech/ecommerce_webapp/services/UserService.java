package com.nashtech.ecommerce_webapp.services;

import com.nashtech.ecommerce_webapp.dtos.SigninResponseDTO;
import com.nashtech.ecommerce_webapp.exceptions.CustomException;
import com.nashtech.ecommerce_webapp.models.user.User;
import com.nashtech.ecommerce_webapp.repositories.UserRepository;
import com.nashtech.ecommerce_webapp.security.JwtTokenProvider;
import com.nashtech.ecommerce_webapp.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService{

    @Autowired
    private UserRepository repository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    //Sign in
    public SigninResponseDTO signIn(String username, String password){
        try{
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);

            Authentication authObj = authenticationManager.authenticate(auth);

            SecurityContextHolder.getContext().setAuthentication(authObj);

            String token = jwtTokenProvider.createToken(authObj);

            UserDetailsImpl userDetails = (UserDetailsImpl) authObj.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return new SigninResponseDTO(token, userDetails.getId(), userDetails.getUsername(), roles);
        }
        catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //Sign up
    public String signUp(User user){
        if (!repository.existsByEmail(user.getEmail())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
            return new String("Sign up success");
        }
        else{
            throw new CustomException("Email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //get accessing user
    public User whoAmI(HttpServletRequest request){
        return repository.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request)));
    }

//    public String refresh(String username) {
//        return jwtTokenProvider.createToken(username, repository.findByEmail(username).getRoles());
//    }

//    @Transactional
//    public boolean createAccount(User account){
//        String nativeQuery = "INSERT INTO Account (email, password, phoneNumber, address, fullname, birthdate, roleID, status) " +
//                "VALUES (?,?,?,?,?,?,?,?)";
//        return em.createNativeQuery(nativeQuery)
//                .setParameter(1, account.getEmail())
//                .setParameter(2, account.getPassword())
//                .setParameter(3, account.getPhoneNumber())
//                .setParameter(4, account.getAddress())
//                .setParameter(5, account.getFullName())
//                .setParameter(6, account.getBirthDate())
//                .setParameter(7, account.getRoleID())
//                .setParameter(8, account.getStatus())
//                .executeUpdate() > 0;
//    }
    //Get all account
    @Transactional
    public List<User> getAll(){
        return repository.findAll();
    }

    //Get account by email
    @Transactional
    public User getAccountByEmail(String email){
        return repository.findByEmail(email);
    }
    //Get accounts like name, sort by birthdate
    @Transactional
    public List<User> getAccountsLikeName(String name){
        List<User> result = repository.getAccountLikeName(name);
        if (result != null){
            return result;
        }
        return null;
    }
    //TODO: Update account
//    @Transactional
//    public boolean updateAccount(User account){
//        boolean isExisted = isExistedAccount(account);
//        if (isExisted){
//            String nativeQuery = "UPDATE Account SET password = ?, " +
//                    "phoneNumber = ?, address = ?, fullname = ?, birthdate = ?, " +
//                    "roleID = ? , status = ? WHERE email = ?";
//            int impactedRow = em.createNativeQuery(nativeQuery)
//                    .setParameter(1, account.getPassword())
//                    .setParameter(2, account.getPhoneNumber())
//                    .setParameter(3, account.getAddress())
//                    .setParameter(4, account.getFullName())
//                    .setParameter(5, account.getBirthDate())
//                    .setParameter(6, account.getRoleID())
//                    .setParameter(7, account.getStatus())
//                    .setParameter(8, account.getEmail())
//                    .executeUpdate();
//            return impactedRow > 0;
//        }
//        return false;
//    }
    //TODO: Delete account
    @Transactional
    public boolean deleteAccount(String email){
        User result = getAccountByEmail(email);
        if (result != null){
            String nativeQuery = "DELETE FROM Account WHERE email = ?";
            int impactedRow = em.createNativeQuery(nativeQuery)
                    .setParameter(1, email)
                    .executeUpdate();
            return impactedRow > 0;
        }
        return false;
    }
}
