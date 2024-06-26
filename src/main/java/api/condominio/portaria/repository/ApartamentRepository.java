package api.condominio.portaria.repository;

import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.models.Apartament;
import api.condominio.portaria.models.embeddable.ApartamentNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApartamentRepository extends JpaRepository<Apartament, ApartamentNumber> {
    Page<Apartament> findAllApartamentBynumAptoBlocoEqualsAndStatusEquals(Pageable p, String bloco, RecordStatusEnum s);

    Optional<Apartament> findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals(String bloco, String numApto, RecordStatusEnum s);
}