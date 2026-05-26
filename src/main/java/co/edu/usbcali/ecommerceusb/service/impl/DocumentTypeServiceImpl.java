package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.CreateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteDocumentTypeResponse;
import co.edu.usbcali.ecommerceusb.dto.response.DocumentTypeResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.ResourceNotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.DocumentTypeMapper;
import co.edu.usbcali.ecommerceusb.model.DocumentType;
import co.edu.usbcali.ecommerceusb.repository.DocumentTypeRepository;
import co.edu.usbcali.ecommerceusb.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public List<DocumentTypeResponse> getDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeRepository.findAll();

        if (documentTypes.isEmpty()) {
            return List.of();
        }

        return DocumentTypeMapper.modelToDocumentTypeResponseList(documentTypes);
    }

    @Override
    public DocumentTypeResponse getDocumentTypeById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar un id válido para buscar");
        }
        DocumentType documentType = documentTypeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("Tipo de documento no encontrado con el id: %d", id)));

        return DocumentTypeMapper.modelToDocumentTypeResponse(documentType);
    }

    @Override
    public DocumentTypeResponse createDocumentType(CreateDocumentTypeRequest request) {
        DocumentType documentType = DocumentTypeMapper.createRequestToModel(request);
        return DocumentTypeMapper.modelToDocumentTypeResponse(documentTypeRepository.save(documentType));
    }

    @Override
    public DocumentTypeResponse updateDocumentType(Integer id, UpdateDocumentTypeRequest request) {
        DocumentType documentType = documentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado para actualizar"));
        DocumentTypeMapper.updateModelFromRequest(documentType, request);
        return DocumentTypeMapper.modelToDocumentTypeResponse(documentTypeRepository.save(documentType));
    }

    @Override
    public DeleteDocumentTypeResponse deleteDocumentType(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Ingrese ID para eliminar");
        }
        DocumentType documentType = documentTypeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No se encontró el tipo de documento con id %d", id)));
        documentTypeRepository.delete(documentType);
        return DeleteDocumentTypeResponse.builder()
                .message(String.format("Tipo de documento con id %d borrado correctamente", id))
                .build();
    }
}
