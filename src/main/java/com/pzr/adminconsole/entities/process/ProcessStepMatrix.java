package com.pzr.adminconsole.entities.process;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Process_Step_Matrix")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessStepMatrix {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private ProcessTemplate processTemplate;
	
	private String prevStep;
	private String currentStep;
	private String description;
}
