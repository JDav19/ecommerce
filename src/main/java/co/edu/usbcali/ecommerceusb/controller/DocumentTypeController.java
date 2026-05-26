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

    @Autowired
    private DocumentTypeService documentTypeService;

    @GetMapping("/all")
    public List<DocumentTypeResponse> getAll() {
        return DocumentTypeMapper.modelToDocumentTypeResponseList(
                documentTypeRepository.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> getById(@PathVariable Integer id) {
        DocumentType documentType = documentTypeRepository.getReferenceById(id);
        DocumentTypeResponse documentTypeResponse =
                DocumentTypeMapper.modelToDocumentTypeResponse(documentType);
        return new ResponseEntity<>(documentTypeResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DocumentTypeResponse> create(@RequestBody CreateDocumentTypeRequest request) {
        DocumentType documentType = DocumentTypeMapper.createRequestToModel(request);
        documentType = documentTypeRepository.save(documentType);
        return new ResponseEntity<>(
                DocumentTypeMapper.modelToDocumentTypeResponse(documentType),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> update(@PathVariable Integer id,
                                                       @RequestBody UpdateDocumentTypeRequest request) {
        return new ResponseEntity<>(documentTypeService.updateDocumentType(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteDocumentTypeResponse> deleteDocumentType(@PathVariable Integer id) {
        return new ResponseEntity<>(documentTypeService.deleteDocumentType(id), HttpStatus.OK);
    }
}
