package it.biters.demo.user.manager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record UserDto(@NotNull Long id, @NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty @Email String email, String address) implements Serializable {
}
