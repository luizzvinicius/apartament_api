package api.condominio.portaria.repository;

import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.models.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, UUID> {
    List<Resident> findByApartamentNumAptoBlocoAndApartamentNumAptoNumAptoAndStatusEquals(String b, String numApto, RecordStatusEnum s);
    List<Resident> findByApartamentNumAptoBlocoAndStatusEquals(String b, RecordStatusEnum s);

    Optional<Resident> findByCpfEquals(String cpf);

    @Modifying
    @Query(value = "UPDATE morador SET status = ?2 WHERE id = ?1", nativeQuery = true)
    Integer updateResidentStatus(UUID id, String s);

    @Modifying
    @Query(value = "UPDATE morador SET status = ?3 WHERE bloco = ?1 AND num_apto = ?2", nativeQuery = true)
    Integer updateAllResidentsStatus(String bloco, String numApto, String status);
}