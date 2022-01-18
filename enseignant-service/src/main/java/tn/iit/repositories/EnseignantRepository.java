package tn.iit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.models.Enseignant;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

	Optional<Enseignant> findByCin(String cin);

}
