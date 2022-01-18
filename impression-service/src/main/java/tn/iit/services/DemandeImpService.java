package tn.iit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tn.iit.dto.Enseignant;
import tn.iit.dto.Matiere;
import tn.iit.models.DemandeImpression;
import tn.iit.repositories.DemandeImpressionRepo;

@Service
public class DemandeImpService {

	private DemandeImpressionRepo demandeImpressionRepo;
	private final RestTemplate restTemplate;

	@Autowired
	public DemandeImpService(DemandeImpressionRepo demandeImpService, RestTemplate restTemplate) {
		this.demandeImpressionRepo = demandeImpService;
		this.restTemplate = restTemplate;
	}

	public List<DemandeImpression> getAllDemandesImp() {
		return demandeImpressionRepo.findAll();
	}

	public Optional<DemandeImpression> getDemandeById(long id) {
		return demandeImpressionRepo.findById(id);
	}

	public List<DemandeImpression> getDemandeByEnsId(long id) {
		return demandeImpressionRepo.findByIdEnseignant(id);
	}

	public DemandeImpression saveDemandeImp(DemandeImpression demandeImp) {
		return demandeImpressionRepo.save(demandeImp);
	}

	public Matiere getMatiere(long id) {
		Matiere matiere = restTemplate.getForObject("http://ENSEIGNANT-SERVICE/enseignant-service/matiere/" + id, Matiere.class);
		if (matiere != null)
			return matiere;
		return null;
	}

	public Enseignant getEnseignant(long id) {
		Enseignant matiere = restTemplate.getForObject("http://ENSEIGNANT-SERVICE/enseignant-service/enseignants/id/" + id,
				Enseignant.class);
		if (matiere != null)
			return matiere;
		return null;
	}

}
