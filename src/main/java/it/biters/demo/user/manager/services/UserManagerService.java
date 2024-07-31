package it.biters.demo.user.manager.services;

import it.biters.demo.user.manager.exceptions.UserNotFoundException;
import it.biters.demo.user.manager.models.UserModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserManagerService {

    void insert(UserModel userModel);

    UserModel get(long id) throws UserNotFoundException;

    void update(UserModel userModel) throws UserNotFoundException;

    void delete(long id) throws UserNotFoundException;

    List<UserModel> search(String firstName, String lastName);

    void importUsers(MultipartFile file) throws IOException;
}
