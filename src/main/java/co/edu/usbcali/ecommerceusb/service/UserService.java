package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.CreateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();
    UserResponse getUserById(Integer id) throws Exception;
    UserResponse getUserByEmail(String email) throws Exception;
    UserResponse createUser(CreateUserRequest createUserRequest) throws Exception;
    UserResponse updateUser(Integer id, UpdateUserRequest request) throws Exception;
}