package api.condominio.portaria.repository;

import api.condominio.portaria.models.Owner;
import api.condominio.portaria.enums.RecordStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, UUID> {
    Page<Owner> findAllByStatusEquals(RecordStatusEnum status, Pageable p);

    Optional<Owner> findByCpfAndStatusEquals(String cpf, RecordStatusEnum s);

    Optional<Owner> findOwnerByIdAndStatusEquals(UUID id, RecordStatusEnum s);

    @Modifying
    @Query(value = "UPDATE proprietario SET telefone = :phone WHERE id = :id", nativeQuery = true)
    Integer updateOwnerPhone(@Param("id") UUID id, @Param("phone") String newPhone);

    @Modifying
    @Query(value = "UPDATE proprietario SET status = :setStatus WHERE id = :id AND status = :wStatus", nativeQuery = true)
    Integer updateOwnerStatus(@Param("id") UUID id, @Param("setStatus") String setStatus, @Param("wStatus") String wStatus);
}