package diplomskarabota.demo.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name="companies",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name"),
                @UniqueConstraint(columnNames = "email")
        })
public class  CompanyUser extends User {

    @NotBlank
    @Size(max=20)
    private String phoneNumber;

    private String companyDescription;

    @NotBlank
    @Size(max=30)
    private String ContactName;

    @NotBlank
    @Size(max=50)
    private String Address;


    @OneToOne
    private DBFile Logo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "company_roles",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name="website_link")
    private String linkToWebSite;

    @Column(name="aboutUs", length=2048)
    private String aboutUs;



    public CompanyUser(){

    }

    public CompanyUser(String username, String email, String password) {
        super(UserType.Company, username, email, password);


    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }



    public DBFile getLogo() {
        return Logo;
    }

    public void setLogo(DBFile logo) {
        Logo = logo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public String getLinkToWebSite() {
        return linkToWebSite;
    }

    public void setLinkToWebSite(String linkToWebSite) {
        this.linkToWebSite = linkToWebSite;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }
}
