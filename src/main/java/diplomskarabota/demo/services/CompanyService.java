package diplomskarabota.demo.services;


import diplomskarabota.demo.payload.request.CompanySignUpRequest;
import diplomskarabota.demo.payload.request.LoginRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CompanyService {


    ResponseEntity<?> loginCompany(LoginRequest loginRequest);

    ResponseEntity<?> registerCompany(CompanySignUpRequest signUpRequest);
}



