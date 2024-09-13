package com.application.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PersonDTO {
    @NotEmpty(message = "Login field cannot be empty")
    @Size(min = 5, max = 20)
    private String login;
    @NotEmpty(message = "Name field cannot be empty")
    @Size(min = 1, max = 20)
    private String name;
    @NotEmpty(message = "Surname field cannot be empty")
    @Size(min = 1, max = 20)
    private String surname;
    @NotNull(message = "Date Of Birth field cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @NotEmpty(message = "Email field cannot be empty")
    @Email(message = "Email is not correct")
    private String email;
    @NotEmpty(message = "Password field cannot be empty")
    @Size(min = 8, max = 20)
    private String password;
    @NotEmpty(message = "Verification Password field cannot be empty")
    private String verificationPassword;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getVerificationPassword() {
        return verificationPassword;
    }

    public void setVerificationPassword(String verificationPassword) {
        this.verificationPassword = verificationPassword;
    }
}
