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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import utils.PasswordUtils;

/**
 * Tela para Autenticação
 * 
 * @author Fernando Brito			<fernando@lavid.ufpb.br>
 * @author Jansepetrus Brasileiro	<jansepetrus@lavid.ufpb.br>
 *
 */
@SuppressWarnings("serial")
public class Autenticacao extends JDialog {
	//Variáveis para a manipulação de arquivo.
	static DataOutputStream output = null;
    static DataInputStream  input  = null;

    //Variáveis para a GUI, usadas no Construtor.
    private static	String			senhaDigitada;
	private 		JPasswordField 	passwordField;
	private 		JLabel 			lblSenha;
	private final 	JPanel 			jPanel = new JPanel();

	
	/**
	 * Construtor da classe. Cria o Frame.
	 */
	public Autenticacao() {
		
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
		
		//Tratamento do Botão de OK.
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				senhaDigitada = new String (passwordField.getPassword());
				//Autenticação do Usuário.
				try {
					input = new DataInputStream(new FileInputStream("senha.dat"));
					String senha = input.readUTF();
					if (senha.equals(PasswordUtils.calculateMD5Hash(senhaDigitada))){
						JOptionPane.showMessageDialog(null, "Autenticado!");
						setVisible(false);
						input.close();
						//Inicia o Aplicativo da Caderneta Escolar.
						new CadernetaApp().setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "A senha digitada não é a mesma registrada no servidor.\nTente novamente.");
						passwordField.setText("");
					}
				} catch (IOException e1) {
					System.err.println("Não foi possível iniciar a autenticação");
				}
			}
		});//Fim do Tratamento do Botão OK.
		
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		
		buttonPanel.add(okButton);
		getContentPane().add(jPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}
	
	
	/**
	 * Método inicial da Aplicação.
	 * 
	 * @param args Argumentos da Linha de Comando.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//Verifica se o arquivo com a senha existe e, caso não exista, cria um e cadastra a senha.
		try {
			input = new DataInputStream(new FileInputStream("senha.dat"));
		} catch (FileNotFoundException e) {
			System.err.println(e.toString() + "\nCriando um arquivo para armazenar a senha...\n\n");
			try {
				output = new DataOutputStream(new FileOutputStream("senha.dat",false));
			} catch (FileNotFoundException e2) {
				input.close();
				output.close();
				System.err.println("O Arquivo não pôde ser criado, acessado ou sobrescrito!\n");
				System.exit(1);
			}
			
			String senha = JOptionPane.showInputDialog("Primeiro acesso ao programa.\nVocê deve cadastrar uma senha de usuário.");
			try {
				output.writeUTF(PasswordUtils.calculateMD5Hash(senha));
				JOptionPane.showMessageDialog(null, "Senha cadastrada com sucesso!\n Digite a mesma senha para autenticar-se no servidor.");
				output.close();
			} catch (IOException e3) {
				System.err.println("Erro durante o cadastro da senha.\n" + e3.toString());
				output.writeUTF(PasswordUtils.calculateMD5Hash(senha));
				input.close();
				output.close();
			}
		}
		
		//Inicia a Interface Gráfica da Tela de Autenticação.
		try {
			Autenticacao dialog = new Autenticacao();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e){
					System.exit(0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
