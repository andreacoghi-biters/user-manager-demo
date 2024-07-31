package it.biters.demo.user.manager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

public record UserManagerInputRequest(@NotEmpty String firstName, @NotEmpty String lastName,
                                      @NotEmpty @Email String email, String address) implements Serializable {
}
