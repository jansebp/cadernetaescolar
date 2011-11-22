package main;

import exceptions.CadernetaException;


public class Aluno {
	public static final double NOTA_NECESSARIA_APROVACAO = 7;
	public static final double NOTA_NECESSARIA_FINAL = 4;
	public static final double PERCENTAGEM_MAXIMA_FALTA = 0.25; 
	  
	private String matricula;
	
	private String nome;
	private int ano, periodo, curso, id;
	
	private Disciplina disciplina;
	
	private double[] notas = new double[4];
	private int i_notas = 0;
	
	private double nota_final;
	private boolean realizou_final = false;
	
	private int qtd_faltas = 0;
	
	// Construtor 
	public Aluno(String matricula, Disciplina disciplina) {
		// TODO Parseia a matrícula
		
		this.disciplina = disciplina;
	}
	
	
	// Métodos referentes a cálculo de médias
	public double calculaMediaParcial() {
		if (this.getQtdAvaliacoesRealizadas() == 0) return 0;
		
		return this.somaNotas() / this.getQtdAvaliacoesRealizadas();
	}
	
	public double calculaMediaFinal() {
		// MF = (MP*6 + AF*4)/10
		return ((this.calculaMediaParcial() * 6) + (this.nota_final * 4))/10;
	}
	
	public double calculaNotaFinalNecessaria() {
		// MF = (MP*6 + AF*4)/10
		// 5 * 10 = 6*MP + 4*AF 
		
		return (50 - 6 * this.calculaMediaParcial())/4;
	}

	
	// Métodos referentes a adição e leitura de atributos
	public int getQtdAvaliacoesRealizadas() {
		return this.i_notas;
	}
	
	public void setQtdFaltas(int qtd_faltas) throws CadernetaException {
		this.validaQtdFaltas(qtd_faltas);
		
		this.qtd_faltas = qtd_faltas;
	}
	
	public void adicionaNota(double nota) throws CadernetaException {
		this.validaNota(nota);
		this.validaQtdMaxNotas();
		
		this.notas[i_notas++] = nota;
	}
	
	public void adicionaNotaFinal(double nota) throws CadernetaException {
		this.validaNota(nota);
		this.validaSeFezTodasAsParciais();
		
		this.nota_final = nota;
		this.realizou_final = true;
	}
	

	public String verificaSituacao() {
		// Se tem mais faltas que o permitido
		if (this.reprovadoPorFalta()) {
			return "Reprovado por Falta";
					
		// Se já fez a prova final
		} else if (this.realizou_final) { 
			if (this.calculaMediaFinal() >= 5) return "Aprovado";
			else return "Reprovado";
			
		// Se ainda não fez a prova final e nem fez todas as provas
		} else if (this.getQtdAvaliacoesRealizadas() < this.disciplina.getQtdAvaliacoes())	
			return "Matriculado";
		
		// Se já fez todas as provas
		else {
			if (this.calculaMediaParcial() >= NOTA_NECESSARIA_APROVACAO) 
				return "Aprovado";
			else if (this.calculaMediaParcial() < NOTA_NECESSARIA_APROVACAO && this.calculaMediaParcial() >= NOTA_NECESSARIA_FINAL)
				return "Final";
			else
				return "Reprovado";
		}
	}
	
	
	// Métodos internos	
	private boolean reprovadoPorFalta() {
		if (this.qtd_faltas >= this.disciplina.getCargaHoraria() * PERCENTAGEM_MAXIMA_FALTA) return true;
		else return false;
	}

	private double somaNotas() {
		double total = 0;
		
		for(int i = 0; i < i_notas; i++) total += notas[i];
		
		return total;
	}
	
	
	// Métodos de validação
	private void validaNota(double nota) {
		if (nota < 0 || nota > 10) 
			throw new CadernetaException("Nota deve estar entre 0 e 10.");
	}
	
	private void validaQtdFaltas(int qtd_faltas) {
		if (qtd_faltas < 0 || qtd_faltas > this.disciplina.getCargaHoraria()) 
			throw new CadernetaException("Quantidade de faltas deve estar entre 0 e carga horária da disciplina.");
	}
	
	private void validaQtdMaxNotas() {
		if (this.getQtdAvaliacoesRealizadas() == this.disciplina.getQtdAvaliacoes())
			throw new CadernetaException("Quantidade máxima de avaliações antigida.");
	}
	
	private void validaSeFezTodasAsParciais() {
		if (this.getQtdAvaliacoesRealizadas() != this.disciplina.getQtdAvaliacoes())
			throw new CadernetaException("Aluno ainda não realizou todas as avaliações parciais.");
	}
}
