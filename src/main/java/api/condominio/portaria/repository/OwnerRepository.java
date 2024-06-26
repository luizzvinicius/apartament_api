package api.condominio.portaria.repository;

import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.models.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, UUID> {
    Page<Owner> findAllOwnerByStatusEquals(RecordStatusEnum status, Pageable p);

    Optional<Owner> findOwnerByIdAndStatusEquals(UUID id, RecordStatusEnum s);

    @Modifying
    @Query(value = "UPDATE proprietario SET telefone = :phone WHERE id = :id", nativeQuery = true)
    int updateOwnerPhone(@Param("id") UUID id, @Param("phone") String newPhone);

    @Modifying
    @Query(value = "UPDATE proprietario SET status = :status WHERE id = :id AND status = 'ativo'", nativeQuery = true)
    int updateOwnerStatus(@Param("id") UUID id, @Param("status") String status);
}