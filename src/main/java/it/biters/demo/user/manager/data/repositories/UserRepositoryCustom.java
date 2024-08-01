package it.biters.demo.user.manager.data.repositories;

import it.biters.demo.user.manager.data.entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryCustom {

    List<UserEntity> searchByFirstNameAndLastName(String firstName, String lastName);
}
