package cl.duoc.Inventory_Concession.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.Inventory_Concession.Model.Concession;

@Repository
public interface ConcessionRepository extends JpaRepository<Concession, Long>{
}