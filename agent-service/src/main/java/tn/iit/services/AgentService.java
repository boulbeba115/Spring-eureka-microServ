package tn.iit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.iit.dto.UtilisateurDto;
import tn.iit.models.Agent;
import tn.iit.repositories.AgentRepository;

@Service
public class AgentService {

	private final AgentRepository agentRepository;

	@Autowired
	public AgentService(AgentRepository agentRepository) {
		this.agentRepository = agentRepository;
	}

	public List<Agent> getAllAgents() {
		return agentRepository.findAll();
	}

	public Optional<Agent> findAgentById(long id) {
		return agentRepository.findById(id);
	}

	public boolean login(UtilisateurDto utilisateurDto) {
		final Optional<Agent> agent = agentRepository.findByCin(utilisateurDto.getCin());
		if (!agent.isPresent())
			return false;
		if (utilisateurDto.getPassword().equals(agent.get().getPassword()))
			return true;
		return false;
	}
}
