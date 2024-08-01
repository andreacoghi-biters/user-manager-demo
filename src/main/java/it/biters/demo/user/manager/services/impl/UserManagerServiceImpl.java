package it.biters.demo.user.manager.services.impl;

import it.biters.demo.user.manager.data.entities.UserEntity;
import it.biters.demo.user.manager.data.repositories.UserRepository;
import it.biters.demo.user.manager.exceptions.UserNotFoundException;
import it.biters.demo.user.manager.mappers.UserManagerMapper;
import it.biters.demo.user.manager.models.UserModel;
import it.biters.demo.user.manager.services.UserManagerService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class UserManagerServiceImpl implements UserManagerService {

    public static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .setIgnoreHeaderCase(true)
            .setTrim(true)
            .build();

    private final UserManagerMapper mapper;
    private final UserRepository repository;
    private final Validator validator;

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

    @Override
    public void importUsers(MultipartFile file) throws IOException {
        List<UserEntity> userEntities;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSV_FORMAT)) {
            userEntities = csvParser.stream()
                    .map(mapper::csvRecord2model)
                    .filter(userModel -> validator.validate(userModel).isEmpty())
                    .map(mapper::model2dao)
                    .toList();
        }
        repository.saveAll(userEntities);
    }
}
