package diplomskarabota.demo.security.services;




import diplomskarabota.demo.models.User;
import diplomskarabota.demo.repositories.CompanyRepository;
import diplomskarabota.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if(studentRepository.existsByName(username)){
            user=studentRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with name: " + username));
        }
        else{
            user=companyRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with name: " + username));
        }

        return UserDetailsImpl.build(user);
    }

}