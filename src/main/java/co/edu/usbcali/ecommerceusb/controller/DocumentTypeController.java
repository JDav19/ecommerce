package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.DocumentTypeResponse;
import co.edu.usbcali.ecommerceusb.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/document-type")
public class DocumentTypeController {

    // Inyección de dependencias de DocumentTypeService
    @Autowired
    private DocumentTypeService documentTypeService;

    @GetMapping("/all")
    public List<DocumentTypeResponse> getAll() {
        // Invoca el Mapper para convertir la lista de DocumentType
        // a una lista de DocumentTypeResponse
        return documentTypeService.getDocumentTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> getById(@PathVariable Integer id) throws Exception {
        // Mapear o convertir al DTO (Response) DocumentTypeResponse
        // Invocando el Mapper para convertir
        DocumentTypeResponse documentTypeResponse = documentTypeService.getDocumentTypeById(id);

        // Retornar el ResponseEntity con el documentTypeResponse
        return new ResponseEntity<>(
                documentTypeResponse,
                HttpStatus.OK
        );
    }
}