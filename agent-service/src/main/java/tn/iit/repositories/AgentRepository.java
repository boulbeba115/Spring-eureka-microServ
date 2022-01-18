package tn.iit.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.models.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {

	Optional<tn.iit.models.Agent> findByCin(String cin);

}
