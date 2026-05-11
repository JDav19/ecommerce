package co.edu.usbcali.ecommerceusb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateUserRequest {
    private String fullName;
    private String phone;
    private String address;
    private String country;
}