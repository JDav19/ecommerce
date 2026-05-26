//Para todo lo que no exista, osea el 404

package co.edu.usbcali.ecommerceusb.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

