package main;

import gui.Autenticacao;
import gui.CadernetaApp;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author Fernando Brito			<fernando@lavid.ufpb.br>
 * @author Jansepetrus Brasileiro	<jansepetrus@lavid.ufpb.br>
 *
 */
public class Main {

	public static void main(String[] args) {
		//Seta a aparencia da janela.
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			System.err.println(	"Ocorreu algum erro na obten��o da Skin.\n" +
					"O programa ser� executado na Skin padr�o.");
		}

		//Faz a autentica��o do usu�rio
		autentica();

		//Se ocorrer tudo bem, continua a execu��o do programa e abre a Tela Inicial. 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadernetaApp frame = new CadernetaApp();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e){
							System.exit(0);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Abre a Interface Gr�fica de Autentica��o
	 */
	private static void autentica(){
		Autenticacao autenticacao = Autenticacao.showDialog();
	}

}