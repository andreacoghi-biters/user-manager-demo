package it.biters.demo.user.manager.services.impl;

import it.biters.demo.user.manager.data.entities.UserEntity;
import it.biters.demo.user.manager.data.repositories.UserRepository;
import it.biters.demo.user.manager.exceptions.UserNotFoundException;
import it.biters.demo.user.manager.mappers.UserManagerMapper;
import it.biters.demo.user.manager.models.UserModel;
import it.biters.demo.user.manager.services.UserManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagerServiceImpl implements UserManagerService {

    private final UserManagerMapper mapper;
    private final UserRepository repository;

    @Override
    public void insert(UserModel userModel) {
        UserEntity userEntity = mapper.model2dao(userModel);
        repository.save(userEntity);
    }

    @Override
    public UserModel get(long id) throws UserNotFoundException {
        return repository.findById(id).map(mapper::dao2model).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void update(UserModel userModel) throws UserNotFoundException {
//        repository.update(userModel.firstName(), userModel.firstName(), userModel.email(), userModel.address(), userModel.id());
        UserEntity userEntity = repository.findById(userModel.id()).orElseThrow(() -> new UserNotFoundException(userModel.id()));
        mapper.updateDao(userEntity, userModel);
        repository.save(userEntity);
    }

    @Override
    public void delete(long id) throws UserNotFoundException {
        UserEntity userEntity = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        repository.delete(userEntity);
    }

    @Override
    public List<UserModel> search(String firstName, String lastName) {
        List<UserEntity> userEntities = repository.searchByFirstNameAndLastName(firstName, lastName);
        return mapper.allDaos2models(userEntities);
    }
}
