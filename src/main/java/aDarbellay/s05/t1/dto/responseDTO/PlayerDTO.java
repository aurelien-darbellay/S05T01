package aDarbellay.s05.t1.dto.responseDTO;

import java.util.Objects;

public class PlayerDTO {
    private final String userName;
    private final String firstName;
    private final String lastName;
    private final long points;

    public PlayerDTO(String userName, String firstName, String lastName, long points) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getPoints() {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDTO playerDTO = (PlayerDTO) o;
        return points == playerDTO.points && Objects.equals(userName, playerDTO.userName) && Objects.equals(firstName, playerDTO.firstName) && Objects.equals(lastName, playerDTO.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, firstName, lastName, points);
    }
}
