package it.biters.demo.user.manager.mappers;

import it.biters.demo.user.manager.data.entities.UserEntity;
import it.biters.demo.user.manager.dtos.UserDto;
import it.biters.demo.user.manager.dtos.UserManagerInputRequest;
import it.biters.demo.user.manager.models.UserModel;
import org.apache.commons.csv.CSVRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface UserManagerMapper {

    @Mapping(target = "id", ignore = true)
    UserModel dto2model(UserManagerInputRequest request);

    @Mapping(target = "id", source = "userId")
    UserModel dto2model(Long userId, UserManagerInputRequest request);

    UserEntity model2dao(UserModel userModel);

    UserModel dao2model(UserEntity userEntity);

    UserDto model2dto(UserModel userModel);

    void updateDao(@MappingTarget UserEntity userEntity, UserModel userModel);

    List<UserModel> allDaos2models(List<UserEntity> userEntity);

    List<UserDto> allModels2dtos(List<UserModel> userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", expression = "java(csvRecord.get(\"firstName\"))")
    @Mapping(target = "lastName", expression = "java(csvRecord.get(\"lastName\"))")
    @Mapping(target = "email", expression = "java(csvRecord.get(\"email\"))")
    @Mapping(target = "address", expression = "java(csvRecord.get(\"address\"))")
    UserModel csvRecord2model(CSVRecord csvRecord);
}
