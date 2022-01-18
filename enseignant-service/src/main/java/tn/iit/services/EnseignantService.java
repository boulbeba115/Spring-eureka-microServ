package tn.iit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.iit.dto.UtilisateurDto;
import tn.iit.models.Enseignant;
import tn.iit.repositories.EnseignantRepository;

@Service
public class EnseignantService {

	private final EnseignantRepository enseignantRepo;

	@Autowired
	public EnseignantService(final EnseignantRepository enseignantRepo) {
		this.enseignantRepo = enseignantRepo;
	}

	public List<Enseignant> getAllEnseignants() {
		return enseignantRepo.findAll();
	}

	public Optional<Enseignant> findEnseignantById(long id) {
		return enseignantRepo.findById(id);
	}

	public Optional<Enseignant> findEnseignantByCin(String cin) {
		return enseignantRepo.findByCin(cin);
	}

	public Enseignant saveEnseignant(Enseignant enseignant) {
		return enseignantRepo.save(enseignant);
	}

	public void deleteEns(long id) {
		enseignantRepo.deleteById(id);
	}

	public boolean login(UtilisateurDto utilisateurDto) {
		final Optional<Enseignant> ens = enseignantRepo.findByCin(utilisateurDto.getCin());
		if (!ens.isPresent())
			return false;
		if (utilisateurDto.getPassword().equals(ens.get().getPassword()))
			return true;
		return false;
	}
}
