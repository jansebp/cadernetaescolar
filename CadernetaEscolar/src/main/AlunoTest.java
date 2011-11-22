package main;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import exceptions.CadernetaException;


public class AlunoTest {
	Disciplina disciplina;
	Aluno aluno;
	
	@Before
	public void prepara_classes() {
		disciplina = new Disciplina("LP1", 30);
		aluno = new Aluno("", disciplina);
	}

	@Test
	public void cadastro_de_notas_e_medias_parciais() {
		// Verifica vazio
		assertEquals(0, Double.compare(aluno.calculaMediaParcial(), 0));
		assertEquals(0, aluno.getQtdAvaliacoesRealizadas());
		
		aluno.adicionaNota(5);
		assertEquals(0, Double.compare(aluno.calculaMediaParcial(), 5));
		assertEquals(1, aluno.getQtdAvaliacoesRealizadas());
		
		aluno.adicionaNota(10);
		assertEquals(0, Double.compare(aluno.calculaMediaParcial(), 7.5));
		assertEquals(2, aluno.getQtdAvaliacoesRealizadas());
	}
	
	@Test
	public void previsao_nota_final() {	
		aluno.adicionaNota(5);
		aluno.adicionaNota(7);
		assertEquals(0, Double.compare(aluno.calculaNotaFinalNecessaria(), 3.5));
	}
	
	@Test
	public void situacao_aprovado_direto() {	
		assertEquals("Matriculado", aluno.verificaSituacao());
		
		aluno.adicionaNota(7);
		assertEquals("Matriculado", aluno.verificaSituacao());
		
		aluno.adicionaNota(7);
		assertEquals("Aprovado", aluno.verificaSituacao());
	}
	
	@Test
	public void situacao_reprovado_direto() {
		aluno.adicionaNota(3);
		aluno.adicionaNota(4);
		assertEquals("Reprovado", aluno.verificaSituacao());
	}

	@Test
	public void situacao_final_aprovado() {
		aluno.adicionaNota(4);
		aluno.adicionaNota(5);
		assertEquals("Final", aluno.verificaSituacao());
		
		aluno.adicionaNotaFinal(10);
		assertEquals("Aprovado", aluno.verificaSituacao());
	}

	@Test
	public void situacao_final_reprovado() {
		aluno.adicionaNota(4);
		aluno.adicionaNota(5);
		assertEquals("Final", aluno.verificaSituacao());
		
		aluno.adicionaNotaFinal(2);
		assertEquals("Reprovado", aluno.verificaSituacao());
	}
	
	@Test
	public void situacao_reprovado_falta() {
		assertEquals("Matriculado", aluno.verificaSituacao());
		
		aluno.setQtdFaltas(1);
		assertEquals("Matriculado", aluno.verificaSituacao());
		
		aluno.setQtdFaltas(15);
		assertEquals("Reprovado por Falta", aluno.verificaSituacao());
	}

	@Test(expected= CadernetaException.class)
	public void exception_qtd_max_notas() {
		aluno.adicionaNota(5);
		aluno.adicionaNota(5);
		aluno.adicionaNota(5);
	}
	
	@Test(expected= CadernetaException.class)
	public void exception_nota_invalida() {
		aluno.adicionaNota(11);
	}

	@Test(expected= CadernetaException.class)
	public void exception_nota_final_invalida() {
		aluno.adicionaNotaFinal(11);
	}

	@Test(expected= CadernetaException.class)
	public void exception_quantidade_faltas() {
		// Disciplina só permite 30
		aluno.setQtdFaltas(35);
	}

	@Test(expected= CadernetaException.class)
	public void exception_nao_fez_todas_as_parciais() {
		// Adicionar final antes de adicionar todas as parciais
		aluno.adicionaNotaFinal(8);
	}	
}
