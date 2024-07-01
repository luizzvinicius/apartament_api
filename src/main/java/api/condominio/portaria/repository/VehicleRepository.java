package api.condominio.portaria.repository;

import api.condominio.portaria.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Set<Vehicle> findAllByApartamentNumAptoBlocoEquals(String bloco);
    Integer deleteByPlaca(String placa);
}