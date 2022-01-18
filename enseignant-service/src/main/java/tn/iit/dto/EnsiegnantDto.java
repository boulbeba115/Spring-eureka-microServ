package tn.iit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnsiegnantDto {
	private long id;
	private String nom;
	private String prenom;
	private String email;
	private String cin;
}
