
package br.com.agsolutio.wschecklist.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
@Table(name = "consultant", uniqueConstraints = @UniqueConstraint(columnNames = "matricula"))
public class Consultant implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long idConsultalt;

    @NotNull
    @Size(min = 1, max = 100)
    @Pattern(regexp = "[^0-9]*", message = "Nome não pode conter números")
    private String nome;

    @NotNull
    @NotEmpty
    private String matricula;
    
    @NotNull
    @NotEmpty
    private String senha;
    
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "idSetor")
    Sector setor;


	public Long getIdConsultalt() {
		return idConsultalt;
	}


	public void setIdConsultalt(Long idConsultalt) {
		this.idConsultalt = idConsultalt;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public Sector getSetor() {
		return setor;
	}


	public void setSetor(Sector setor) {
		this.setor = setor;
	}

    
	
}
