package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Tela do Aplicativo Caderneta Escolar
 * 
 * @author Fernando Brito			<fernando@lavid.ufpb.br>
 * @author Jansepetrus Brasileiro	<jansepetrus@lavid.ufpb.br>
 *
 */
@SuppressWarnings("serial")
public class CadernetaApp extends JFrame {
	//Variáveis para a GUI, usadas no Construtor.
	private 	JDesktopPane	desktopPane;
	private 	JMenuBar 		menuBar;
	private 	JMenu			menuDisciplina;
	private 	JMenu 			menuAlunos;
	private 	JMenu			menuRelatorios;
	private 	JMenuItem 		menuItemCadastroAlunos;
	private 	JMenuItem 		menuItemCadastroDisciplina;


	/**
	 * Construtor da Classe. Cria o Frame.
	 */
	public CadernetaApp() {
		setTitle("Caderneta escolar");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		
		menuBar = new JMenuBar();
		{
			menuDisciplina	= new JMenu("Gerenciamento de Disciplinas");
			{
				menuItemCadastroDisciplina = new JMenuItem("Cadastrar Disciplina");
			}
			menuAlunos 		= new JMenu("Gerenciamento de Alunos");
			{
				menuItemCadastroAlunos = new JMenuItem("Cadastro de Alunos");
			}
			menuRelatorios 	= new JMenu("Relatórios");

			menuDisciplina.add(menuItemCadastroDisciplina);
			menuAlunos.add(menuItemCadastroAlunos);
		}
		menuBar.add(menuDisciplina);
		menuBar.add(menuAlunos);
		menuBar.add(menuRelatorios);

		setJMenuBar(menuBar);
        
		//Usar isso ou tirar o pack() =P
		layout.setHorizontalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGap(0, 547, Short.MAX_VALUE)
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGap(0, 279, Short.MAX_VALUE)
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
		);
		//
		pack();
	}
    
	/**
	 * Método inicial da Aplicação.
	 * 
	 * @param args Argumentos da Linha de Comando.
	 */
	public static void main(String[] args) {
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
}
