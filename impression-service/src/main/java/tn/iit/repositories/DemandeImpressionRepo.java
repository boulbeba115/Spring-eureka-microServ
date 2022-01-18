package tn.iit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.models.DemandeImpression;

public interface DemandeImpressionRepo extends JpaRepository<DemandeImpression, Long>{

	List<DemandeImpression> findByIdEnseignant(long id);

}
