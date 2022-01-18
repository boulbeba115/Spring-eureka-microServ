package tn.iit.web;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.dto.AuthResponse;
import tn.iit.dto.AuthResponse.Role;
import tn.iit.dto.UtilisateurDto;
import tn.iit.models.Enseignant;
import tn.iit.models.Groupe;
import tn.iit.models.Matiere;
import tn.iit.services.EnseignantService;

@RestController
@RequestMapping("enseignants")
@CrossOrigin(origins = "*")
public class EnseignantController {

	private final EnseignantService enseignantService;

	public EnseignantController(final EnseignantService enseignantService) {
		this.enseignantService = enseignantService;
	}

	@GetMapping
	public List<Enseignant> getAllEnseignants() {
		return enseignantService.getAllEnseignants();
	}

	@GetMapping("id/{id}")
	public Optional<Enseignant> findEnseignantById(@PathVariable long id) {
		return enseignantService.findEnseignantById(id);
	}

	@GetMapping("cin/{cin}")
	public Optional<Enseignant> findEnseignantByCin(@PathVariable String cin) {
		return enseignantService.findEnseignantByCin(cin);
	}

	@PostMapping
	public Enseignant saveEnseignant(@RequestBody Enseignant enseignant) {
		return enseignantService.saveEnseignant(enseignant);
	}

	@DeleteMapping("{id}")
	public void deleteEns(long id) {
		enseignantService.deleteEns(id);
	}

	@GetMapping("matiere-list/{id}")
	public HashSet<Matiere> getEnseignantMatiere(@PathVariable long id) {
		Optional<Enseignant> enseignantOptional = enseignantService.findEnseignantById(id);
		if (enseignantOptional.isPresent()) {
			return new HashSet<Matiere>(enseignantOptional.get().getGroupes().stream().map((item) -> {
				return item.getMatiere();
			}).collect(Collectors.toList()));
		}
		return null;
	}

	@GetMapping("/matiere-groupes/{idEns}/{idMat}")
	public HashSet<Groupe> getEnseignantMatiere(@PathVariable long idEns, @PathVariable long idMat) {
		Optional<Enseignant> enseignantOptional = enseignantService.findEnseignantById(idEns);
		if (enseignantOptional.isPresent()) {
			return new HashSet<Groupe>(enseignantOptional.get().getGroupes().stream().map((item) -> {
				if (item.getMatiere().getId() == idMat)
					return item.getGroupe();
				return null;
			}).filter(Objects::nonNull).collect(Collectors.toList()));
		}
		return null;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody UtilisateurDto utilisateurDto) {
		Boolean isLogged = enseignantService.login(utilisateurDto);
		if (!isLogged)
			return new ResponseEntity<String>("Check Your Credential Please", HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<AuthResponse>(new AuthResponse(utilisateurDto.getCin(), Role.ENSEINGANT),
				HttpStatus.OK);
	}

}
