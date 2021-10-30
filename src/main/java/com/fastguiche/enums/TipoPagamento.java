package com.fastguiche.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerador com todos estados do Brasil
 * 
 * @author João Eduardo Rosa da Fonseca
 */
public enum TipoPagamento {
	PIX("pix"),

	TRANSFERENCIA("transferencia"),

	CARTAO("cartao");

	private String nome;

	private TipoPagamento(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public static List<String> toStringList() {
		TipoPagamento[] tipos = TipoPagamento.values();
		List<String> TiposDePagamento = new ArrayList<>();
		for (TipoPagamento tipo : tipos) {
			TiposDePagamento.add(tipo.getNome());
		}
		return TiposDePagamento;
	}
}