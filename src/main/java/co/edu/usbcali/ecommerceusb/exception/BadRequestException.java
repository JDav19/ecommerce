//Para los de validacion de campos vacios o incorrectos, osea el 400

package co.edu.usbcali.ecommerceusb.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}