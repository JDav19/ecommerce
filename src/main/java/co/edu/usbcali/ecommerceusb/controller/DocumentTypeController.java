package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.request.CreateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DocumentTypeResponse;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteDocumentTypeResponse;
import co.edu.usbcali.ecommerceusb.mapper.DocumentTypeMapper;
import co.edu.usbcali.ecommerceusb.model.DocumentType;
import co.edu.usbcali.ecommerceusb.repository.DocumentTypeRepository;
import co.edu.usbcali.ecommerceusb.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document-type")
public class DocumentTypeController {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @GetMapping("/all")
    public List<DocumentTypeResponse> getAll() {
        // Invoca el Mapper para convertir la lista de DocumentType
        // a una lista de DocumentTypeResponse
        return DocumentTypeMapper.modelToDocumentTypeResponseList(
                documentTypeRepository.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> getById(@PathVariable Integer id) {
        // Consultar el Document Type en la base de datos
        DocumentType documentType = documentTypeRepository.getReferenceById(id);

        // Mapear o convertir al DTO (Response) DocumentTypeResponse
        // Invocando el Mapper para convertir
        DocumentTypeResponse documentTypeResponse =
                DocumentTypeMapper.modelToDocumentTypeResponse(documentType);

        // Retornar el ResponseEntity con el documentTypeResponse
        return new ResponseEntity<>(
                documentTypeResponse,
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<DocumentTypeResponse> create(@RequestBody CreateDocumentTypeRequest request) {
        // 1. Usamos el Mapper para convertir el DTO de entrada al Modelo
        DocumentType documentType = DocumentTypeMapper.createRequestToModel(request);

        // 2. Guardamos directamente usando el repository
        documentType = documentTypeRepository.save(documentType);

        // 3. Convertimos el resultado a Response y respondemos
        return new ResponseEntity<>(
                DocumentTypeMapper.modelToDocumentTypeResponse(documentType),
                HttpStatus.CREATED
        );
    }

    @Autowired
    private DocumentTypeService documentTypeService;

    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> update(@PathVariable Integer id,
                                                       @RequestBody UpdateDocumentTypeRequest request) throws Exception {
        return new ResponseEntity<>(documentTypeService.updateDocumentType(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteDocumentTypeResponse> deleteDocumentType(
            @PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(documentTypeService.deleteDocumentType(id), HttpStatus.OK);
    }
}