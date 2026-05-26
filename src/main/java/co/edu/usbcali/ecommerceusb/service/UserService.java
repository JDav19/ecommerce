package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.CreateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteUserResponse;
import co.edu.usbcali.ecommerceusb.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();
    UserResponse getUserById(Integer id);
    UserResponse getUserByEmail(String email);
    UserResponse createUser(CreateUserRequest createUserRequest);
    UserResponse updateUser(Integer id, UpdateUserRequest request);
    DeleteUserResponse deleteUser(Integer id);
}