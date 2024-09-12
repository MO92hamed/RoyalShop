package com.ecommerce.royalshop.controllers;

import com.ecommerce.royalshop.exception.TokenRefreshException;
import com.ecommerce.royalshop.models.*;
import com.ecommerce.royalshop.payload.request.LoginRequest;
import com.ecommerce.royalshop.payload.request.SignupRequest;
import com.ecommerce.royalshop.payload.request.TokenRefreshRequest;
import com.ecommerce.royalshop.payload.response.JwtResponse;
import com.ecommerce.royalshop.payload.response.MessageResponse;
import com.ecommerce.royalshop.payload.response.TokenRefreshResponse;
import com.ecommerce.royalshop.repositories.RoleRepository;
import com.ecommerce.royalshop.repositories.UserRepository;
import com.ecommerce.royalshop.security.jwt.JwtUtils;
import com.ecommerce.royalshop.security.services.RefreshTokenService;
import com.ecommerce.royalshop.security.services.UserDetailsImpl;
import com.ecommerce.royalshop.services.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {


        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if (user.get().getConfirm() == true){
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String jwt = jwtUtils.generateJwtToken(userDetails);

            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

            return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                    userDetails.getUsername(), userDetails.getEmail(), roles, true));
        }else {
            return new ResponseEntity<>("User in not confirmed", HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "customer":
                        Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(customerRole);

                        break;
                    case "provider":
                        Role providerRole = roleRepository.findByName(ERole.ROLE_PROVIDER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(providerRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    @PostMapping("/forgotpassword")
    public HashMap<String, String> forgotPassword(String email) throws MessagingException {
        HashMap message = new HashMap();
        User userExisting = userRepository.findByEmail(email);
        if (userExisting == null){
            message.put("USER", "user not found");
            return message;
        }
        UUID token = UUID.randomUUID();
        userExisting.setPasswordResetToken(token.toString());
        userExisting.setId(userExisting.getId());

        //MAIL
        MimeMessage message1 = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message1);
        helper.setSubject("Forgot Password");
        helper.setFrom("admin@gmail.com");
        helper.setTo(userExisting.getEmail());
        helper.setText("<HTML><body> <a href=\"http://localhost:4200/reset/"
                + userExisting.getPasswordResetToken()+ "\">Forget Password<a/></body></HTML>", true);
        mailSender.send(message1);

        userRepository.saveAndFlush(userExisting);
        message.put("USER", "User found and mail send Successfully");
        return message;
    }

    @PostMapping("/resetpassword/{passwordResetToken}")
    public HashMap<String, String> resetPassword(@PathVariable String passwordResetToken, String newPassword){
        HashMap message = new HashMap();
        User userExisting = userRepository.findByPasswordResetToken(passwordResetToken);

        if (userExisting != null){
            userExisting.setId(userExisting.getId());
            userExisting.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userExisting.setPasswordResetToken(null);
            userRepository.save(userExisting);

            message.put("Reset Password", "PROCESSED");
            return message;
        }else {
            message.put("Reset Password", "FAILED");
            return message;
        }
    }

    @GetMapping("/getuser/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @PostMapping("/change-Password")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestBody ChangePasswordRequest request){
        String username=authentication.getName();
        System.out.println(username);
        Optional<User> user =userRepository.findByUsername(username);

        System.out.println(user);

        if(user==null) {
            throw new IllegalArgumentException("Invalide user");
        }
        User u = user.get();
        if(!encoder.matches(request.getOldPassword(),u.getPassword())){
            return new ResponseEntity<>("invalide old password", HttpStatus.EXPECTATION_FAILED);


        }
        u.setPassword(encoder.encode(request.getNewPassword()));
        return ResponseEntity.ok(userRepository.save(u));
    }

}
