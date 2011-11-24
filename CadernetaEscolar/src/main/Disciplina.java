package main;

@SuppressWarnings ("unused")
public class Disciplina {
	private int qtd_avaliacoes;
	private int carga_horaria;
	
	private String nome;
	
	public Disciplina(String nome, int carga_horaria) {
		this.nome = nome;
		this.carga_horaria = carga_horaria;
		
		if (carga_horaria == 30) this.qtd_avaliacoes = 2;
		else if (carga_horaria >= 40 && carga_horaria <= 50) this.qtd_avaliacoes = 3;
		else if (carga_horaria > 60) this.qtd_avaliacoes = 4;
		
	}

	public int getQtdAvaliacoes() {
		return this.qtd_avaliacoes;
	}
	
	public int getCargaHoraria() {
		return this.carga_horaria;
	}
}
