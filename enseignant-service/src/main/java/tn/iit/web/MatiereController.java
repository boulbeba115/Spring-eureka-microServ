package tn.iit.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.models.Matiere;
import tn.iit.services.MatiereService;

@RestController
@RequestMapping("matiere")
public class MatiereController {

	private final MatiereService matiereService;

	public MatiereController(MatiereService matiereService) {
		this.matiereService = matiereService;
	}

	@GetMapping
	public List<Matiere> getAllMatieres() {
		return matiereService.getAllMatiere();
	}

	@GetMapping("{id}")
	public Matiere getMatiereById(@PathVariable long id) {
		Optional<Matiere> optionalMatiere = matiereService.getMatiereById(id);
		if (optionalMatiere.isPresent())
			return optionalMatiere.get();
		return null;
	}

}
