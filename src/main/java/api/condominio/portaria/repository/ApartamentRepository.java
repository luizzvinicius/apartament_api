package api.condominio.portaria.repository;

import api.condominio.portaria.models.Apartament;
import api.condominio.portaria.models.embeddable.ApartamentNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartamentRepository extends JpaRepository<Apartament, ApartamentNumber> {
}