package diplomskarabota.demo.services;


import diplomskarabota.demo.payload.request.LoginRequest;
import diplomskarabota.demo.payload.request.StudentSignUpRequest;
import org.springframework.http.ResponseEntity;



public interface StudentService {


    ResponseEntity<?> loginStudent(LoginRequest loginRequest);

    ResponseEntity<?> registerStudent(StudentSignUpRequest signUpRequest);
    String getUserType(String email);
}

