package it.biters.demo.user.manager.dtos;

import java.io.Serializable;

public record UserManagerSearchRequest(String firstName, String lastName) implements Serializable {
}
