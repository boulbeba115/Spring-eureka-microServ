package tn.iit.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandeImpression {
	public enum EtatDemande{
		PRINTED,
		CONFIRMED,
		CANCELED,
		PENDING
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long idEnseignant;
	private long idMatiere;
	private int nbCopie;
	@Basic
	@Temporal(TemporalType.DATE)
	private Date dateArrivage;
	@Basic
	@Temporal(TemporalType.TIME)
	private Date tempArrivage;
	private String description;
	private String ficherPdf;
	private EtatDemande etatDemande = EtatDemande.PENDING;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(updatable = false)
	private Date createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	
	
}
