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

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe que representa a entidade ask 
 * 
 * @author "Alcélio Gomes {@link doalcelio@gmail.com}"
 * 
 * @since 15/06/2017
 *
 */
@Entity
@XmlRootElement
@Table(name = "ask")
public class Ask implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long idAsk;

    @NotNull
    @Size(min = 1, max = 200)
    private String pergunta;
    
    private boolean amarelo = false;
    
    private boolean vermelho = false;
    
    private boolean verde = false;

	public Long getIdAsk() {
		return idAsk;
	}

	public void setIdAsk(Long idAsk) {
		this.idAsk = idAsk;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public boolean isAmarelo() {
		return amarelo;
	}

	public void setAmarelo(boolean amarelo) {
		this.amarelo = amarelo;
	}

	public boolean isVermelho() {
		return vermelho;
	}

	public void setVermelho(boolean vermelho) {
		this.vermelho = vermelho;
	}

	public boolean isVerde() {
		return verde;
	}

	public void setVerde(boolean verde) {
		this.verde = verde;
	}

}
