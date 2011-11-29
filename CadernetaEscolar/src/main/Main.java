package main;

import gui.Autenticacao;
import gui.CadastraDisciplina;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * 
 * @author Fernando Brito			<fernando@lavid.ufpb.br>
 * @author Jansepetrus Brasileiro	<jansepetrus@lavid.ufpb.br>
 *
 */
public class Main {
	//Vari�veis para a manipula��o de arquivo.
	static DataOutputStream outputSenha = null;
    static DataInputStream  inputSenha  = null;
    
    //Vari�veis para verifica��o do estado do programa.
    private static boolean primeiroAcesso;
    
    
	/**
	 * M�todo Inicial da Aplica��o.
	 * 
	 * @param args Argumentos da linha de comando
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//Verifica se o arquivo existe e, se n�o existir, cria um arquivo para armazenar a senha.
		if ( !arquivoExiste() ) {
			criaArquivoDeSenha();
			JOptionPane.showMessageDialog(null, "Primeiro acesso ao programa.\nVoc� deve cadastrar uma senha.");
			primeiroAcesso = true;
		} else if ( estaVazio() ) {
			JOptionPane.showMessageDialog(null, "Primeiro acesso ao programa.\nVoc� deve cadastrar uma senha.");
			primeiroAcesso = true;
		} else {
			JOptionPane.showMessageDialog(null, "Autentique-se");
			primeiroAcesso = false;
		}
		
		Autenticacao autenticacao = Autenticacao.showDialog(primeiroAcesso);

		autenticacao.setDefaultCloseOperation(Autenticacao.DISPOSE_ON_CLOSE);
	}


	/**
	 * Verifica se o arquivo que cont�m a senha existe.
	 * 
	 * @return <code>true</code> se o arquivo existir.<br><code>false</code> se o arquivo n�o existir.
	 */
	private static boolean arquivoExiste() {
			try {
				inputSenha = new DataInputStream(new FileInputStream("senha.dat"));
			} catch (FileNotFoundException e) {
				System.err.println("O arquivo com a senha n�o existe.");
				return false;
			}
			return true;
	}
	
	/**
	 * Cria um Arquivo para armazenar a senha, caso ele n�o exista.
	 */
	private static void criaArquivoDeSenha() {
		System.out.println("Criando um arquivo para armazenar a senha...");
		try {
			outputSenha = new DataOutputStream(new FileOutputStream("senha.dat",false));
		} catch (FileNotFoundException e) {
//			input.close();
//			output.close();
			System.err.println("O Arquivo n�o p�de ser criado, acessado ou sobrescrito!\n");
			System.exit(1);
		}
	}
	
	/**
	 * Verifica se o arquivo com a senha est� vazio
	 * 
	 * @return <code>true</code> se o arquivo de estiver vazio.<br><code>false</code> se algo estiver gravado nele.
	 */
	private static boolean estaVazio() {
		try {
			inputSenha.readUTF();
			return false;
		} catch (IOException e) {
			return true;
		}
	}

}