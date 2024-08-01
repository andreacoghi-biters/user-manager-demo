package it.biters.demo.user.manager.services.impl;

import it.biters.demo.user.manager.data.entities.UserEntity;
import it.biters.demo.user.manager.data.repositories.UserRepository;
import it.biters.demo.user.manager.exceptions.UserNotFoundException;
import it.biters.demo.user.manager.mappers.UserManagerMapper;
import it.biters.demo.user.manager.models.UserModel;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagerServiceImplTest {

    public static final long ID = 1L;
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";

    @Mock
    UserManagerMapper mapper;
    @Mock
    UserRepository repository;
    @Mock
    Validator validator;
    @InjectMocks
    UserManagerServiceImpl userManagerServiceImpl;

    @Test
    void testGet() throws UserNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.of(createUserEntity()));
        when(mapper.dao2model(any())).thenReturn(createUserModel());

        UserModel result = userManagerServiceImpl.get(ID);
        assertEquals(createUserModel(), result);
    }

    @Test
    void testGetUserNotFoundException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userManagerServiceImpl.get(ID));
    }

    private static UserEntity createUserEntity() {
        return new UserEntity(ID, FIRST_NAME, LAST_NAME, EMAIL, ADDRESS);
    }

    private static UserModel createUserModel() {
        return new UserModel(ID, FIRST_NAME, LAST_NAME, EMAIL, ADDRESS);
    }
}
