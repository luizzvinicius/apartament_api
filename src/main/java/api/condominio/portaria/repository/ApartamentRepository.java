package api.condominio.portaria.repository;

import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.models.Apartament;
import api.condominio.portaria.models.embeddable.ApartamentNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApartamentRepository extends JpaRepository<Apartament, ApartamentNumber> {
    Page<Apartament> findAllApartamentBynumAptoBlocoEqualsAndStatusEquals(Pageable p, String bloco, RecordStatusEnum s);
    Integer countByOwnerIdAndStatusEquals(UUID id, RecordStatusEnum s);
    Optional<Apartament> findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals(String bloco, String numApto, RecordStatusEnum s);

    @Modifying
    @Query(value = "UPDATE apartamento SET status = ?3 WHERE bloco = ?1 AND num_apto = ?2", nativeQuery = true)
    Integer updateStatus(String bloco, String numApto, String status);

    @Modifying
    @Query(value = "UPDATE apartamento SET id_proprietario = ?, status = ? WHERE bloco = ? AND num_apto = ? AND status = ?", nativeQuery = true)
    Integer updateIdProprietario(UUID id, String setStatus, String bloco, String numApto, String whereStatus);
}