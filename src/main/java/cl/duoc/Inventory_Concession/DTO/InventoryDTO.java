package cl.duoc.Inventory_Concession.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InventoryDTO {
    @NotBlank(message = "El nombre del item es obligatorio")
    private String itemNombre;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @NotNull(message = "El stock es obligatorio")
    private Integer stockCont;
}
