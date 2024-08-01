package it.biters.demo.user.manager.mappers;

import it.biters.demo.user.manager.data.entities.UserEntity;
import it.biters.demo.user.manager.dtos.UserDto;
import it.biters.demo.user.manager.dtos.UserManagerInputRequest;
import it.biters.demo.user.manager.models.UserModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserManagerMapperTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";

    private final UserManagerMapper userManagerMapper = new UserManagerMapperImpl();

    @Test
    void testDto2model() {
        UserModel expected = createUserModel(null);
        UserModel result = userManagerMapper.dto2model(createUserManagerInputRequest());

        assertEquals(expected, result);
    }

    @Test
    void testDto2model2() {
        UserModel expected = createUserModel(ID);
        UserModel result = userManagerMapper.dto2model(ID, createUserManagerInputRequest());

        assertEquals(expected, result);
    }

    @Test
    void testModel2dao() {
        UserEntity expected = createUserEntity();
        UserEntity result = userManagerMapper.model2dao(createUserModel(ID));

        assertEquals(expected, result);
    }

    @Test
    void testDao2model() {
        UserModel expected = createUserModel(ID);
        UserModel result = userManagerMapper.dao2model(createUserEntity());

        assertEquals(expected, result);
    }

    @Test
    void testModel2dto() {
        UserDto result = userManagerMapper.model2dto(createUserModel(ID));

        assertEquals(createUserDto(), result);
    }

    @Test
    void testUpdateDao() {
        UserModel expectedUserModel = new UserModel(ID, FIRST_NAME+1, LAST_NAME+1, EMAIL+1, ADDRESS+1);
        UserEntity userEntity = createUserEntity();
        userManagerMapper.updateDao(userEntity, expectedUserModel);

        assertEquals(expectedUserModel.firstName(), userEntity.getFirstName());
        assertEquals(expectedUserModel.lastName(), userEntity.getLastName());
        assertEquals(expectedUserModel.email(), userEntity.getEmail());
        assertEquals(expectedUserModel.address(), userEntity.getAddress());
    }

    @Test
    void testAllDaos2models() {
        List<UserModel> result = userManagerMapper.allDaos2models(List.of(createUserEntity()));

        assertEquals(List.of(createUserModel(ID)), result);
    }

    @Test
    void testAllModels2dtos() {
        List<UserDto> result = userManagerMapper.allModels2dtos(List.of(createUserModel(ID)));

        assertEquals(List.of(createUserDto()), result);
    }

    private static UserModel createUserModel(Long id) {
        return new UserModel(id, FIRST_NAME, LAST_NAME, EMAIL, ADDRESS);
    }

    private static UserManagerInputRequest createUserManagerInputRequest() {
        return new UserManagerInputRequest(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS);
    }

    private static UserEntity createUserEntity() {
        return new UserEntity(ID, FIRST_NAME, LAST_NAME, EMAIL, ADDRESS);
    }

    private static UserDto createUserDto() {
        return new UserDto(ID, FIRST_NAME, LAST_NAME, EMAIL, ADDRESS);
    }
}
