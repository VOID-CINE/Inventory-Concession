package cl.duoc.Inventory_Concession.Client;

import cl.duoc.Inventory_Concession.DTO.InventoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class InventarioCliente {

    private static final Logger log = LoggerFactory.getLogger(InventarioCliente.class);
    private final WebClient webClient;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    public InventarioCliente(WebClient webClient) {
        this.webClient = webClient;
    }

    public InventoryDTO obtenerItemInventario(Long id_invent) {
        log.info("Llamando a inventario remoto. ID consultado: {}", id_invent);
        try {
            return webClient.get()
                    .uri(inventoryServiceUrl + "/api/inventory/" + id_invent)
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();
        } catch (Exception e) {
            log.error("Error al consultar el microservicio de inventario para ID: {}", id_invent);
            return null;
        }
    }

    public void descontar(Long id_invent, Integer cantidad) {
        log.info("Enviando orden de descuento remota para id_invent: {}, cantidad: {}", id_invent, cantidad);
        webClient.put()
                .uri(inventoryServiceUrl + "/api/inventory/" + id_invent + "/deduct?quantity=" + cantidad)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}