package tn.iit.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.iit.models.DemandeImpression.EtatDemande;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeImpressionDto {

	private long id;
	private Enseignant enseignant;
	private Matiere matiere;
	private int nbCopie;
	private Date dateArrivage;
	private Date tempArrivage;
	private String description;
	private String ficherPdf;
	private EtatDemande etatDemande;
}
