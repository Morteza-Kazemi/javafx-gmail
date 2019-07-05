package model.user;

import java.io.Serializable;
import java.time.LocalDate;

public class UserAccount implements Serializable {
    private static final long serialVersionUID = 2L;
    private String name;
    private String lastName;
    private String userName;
    private String phoneNumber;
    private String gender;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public byte[] profilePhotoBArr;
    private String password;
    private LocalDate birthDate;

    //    /+++++ use interfaces
    //+++++ which things should be transient?
    // don't think that these two should be trnasient


    public byte[] getProfilePhotoBArr() {
        return profilePhotoBArr;
    }

    public void setProfilePhotoBArr(byte[] profilePhotoBArr) {
        this.profilePhotoBArr = profilePhotoBArr;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserAccount(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserAccount(String name, String lastName, String userName, LocalDate birthDate, String password) {
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.birthDate = birthDate;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
