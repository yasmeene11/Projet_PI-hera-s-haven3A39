package entities;

import java.util.Date;

public class User {
    private int accountId;
    private String name;
    private String surname;
    private String gender;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;
    private String role;
    private String accountStatus;
    private String resetToken;
    private Date resetTokenRequestedAt;
    private String image; // New attribute for user image

    // Constructors
    public User() {
    }

    public User(String name, String surname, String gender, String phoneNumber, String address,
                String email, String password, String role, String accountStatus, String resetToken, Date resetTokenRequestedAt) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.password = password;
        this.role = role;
        this.accountStatus = accountStatus;
        this.resetToken = resetToken;
        this.resetTokenRequestedAt = resetTokenRequestedAt;

    }

    public User(String name, String surname, String gender, String phoneNumber, String address,
                String email, String password, String role, String accountStatus, String resetToken, Date resetTokenRequestedAt, String image) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.password = password;
        this.role = role;
        this.accountStatus = accountStatus;
        this.resetToken = resetToken;
        this.resetTokenRequestedAt = resetTokenRequestedAt;
        this.image = image;
    }

    public User(int accountId, String name, String surname, String gender, String phoneNumber, String address,
                String email, String password, String role, String accountStatus, String resetToken, Date resetTokenRequestedAt, String image) {
        this.accountId = accountId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.password = password;
        this.role = role;
        this.accountStatus = accountStatus;
        this.resetToken = resetToken;
        this.resetTokenRequestedAt = resetTokenRequestedAt;
        this.image = image;
    }

    // Getters and Setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Date getResetTokenRequestedAt() {
        return resetTokenRequestedAt;
    }

    public void setResetTokenRequestedAt(Date resetTokenRequestedAt) {
        this.resetTokenRequestedAt = resetTokenRequestedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "accountId=" + accountId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", resetToken='" + resetToken + '\'' +
                ", resetTokenRequestedAt=" + resetTokenRequestedAt +
                ", image='" + image + '\'' +
                '}';
    }
}
