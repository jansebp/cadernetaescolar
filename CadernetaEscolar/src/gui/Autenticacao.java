package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import utils.PasswordUtils;

/**
 * Tela para Autenticação
 * 
 * @author Fernando Brito			< fernando@lavid.ufpb.br	>
 * @author Jansepetrus Brasileiro	< jansepetrus@lavid.ufpb.br	>
 *
 */
@SuppressWarnings("serial")
public class Autenticacao extends JDialog {
	//Variáveis para a manipulação do arquivo de senha.
	static DataOutputStream outputSenha = null;
	static DataInputStream  inputSenha  = null;

	//Variável para verificação do estado do programa.
	public static boolean primeiroAcesso = false;

	//Variáveis para a GUI, usadas no Construtor.
	private static	String			senhaDigitada;
	private static	JPasswordField 	passwordField;
	private 		JLabel 			lblSenha;
	private final 	JPanel 			jPanel = new JPanel();


	/**
	 * Construtor da classe. Cria o Frame.
	 * 
	 * @param parent A tela pai.
	 */
	public Autenticacao(JFrame parent){
		this(parent, primeiroAcesso);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**
	 * Construtor da classe. Cria o Frame.
	 * 
	 * @param parent A tela pai.
	 * @param primeiroAcesso <code>true</code> se for o primeiro acesso ao programa. <code>false</code> caso já tenha acessado o programa.
	 * @wbp.parser.constructor
	 */
	public Autenticacao(JFrame parent, final boolean primeiroAcesso) {
		super(parent, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setTitle("Tela de Autenticação");
		setResizable(false);
		setBounds(100, 100, 197, 100);

		getContentPane().setLayout(new BorderLayout());

		jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		lblSenha 	  = new JLabel("Digite a Senha de Usuário:");
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		jPanel.setLayout(new BorderLayout(0, 0));

		jPanel.add(lblSenha, BorderLayout.NORTH);
		jPanel.add(passwordField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);

		buttonPanel.add(okButton);
		getContentPane().add(jPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
		//Fim da criação do Frame.

		setLocationRelativeTo(null);	

		//Tratamento do Botão de OK.
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				senhaDigitada = PasswordUtils.calculateMD5Hash(new String(passwordField.getPassword()));

				//Se for o primeiro acesso, Cadastra uma senha. Se não for, Autentica.
				if( primeiroAcesso ) {
					cadastraSenha();
				} else {
					if ( autenticado() ){
						setVisible(false);
						new CadernetaApp().setVisible(true);
					}
				}
				setVisible(false);
			}
		});//Fim do Tratamento do Botão OK.
	}


	/**
	 * Método inicial da Aplicação
	 */
	public static void main(String[] args) {

		try {
			Autenticacao dialog = new Autenticacao(new JFrame());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e){
					System.exit(0);
				}
			});
			//			dialog.setVisible(true);
			//			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Exibe o Dialog. Se o usuário não se autenticar, a aplicação será encerrada.
	 * 
	 * @param primeiroAcesso <code>true</code> se for o primeiro acesso ao programa.</br><code>false</code> caso contrário.
	 * @return Retorna a tela criada.
	 */
	public static Autenticacao showDialog(){
		//Verifica se o arquivo existe e, se não existir, cria um arquivo para armazenar a senha.
		if ( !arquivoExiste(inputSenha) ) {
			criaArquivoDeSenha(outputSenha);
			primeiroAcesso = true;
			JOptionPane.showMessageDialog(null, "Primeiro acesso ao programa.\nVocê deve cadastrar uma senha.");
		} else {
			if ( estaVazio(inputSenha) ) {
				primeiroAcesso = true;
				JOptionPane.showMessageDialog(null, "Primeiro acesso ao programa.\nVocê deve cadastrar uma senha.");
			} else {
				JOptionPane.showMessageDialog(null, "Autentique-se!");
			}
		}

		Autenticacao autenticacao = new Autenticacao(null, primeiroAcesso);
		autenticacao.setVisible(true);

		return autenticacao;
	}


	/**
	 * Verifica se o arquivo que contém a senha existe.
	 * 
	 * @param input Caso o arquivo exista, será associado ao parâmetro.
	 * @return <code>true</code> se o arquivo existir.<br><code>false</code> se o arquivo não existir.
	 */
	private static boolean arquivoExiste(DataInputStream input) {
		try{
			System.out.println("Verificando se é o primeiro acesso ao programa.");
			input = new DataInputStream(new java.io.FileInputStream("senha.dat"));
		} catch (java.io.FileNotFoundException e) {
			return false;
		}
		System.out.println("Arquivo de senha encontrado. Iniciando a Autenticação.");
		return true;
	}

	/**
	 * Cria um Arquivo para armazenar a senha.
	 * 
	 * @param output Associado o arquivo criado ao parâmetro.
	 */
	private static void criaArquivoDeSenha(DataOutputStream output) {
		System.out.println("Criando um arquivo para armazenar a senha.");

		try {
			output = new DataOutputStream(new FileOutputStream("senha.dat",false));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "O Arquivo não pôde ser criado, acessado ou sobrescrito!");
			System.exit(1);
		}
	}

	/**
	 * Verifica se o arquivo com a senha está vazio
	 * 
	 * @param input Arquivo a ser verificado
	 * @return <code>true</code> se o arquivo de estiver vazio.<br><code>false</code> se algo estiver gravado nele.
	 */
	private static boolean estaVazio(DataInputStream input) {
		try {
			input = new DataInputStream(new java.io.FileInputStream("senha.dat"));
			input.readUTF();
			return false;
		} catch (IOException e) {
			System.out.println("O arquivo está vazio.");
			return true;
		}
	}

	/**
	 * Faz o cadastro da senha do usuário, em caso de primeiro acesso ao programa.
	 */
	public static void cadastraSenha() {
		try {
			DataOutputStream output = new DataOutputStream(new FileOutputStream("senha.dat",false));
			output.writeUTF(senhaDigitada);
			JOptionPane.showMessageDialog(null, "Senha cadastrada com sucesso!");
		} catch (IOException e) {
			System.err.println("Erro durante o cadastro da senha.\n" + e.toString());
		}
	}

	/**
	 * Abre a Interface Gráfica de Autenticação
	 */
	private static boolean autenticado(){
		DataInputStream inputSenha = null;
		try {
			inputSenha = new DataInputStream(new FileInputStream("senha.dat"));
			if ( inputSenha.readUTF().equals(senhaDigitada) )
			{
				JOptionPane.showMessageDialog(null, "Autenticado!");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Senha inválida.\nFechando a aplicação");
				System.err.println("Senha inválida.\nFechando a aplicação");
				System.exit(1);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ocorreu algum erro na Autenticação.\nA aplicação será fechada.");
			System.exit(1);
		}
		return false;		
	}
}