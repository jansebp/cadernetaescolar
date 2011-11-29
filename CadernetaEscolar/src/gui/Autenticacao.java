package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
 * Tela para Autentica��o
 * 
 * @author Fernando Brito			<fernando@lavid.ufpb.br>
 * @author Jansepetrus Brasileiro	<jansepetrus@lavid.ufpb.br>
 *
 */
@SuppressWarnings("serial")
public class Autenticacao extends JDialog {
	//Vari�veis para a manipula��o de arquivo.
	static DataOutputStream outputSenha = null;
    static DataInputStream  inputSenha  = null;
    
    private static boolean primeiroAcesso;

    //Vari�veis para a GUI, usadas no Construtor.
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
	 * @param primeiroAcesso <code>true</code> se for o primeiro acesso ao programa. <code>false</code> caso j� tenha acessado o programa.
	 */
	public Autenticacao(JFrame parent, final boolean primeiroAcesso) {
		super(parent, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setTitle("Tela de Autentica��o");
		setResizable(false);
		setBounds(100, 100, 197, 100);
		
		getContentPane().setLayout(new BorderLayout());
		
		jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		lblSenha 	  = new JLabel("Digite a Senha de Usu�rio:");
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		jPanel.setLayout(new BorderLayout(0, 0));
		
		jPanel.add(lblSenha, BorderLayout.NORTH);
		jPanel.add(passwordField);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JButton okButton = new JButton("OK");
		
		//Tratamento do Bot�o de OK.
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				senhaDigitada = PasswordUtils.calculateMD5Hash(new String(passwordField.getPassword()));
				if(primeiroAcesso) {
					cadastraSenha();
				} else {
					autentica();
				}
				setVisible(false);
			}
		});//Fim do Tratamento do Bot�o OK.
		
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		
		buttonPanel.add(okButton);
		getContentPane().add(jPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
		
		this.primeiroAcesso = primeiroAcesso;
	}
	
	
	/**
	 * M�todo inicial da Aplica��o.
	 * 
	 * @param args Argumentos da Linha de Comando.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//Inicia a Interface Gr�fica da Tela de Autentica��o.
		try {
			Autenticacao dialog = new Autenticacao(new JFrame(), primeiroAcesso);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e){
					System.exit(0);
				}
			});
			dialog.setVisible(true);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Informa se � o primeiro acesso do usu�rio
	 * @return O valor booleano da vari�vel <b>primeiroAcesso</b>, indicando se � ou n�o o primeiro acesso do usu�rio ao programa.
	 */
	public boolean ehPrimeiroAcesso(){
		return primeiroAcesso;
	}
	
	/**
	 * Exibe o Dialog. Se o usu�rio n�o se autenticar, a aplica��o ser� encerrada.
	 * 
	 * @return Retorna a tela criada.
	 */
	public static Autenticacao showDialog(){
		return showDialog(false);
	}
	
	/**
	 * Exibe o Dialog. Se o usu�rio n�o se autenticar, a aplica��o ser� encerrada.
	 * 
	 * @param primeiroAcesso <code>true</code> se for o primeiro acesso ao programa.</br><code>false</code> caso contr�rio.
	 * @return Retorna a tela criada.
	 */
	public static Autenticacao showDialog(boolean primeiroAcesso){
	    try {
			inputSenha  = new DataInputStream(new FileInputStream("senha.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Autenticacao autenticacao = new Autenticacao(null, primeiroAcesso);
		autenticacao.setVisible(true);
	return autenticacao;
	}
	
	/**
	 * Faz o cadastro da senha do usu�rio, em caso de primeiro acesso ao programa.
	 */
	public static void cadastraSenha() {
		try {
			outputSenha = new DataOutputStream(new FileOutputStream("senha.dat",false));
			outputSenha.writeUTF(senhaDigitada);
			JOptionPane.showMessageDialog(null, "Senha cadastrada com sucesso!");
			System.out.println("Autenticando...");
			autentica();
		} catch (IOException e) {
			System.err.println("Erro durante o cadastro da senha.\n" + e.toString());
		}
	}
	
	/**
	 * Faz a autentica��o do usu�rio no programa.
	 */
	public static void autentica() { 
			try {
				if (inputSenha.readUTF().equals(senhaDigitada)){
					System.out.println("Autenticado! blabla");
					CadernetaApp caderneta = CadernetaApp.showDialog();
					caderneta.setDefaultCloseOperation(CadernetaApp.DISPOSE_ON_CLOSE);
				} else {
					;
				}
				
			} catch (HeadlessException e) {
				JOptionPane.showMessageDialog(null, "Senha inv�lida!");
				e.printStackTrace();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
