package cl.duoc.Inventory_Concession.Controller;

import cl.duoc.Inventory_Concession.DTO.ConcessionDTO;
import cl.duoc.Inventory_Concession.Model.Concession;
import cl.duoc.Inventory_Concession.Service.ConcessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concessions")
public class ConsecessionController {
    private final ConcessionService concessionService;
    //Crear venta
    @PostMapping
    public ResponseEntity<Concession> realizarVenta(@Valid @RequestBody ConcessionRequestDTO dto) {
        Concession nuevaVenta = concessionService.realizarVenta(dto);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }

    //Obtener todo
    @GetMapping
    public ResponseEntity<List<Concession>> obtenerTodasLasVentas() {
        return ResponseEntity.ok(concessionService.obtenerTodasLasVentas());
    }

    //Obtener por Id
    @GetMapping("/{id}")
    public ResponseEntity<Concession> obtenerVentaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(concessionService.obtenerVentaPorId(id));
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Concession> actualizarVenta(@PathVariable Long id, @Valid @RequestBody ConcessionRequestDTO dto) {
        return ResponseEntity.ok(concessionService.actualizarVenta(id, dto));
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        concessionService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
