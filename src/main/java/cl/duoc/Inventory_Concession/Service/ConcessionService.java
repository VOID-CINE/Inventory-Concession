package cl.duoc.Inventory_Concession.Service;

import cl.duoc.Inventory_Concession.Client.InventarioCliente;
import cl.duoc.Inventory_Concession.DTO.ConcessionRequestDTO;
import cl.duoc.Inventory_Concession.DTO.InventoryDTO;
import cl.duoc.Inventory_Concession.Exception.StockInsuficienteException;
import cl.duoc.Inventory_Concession.Model.Concession;
import cl.duoc.Inventory_Concession.Repository.ConcessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConcessionService {
    private static final Logger log = LoggerFactory.getLogger(ConcessionService.class);

    private final ConcessionRepository concessionRepository;
    private final InventarioCliente inventarioCliente;

    public ConcessionService(ConcessionRepository concessionRepository, InventarioCliente inventarioCliente) {
        this.concessionRepository = concessionRepository;
        this.inventarioCliente = inventarioCliente;
    }

    @Transactional
    public Concession realizarVenta(ConcessionRequestDTO dto) {
        log.info("Procesando negocio de venta para artículo: {}", dto.getNombre());

        // 1. Consultar el stock real al microservicio remoto usando WebClient
        InventoryDTO itemInventario = inventarioCliente.obtenerItemInventario(dto.getId_invent());

        if (itemInventario == null) {
            throw new RuntimeException("El producto seleccionado no existe en el inventario.");
        }

        // 2. Regla de negocio: Validar si alcanza la mercancía
        if (itemInventario.getStock() < dto.getCantidad()) {
            throw new StockInsuficienteException("Stock insuficiente. Disponibles: " + itemInventario.getStock());
        }

        // 3. Modificar el stock remotamente
        inventarioCliente.descontar(dto.getId_invent(), dto.getCantidad());

        // 4. Mapear el DTO a la Entidad local y guardar en la base de datos propia
        Concession concession = new Concession();
        concession.setNombre(dto.getNombre());
        concession.setCantidad(dto.getCantidad());
        concession.setPrecio(dto.getPrecio());
        concession.setId_invent(dto.getId_invent());

        return concessionRepository.save(concession);
    }

    public List<Concession> obtenerTodasLasVentas() {
        return concessionRepository.findAll();
    }

    public Concession obtenerVentaPorId(Long id) {
        return concessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La concesión solicitada no existe."));
    }

    @Transactional
    public Concession actualizarVenta(Long id, ConcessionRequestDTO dto) {
        Concession existente = obtenerVentaPorId(id);
        existente.setNombre(dto.getNombre());
        existente.setCantidad(dto.getCantidad());
        existente.setPrecio(dto.getPrecio());
        existente.setId_invent(dto.getId_invent());
        return concessionRepository.save(existente);
    }

    @Transactional
    public void eliminarVenta(Long id) {
        Concession existente = obtenerVentaPorId(id);
        concessionRepository.delete(existente);
    }
}
