package diplomskarabota.demo.services.Impl;


import diplomskarabota.demo.models.CompanyUser;
import diplomskarabota.demo.models.ERole;
import diplomskarabota.demo.models.Role;
import diplomskarabota.demo.models.exceptions.InvalidEmailException;
import diplomskarabota.demo.payload.request.CompanySignUpRequest;
import diplomskarabota.demo.payload.request.LoginRequest;
import diplomskarabota.demo.payload.response.JwtResponse;
import diplomskarabota.demo.payload.response.MessageResponse;
import diplomskarabota.demo.repositories.CompanyRepository;
import diplomskarabota.demo.repositories.RoleRepository;
import diplomskarabota.demo.repositories.StudentRepository;
import diplomskarabota.demo.security.jwt.JwtUtils;
import diplomskarabota.demo.security.services.UserDetailsImpl;
import diplomskarabota.demo.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl  implements CompanyService {

    private final CompanyRepository companyRepository;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    StudentRepository studentRepository;


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;

    }



    @Override
    public ResponseEntity<?> loginCompany(LoginRequest loginRequest) {
        CompanyUser user=this.companyRepository.findByEmail(loginRequest.getEmail()).orElseThrow(InvalidEmailException::new);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    public ResponseEntity<?> registerCompany(CompanySignUpRequest signUpRequest) {
        if (studentRepository.existsByName(signUpRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Name is already taken!"));
        }

        if (studentRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        if (companyRepository.existsByName(signUpRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Name is already taken!"));
        }

        if (companyRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        CompanyUser user = new CompanyUser(signUpRequest.getName(),
                signUpRequest.getEmail(),
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

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }


        user.setRoles(roles);
        user.setAddress(signUpRequest.getAddress());

        user.setContactName(signUpRequest.getContactName());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());

        user.setCompanyDescription(signUpRequest.getDescription());
        user.setLinkToWebSite(signUpRequest.getLink());

        companyRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Company registered successfully!"));
    }




}
