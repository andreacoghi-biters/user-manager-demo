package it.biters.demo.user.manager.dtos;

import java.io.Serializable;
import java.util.Collection;

public record UserManagerSearchResponse(Collection<UserDto> users) implements Serializable {
}
