package co.edu.usbcali.ecommerceusb.repository;

import co.edu.usbcali.ecommerceusb.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    // Útil para buscar el inventario de un producto específico
    Optional<Inventory> findByProductId(Integer productId);
}