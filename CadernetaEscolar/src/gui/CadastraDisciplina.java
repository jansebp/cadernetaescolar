package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * Tela de Cadastro de Disciplina
 * 
 * @author Fernando Brito			<fernando@lavid.ufpb.br>
 * @author Jansepetrus Brasileiro	<jansepetrus@lavid.ufpb.br>
 *
 */
@SuppressWarnings("serial")
public class CadastraDisciplina extends JInternalFrame {
	private	JLabel			lblNome;
	private JLabel			lblCargaHoraria;
	private	JTextField 		textField;
	private	JList<Object>	listaCargaHoraria;
	private	JPanel			buttonPanel;
	private JButton 		buttonCadastrar;
	/**
	 * Construtor da Classe. Cria o Frame.
	 */
	public CadastraDisciplina() {
		setTitle("Cadastrar Disciplina");
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 352, 166);
		getContentPane().setLayout(null);
		
		lblNome = new JLabel("Nome da Disciplina:");
		lblNome.setBounds(10, 10, 115, 15);
		lblCargaHoraria = new JLabel("Carga Horária");
		lblCargaHoraria.setBounds(10, 40, 115, 15);
		textField = new JTextField();
		textField.setBounds(130, 7, 200, 20);
		textField.setColumns(10);
		
		listaCargaHoraria = new JList<Object>();
		listaCargaHoraria.setModel(new AbstractListModel<Object>() {
			String[] values = { "30", "40 a 50", "60 ou mais" };
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(final int index) {
				return values[index];
			}
		});
		listaCargaHoraria.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		listaCargaHoraria.setBounds(130, 36, 53, 48);
		
		
		
		buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 94, 326, 40);
		buttonPanel.setLayout(new BorderLayout(0, 0));
		{
			buttonCadastrar = new JButton("Cadastrar");
			buttonPanel.add(buttonCadastrar, BorderLayout.CENTER);
		}
		
		getContentPane().add(lblNome);
		getContentPane().add(textField);
		getContentPane().add(lblCargaHoraria);
		getContentPane().add(listaCargaHoraria);
		getContentPane().add(buttonPanel);
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
					CadastraDisciplina frame = new CadastraDisciplina();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
