package tn.iit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	public enum Role {
		ENSEINGANT,
		Agent
	}
	private String cin;
	private Role role = Role.Agent;
}
