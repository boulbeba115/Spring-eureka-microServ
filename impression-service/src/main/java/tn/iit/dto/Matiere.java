package tn.iit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Matiere {

	public enum TypeMatiere {
		COUR, TP, TD
	}

	private long id;

	private String label;
	private TypeMatiere typeMatiere;

}
