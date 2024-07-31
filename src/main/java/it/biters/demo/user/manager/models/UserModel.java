package it.biters.demo.user.manager.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserModel(Long id, @NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty @Email String email, String address) {
}
