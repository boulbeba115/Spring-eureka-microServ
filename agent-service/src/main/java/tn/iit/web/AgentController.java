package tn.iit.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.dto.AuthResponse;
import tn.iit.dto.UtilisateurDto;
import tn.iit.dto.AuthResponse.Role;
import tn.iit.models.Agent;
import tn.iit.services.AgentService;

@RestController
@RequestMapping("agents")
@CrossOrigin
public class AgentController {

	private final AgentService agentService;

	public AgentController(AgentService agentService) {
		super();
		this.agentService = agentService;
	}

	@GetMapping
	public List<Agent> getAllAgents() {
		return agentService.getAllAgents();
	}

	@GetMapping("/{id}")
	public Optional<Agent> findAgentById(long id) {
		return agentService.findAgentById(id);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody UtilisateurDto utilisateurDto) {
		Boolean isLogged = agentService.login(utilisateurDto);
		if (!isLogged)
			return new ResponseEntity<String>("Check Your Credential Please", HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<AuthResponse>(new AuthResponse(utilisateurDto.getCin(), Role.ENSEINGANT),
				HttpStatus.OK);
	}
}
