package api.condominio.portaria.repository;

import api.condominio.portaria.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findAllByApartamentNumAptoBlocoEquals(String bloco);
    Integer countByApartamentNumAptoBlocoAndApartamentNumAptoNumApto(String bloco, String numApto);
    Integer deleteByPlaca(String placa);
}