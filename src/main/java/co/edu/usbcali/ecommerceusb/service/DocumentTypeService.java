package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.CreateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteDocumentTypeResponse;
import co.edu.usbcali.ecommerceusb.dto.response.DocumentTypeResponse;

import java.util.List;

public interface DocumentTypeService {
    List<DocumentTypeResponse> getDocumentTypes();
    DocumentTypeResponse getDocumentTypeById(Integer id);
    DocumentTypeResponse createDocumentType(CreateDocumentTypeRequest request);
    DocumentTypeResponse updateDocumentType(Integer id, UpdateDocumentTypeRequest request);
    DeleteDocumentTypeResponse deleteDocumentType(Integer id);
}
