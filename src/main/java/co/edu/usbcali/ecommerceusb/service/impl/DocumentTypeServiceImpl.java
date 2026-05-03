package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.DocumentTypeResponse;
import co.edu.usbcali.ecommerceusb.mapper.DocumentTypeMapper;
import co.edu.usbcali.ecommerceusb.model.DocumentType;
import co.edu.usbcali.ecommerceusb.repository.DocumentTypeRepository;
import co.edu.usbcali.ecommerceusb.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    // Inyección de dependencias de DocumentTypeRepository
    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public List<DocumentTypeResponse> getDocumentTypes() {
        // Definir una lista de DocumentTypes
        List<DocumentType> documentTypes = documentTypeRepository.findAll();

        // Validar si la lista está vacía
        if (documentTypes.isEmpty()) {
            return List.of();
        }

        return DocumentTypeMapper.modelToDocumentTypeResponseList(documentTypes);
    }

    @Override
    public DocumentTypeResponse getDocumentTypeById(Integer id) throws Exception {
        // Validar que venga un valor en id
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar un id válido para buscar");
        }
        // Buscar tipo de documento en base de datos por id
        // Si no lo encuentra, lanza excepción
        DocumentType documentType = documentTypeRepository.findById(id)
                .orElseThrow(() ->
                        new Exception(
                                String.format("Tipo de documento no encontrado con el id: %d", id)));

        // Mapear a Response
        DocumentTypeResponse documentTypeResponse = DocumentTypeMapper.modelToDocumentTypeResponse(documentType);
        // Retornar tipo de documento encontrado
        return documentTypeResponse;
    }

    @Override
    public DocumentTypeResponse createDocumentType(CreateDocumentTypeRequest request) throws Exception {
        // Podrías validar si el código ya existe para evitar el error de base de datos
        DocumentType documentType = DocumentTypeMapper.createRequestToModel(request);
        return DocumentTypeMapper.modelToDocumentTypeResponse(documentTypeRepository.save(documentType));
    }
}