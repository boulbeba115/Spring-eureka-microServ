package tn.iit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.iit.models.Matiere;
import tn.iit.repositories.MatiereRepository;

@Service
public class MatiereService {

	private final MatiereRepository matiereRepository;

	@Autowired
	public MatiereService(MatiereRepository matiereRepository) {
		this.matiereRepository = matiereRepository;
	}

	public List<Matiere> getAllMatiere() {
		return matiereRepository.findAll();
	}

	public Optional<Matiere> getMatiereById(long id) {
		return matiereRepository.findById(id);
	}

}
