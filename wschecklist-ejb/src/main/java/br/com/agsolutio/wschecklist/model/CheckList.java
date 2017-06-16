/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.agsolutio.wschecklist.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
@Table(name = "checklist")
public class CheckList implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long idCheckList;
    
    @NotNull
	@JoinColumn(name = "id")
    private Consultant consultor;

    @NotNull
    @NotEmpty
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRealizacao;
    
	@ManyToMany(fetch  =EAGER, cascade = ALL)
	@JoinTable(name = "checklist_realizado", joinColumns = { @JoinColumn(name = "idCheckList") }, inverseJoinColumns = {
			@JoinColumn(name = "idPergunta") })
	private Set<Ask> resultadosChacagem = new HashSet<Ask>();

	public Long getIdCheckList() {
		return idCheckList;
	}

	public void setIdCheckList(Long idCheckList) {
		this.idCheckList = idCheckList;
	}

	public Consultant getConsultor() {
		return consultor;
	}

	public void setConsultor(Consultant consultor) {
		this.consultor = consultor;
	}

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public Set<Ask> getResultadosChacagem() {
		return resultadosChacagem;
	}

	public void setResultadosChacagem(Set<Ask> resultadosChacagem) {
		this.resultadosChacagem = resultadosChacagem;
	}
    

	
}
