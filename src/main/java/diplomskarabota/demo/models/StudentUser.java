package diplomskarabota.demo.models;



import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="students",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name"),
                @UniqueConstraint(columnNames = "email")
        })
public class StudentUser extends User{

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "student_roles",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne
    private DBFile img;

    @OneToOne
    private DBFile CV;



    @Email
    private String contactEmail;
    private String contactPhone;
    private String address;

    @Column(name = "summary", length = 2048)
    private String summary;


    public StudentUser() {

    }

    public StudentUser(String username, String email, String password){
        super(UserType.Student,username,email,password);
    }



    public String getContactEmail() {
        return contactEmail;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public DBFile getImg() {
        return img;
    }

    public void setImg(DBFile img) {
        this.img = img;
    }

    public DBFile getCV() {
        return CV;
    }

    public void setCV(DBFile CV) {
        this.CV = CV;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


}
