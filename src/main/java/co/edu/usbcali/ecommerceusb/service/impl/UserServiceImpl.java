package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.CreateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteUserResponse;
import co.edu.usbcali.ecommerceusb.dto.response.UserResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.ResourceNotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.UserMapper;
import co.edu.usbcali.ecommerceusb.model.DocumentType;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.DocumentTypeRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return List.of();
        }

        return UserMapper.modelToUserResponseList(users);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("Usuario no encontrado con el id: %d", id)));
        return UserMapper.modelToUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new BadRequestException("Debe ingresar el email");
        }
        User userByEmail = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("Usuario no encontrado con el email: %s", email)));
        return UserMapper.modelToUserResponse(userByEmail);
    }

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        if (Objects.isNull(createUserRequest)) {
            throw new BadRequestException("El objeto createUserRequest no puede ser nulo");
        }

        if (Objects.isNull(createUserRequest.getFullName()) ||
                createUserRequest.getFullName().isBlank()) {
            throw new BadRequestException("El campo fullName no puede ser nulo ni vacío");
        }

        if (Objects.isNull(createUserRequest.getPhone()) ||
                createUserRequest.getPhone().isBlank()) {
            throw new BadRequestException("El campo phone no puede ser nulo ni vacío");
        }

        if (Objects.isNull(createUserRequest.getEmail())
                || createUserRequest.getEmail().isBlank()) {
            throw new BadRequestException("El campo email no puede ser nulo ni vacío");
        }

        if (createUserRequest.getDocumentTypeId() == null || createUserRequest.getDocumentTypeId() <= 0) {
            throw new BadRequestException("El campo documentTypeId debe contener un valor mayor a 0");
        }

        if (createUserRequest.getDocumentNumber() == null || createUserRequest.getDocumentNumber().isBlank()) {
            throw new BadRequestException("El campo documentNumber no puede estar nulo ni vacío");
        }

        if (Objects.isNull(createUserRequest.getBirthDate()) || createUserRequest.getBirthDate().isBlank()) {
            throw new BadRequestException("El campo birthDate no puede estar nulo ni vacío");
        }

        if (Objects.isNull(createUserRequest.getCountry()) || createUserRequest.getCountry().isBlank()) {
            throw new BadRequestException("El campo country no puede estar nulo ni vacío");
        }

        if (Objects.isNull(createUserRequest.getAddress()) || createUserRequest.getAddress().isBlank()) {
            throw new BadRequestException("El campo address no puede estar nulo ni vacío");
        }

        DocumentType documentType = documentTypeRepository.findById(createUserRequest.getDocumentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("El tipo de documento no existe"));

        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new BadRequestException("Ya existe un usuario con el email ingresado");
        }

        if (userRepository.existsByDocumentNumberAndDocumentTypeId(
                createUserRequest.getDocumentNumber(), createUserRequest.getDocumentTypeId())) {
            throw new BadRequestException("Ya existe un usuario con el documento y tipo de documento ingresados");
        }

        User user = UserMapper.createUserRequestToUser(createUserRequest, documentType);

        user = userRepository.save(user);
        return UserMapper.modelToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario a actualizar no existe"));
        if (request.getFullName() == null || request.getFullName().isBlank()) {
            throw new BadRequestException("El nombre completo es obligatorio");
        }
        UserMapper.updateUserFromRequest(user, request);
        return UserMapper.modelToUserResponse(userRepository.save(user));
    }

    @Override
    public DeleteUserResponse deleteUser(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Ingrese ID para eliminar");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No se encontró el usuario con id %d", id)));
        userRepository.delete(user);
        return DeleteUserResponse.builder()
                .message(String.format("Usuario con id %d borrado con éxito", id))
                .build();
    }
}