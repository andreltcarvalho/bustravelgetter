package com.fastguiche.enums;

/**
 * Enumerador com todos estados do Brasil
 * 
 * @author João Eduardo Rosa da Fonseca
 */
public enum TipoViagem {

	IDA("Ida"),
	IDAEVOLTA("Ida e Volta");
	
	private String nome;

	public String getNome() {
		return nome;
	}
	private TipoViagem(String nome) {
		this.nome = nome;
	}

	public boolean isIdaVolta() {
		
		
		return false;
	}
}