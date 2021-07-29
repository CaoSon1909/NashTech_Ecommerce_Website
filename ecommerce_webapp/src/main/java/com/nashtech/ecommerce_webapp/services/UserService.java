package com.nashtech.ecommerce_webapp.services;

import com.nashtech.ecommerce_webapp.dtos.SigninResponseDTO;
import com.nashtech.ecommerce_webapp.dtos.SignupRequestDTO;
import com.nashtech.ecommerce_webapp.dtos.SignupResponseDTO;
import com.nashtech.ecommerce_webapp.exceptions.CustomException;
import com.nashtech.ecommerce_webapp.models.user.Role;
import com.nashtech.ecommerce_webapp.models.user.RoleName;
import com.nashtech.ecommerce_webapp.models.user.User;
import com.nashtech.ecommerce_webapp.repositories.RoleRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

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

    private User resolveUserFromSignupRequest(SignupRequestDTO dto){
        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = dto.getRoles();
        if (strRoles == null){
            Role userRole = roleRepository.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
        else{
            strRoles.forEach(role -> {
                switch (role){
                    case "ADMIN":
                        Role adminRole = roleRepository.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        User user = new User();
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNum());
        user.setAddress(dto.getAddress());
        user.setFullName(dto.getFullName());
        user.setBirthDate(dto.getBirthdate());
        user.setStatus(1);
        return user;
    }

    private boolean checkRetypePassword(SignupRequestDTO dto){
        String password = dto.getPassword().trim();
        String retypePass = dto.getRetypePassword().trim();
        return password.matches(retypePass) ? true : false;
    }

    //Sign up
    @Transactional
    public User signUp(SignupRequestDTO dto){
        if (!repository.existsByEmail(dto.getEmail()) && checkRetypePassword(dto)){
            User user = resolveUserFromSignupRequest(dto);
            User result = repository.save(user);
            return result;
        }
        return null;
    }


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
    @Transactional
    public boolean updateAccount(User account){
        User result = getAccountByEmail(account.getEmail());
        if (result != null){
            String nativeQuery = "UPDATE Userr SET email = ?, password = ?, phoneNumber = ?, address = ?, " +
                    "fullname = ?, birthdate = ?, status = ? WHERE id = ?";
            int impactedRow = em.createNativeQuery(nativeQuery)
                    .setParameter(1, account.getEmail())
                    .setParameter(2, account.getPassword())
                    .setParameter(3, account.getPhoneNumber())
                    .setParameter(4, account.getAddress())
                    .setParameter(5, account.getFullName())
                    .setParameter(6, account.getBirthDate())
                    .setParameter(7, account.getStatus())
                    .setParameter(8, account.getId())
                    .executeUpdate();
            return impactedRow > 0;
        }
        return false;
    }
    //TODO: Delete account
    @Transactional
    public boolean deleteAccount(String email){
        User result = getAccountByEmail(email);
        if (result != null){
            String nativeQuery = "UPDATE Userr SET status = 0 WHERE email = ?";
            int impactedRow = em.createNativeQuery(nativeQuery)
                    .setParameter(1, email)
                    .executeUpdate();
            return impactedRow > 0;
        }
        return false;
    }
}
