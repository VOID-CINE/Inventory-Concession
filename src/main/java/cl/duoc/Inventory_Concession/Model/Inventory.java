package cl.duoc.Inventory_Concession.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_invent; //Id_inventario

    @Column(name = "item_nombre", nullable = false, length = 100)
    private String itemNombre; //nombre de objeto

    @Column(name = "stock_contable", nullable = false)
    private Integer stockCont; //cantidad existente en el local
}
