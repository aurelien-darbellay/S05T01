package aDarbellay.s05.t1.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class PlayerRequest {
    @NotBlank(message = "Username is required")
    private String userName;
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerRequest request = (PlayerRequest) o;
        return Objects.equals(firstName, request.firstName) && Objects.equals(lastName, request.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
