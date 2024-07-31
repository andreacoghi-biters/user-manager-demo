package it.biters.demo.user.manager.controllers;

import it.biters.demo.user.manager.dtos.UserDto;
import it.biters.demo.user.manager.dtos.UserManagerInputRequest;
import it.biters.demo.user.manager.dtos.UserManagerSearchRequest;
import it.biters.demo.user.manager.dtos.UserManagerSearchResponse;
import it.biters.demo.user.manager.exceptions.UserNotFoundException;
import it.biters.demo.user.manager.mappers.UserManagerMapper;
import it.biters.demo.user.manager.models.UserModel;
import it.biters.demo.user.manager.services.UserManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Log
public class UserManagerController {

    private final UserManagerMapper mapper;
    private final UserManagerService service;

    @PostMapping(value = "/insert")
    public ResponseEntity<Void> insert(@Valid @RequestBody UserManagerInputRequest request) {
        UserModel userModel = mapper.dto2model(request);
        service.insert(userModel);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDto> get(@PathVariable long userId) {
        UserModel userModel;
        try {
            userModel = service.get(userId);
        } catch (UserNotFoundException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        UserDto userDto = mapper.model2dto(userModel);
        return new ResponseEntity<>(userDto, OK);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<Void> update(@PathVariable long userId, @Valid @RequestBody UserManagerInputRequest request) {
        try {
            service.update(mapper.dto2model(userId, request));
        } catch (UserNotFoundException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> delete(@PathVariable long userId) {
        try {
            service.delete(userId);
        } catch (UserNotFoundException e) {
            String message = "Cannot delete: " + e.getMessage();
            log.log(Level.SEVERE, message, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message, e);
        }
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping(value = "/search")
    public ResponseEntity<UserManagerSearchResponse> search(@RequestBody UserManagerSearchRequest request) {
        List<UserModel> usersModels = service.search(request.firstName(), request.lastName());
        List<UserDto> usersDtos = mapper.allModels2dtos(usersModels);
        var userSearchResponse = new UserManagerSearchResponse(usersDtos);
        return ResponseEntity.status(OK).body(userSearchResponse);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importUsers(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            service.importUsers(file);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
