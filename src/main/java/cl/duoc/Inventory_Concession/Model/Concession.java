package cl.duoc.Inventory_Concession.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "productos")
public class Concession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_concession; //Id_concession

    @Column(name = "nombre", nullable = false)
    private String nombre; //Nombre del producto

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad; //La cantidad que lleva por producto

    @Column(name = "precio", nullable = false)
    private Integer precio; //Precio por cada producto

    //Id de la clase inventory
    @Column(name = "id_invent", nullable = false)
    private Long id_invent;
}
