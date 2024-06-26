package api.condominio.portaria.repository;

import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.models.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, UUID> {
    Set<Resident> findByApartamentNumAptoBlocoAndApartamentNumAptoNumAptoAndStatusEquals(String b, String numApto, RecordStatusEnum s);
}