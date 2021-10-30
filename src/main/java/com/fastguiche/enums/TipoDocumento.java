package com.fastguiche.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerador com todos estados do Brasil
 * 
 * @author João Eduardo Rosa da Fonseca
 */
public enum TipoDocumento {
	RG("rg"),
	CPF("cpf");

	private String nome;

	private TipoDocumento(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public static List<String> toStringList() {
		TipoDocumento[] tipos = TipoDocumento.values();
		List<String> TiposDeDocumento = new ArrayList<>();
		for (TipoDocumento tipo : tipos) {
			TiposDeDocumento.add(tipo.getNome());
		}
		return TiposDeDocumento;
	}
}