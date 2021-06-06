package br.com.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.dao.LeitorDAO;
import br.com.model.Leitor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.TextArea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JLabel lblCodLeitor;
	private JLabel lblNomeLeitor;
	private JLabel lblTipoLeitor;
	private JTextField txtCodLeitor;
	private JTextField txtNomeLeitor;
	private JComboBox cmbTipoLeitor;
	private TextArea txtListar;
	private JButton btnNovo;
	private JButton btnSalvar;
	private JButton btnConsultar;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnListar;
	
	private Leitor leitor;
	private LeitorDAO dao;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("LEITOR");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);// frame no centro da tela tela 
		
		lblCodLeitor = new JLabel("CODIGO DO LEITOR :");
		lblCodLeitor.setFont(new Font("Arial", Font.BOLD, 11));
		lblCodLeitor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodLeitor.setBounds(63, 38, 109, 14);
		contentPane.add(lblCodLeitor);
		
		lblNomeLeitor = new JLabel("NOME :");
		lblNomeLeitor.setFont(new Font("Arial", Font.BOLD, 11));
		lblNomeLeitor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomeLeitor.setBounds(63, 101, 109, 14);
		contentPane.add(lblNomeLeitor);
		
		lblTipoLeitor = new JLabel("TIPO LEITOR :");
		lblTipoLeitor.setFont(new Font("Arial", Font.BOLD, 11));
		lblTipoLeitor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoLeitor.setBounds(63, 164, 109, 14);
		contentPane.add(lblTipoLeitor);
		
		txtCodLeitor = new JTextField();
		txtCodLeitor.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtCodLeitor.setBounds(182, 35, 158, 20);
		contentPane.add(txtCodLeitor);
		txtCodLeitor.setColumns(10);
		
		txtNomeLeitor = new JTextField();
		txtNomeLeitor.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtNomeLeitor.setColumns(10);
		txtNomeLeitor.setBounds(182, 98, 316, 20);
		contentPane.add(txtNomeLeitor);
		
		cmbTipoLeitor = new JComboBox();
		cmbTipoLeitor.setFont(new Font("Tahoma", Font.BOLD, 10));
		cmbTipoLeitor.setModel(new DefaultComboBoxModel(new String[] {"ESCOLHA UMA OP\u00C7\u00C3O", "ALUNO", "PROFESSOR", "ADMINISTRATIVO"}));
		cmbTipoLeitor.setBounds(182, 159, 316, 24);
		contentPane.add(cmbTipoLeitor);
		
		txtListar = new TextArea();
		txtListar.setBounds(42, 270, 497, 216);
		contentPane.add(txtListar);
		
		btnNovo = new JButton("NOVO");
		btnNovo.setToolTipText("LIMPAR OS CAMPOS");
		btnNovo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ação de limpar os dados no textField e comboBox
				limpar();
			}
		});
		btnNovo.setFont(new Font("Arial", Font.BOLD, 10));
		btnNovo.setBounds(42, 219, 82, 23);
		contentPane.add(btnNovo);
		
		btnSalvar = new JButton("SALVAR");
		btnSalvar.setToolTipText("ADICIONAR UM NOVO LEITOR");
		btnSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//metodo Salvar
				try {
					// objeto leitor que captura dados da tela
					leitor = new Leitor();
					leitor.setCodLeitor(Integer.parseInt(txtCodLeitor.getText()));
					leitor.setNomeLeitor(txtNomeLeitor.getText());
					leitor.setTipoLeitor((String) cmbTipoLeitor.getSelectedItem());
					// abrir conexão
					dao = new LeitorDAO();
					// salvar
					dao.Salvar(leitor);
					JOptionPane.showMessageDialog(null, "ADICIONADO COM SUCESSO !");
					limpar();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO AO ADICIONAR !");
					limpar();
				}
			}
		});
		btnSalvar.setFont(new Font("Arial", Font.BOLD, 10));
		btnSalvar.setBounds(146, 219, 82, 23);
		contentPane.add(btnSalvar);
		
		btnConsultar = new JButton("CONSULTAR");
		btnConsultar.setToolTipText("CONSULTE PELO CODIGO ");
		btnConsultar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// abrir conexão
					dao = new LeitorDAO();
					// metodo consultar
					int codigo = Integer.parseInt(txtCodLeitor.getText());
					leitor = dao.Consultar(codigo);
					txtNomeLeitor.setText(leitor.getNomeLeitor());				
					String tipo = leitor.getTipoLeitor();
					// compara os dados do banco de dados e retorna no comboBox
					if(tipo.equals("ALUNO")) {
						cmbTipoLeitor.setSelectedIndex(1);			
					}
					else if(tipo.equals("PROFESSOR")) {
						cmbTipoLeitor.setSelectedIndex(2);			
					}
					else {
						cmbTipoLeitor.setSelectedIndex(3);			
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO AO CONSULTAR !");
					limpar();
				}
			}
		});
		btnConsultar.setFont(new Font("Arial", Font.BOLD, 10));
		btnConsultar.setBounds(395, 34, 103, 23);
		contentPane.add(btnConsultar);
		
		btnAlterar = new JButton("ALTERAR");
		btnAlterar.setToolTipText("ALTERAR O LEITOR ATUAL");
		btnAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// metodo alterar
				try {
					// objeto leitor que captura dados da tela
					leitor = new Leitor();
					leitor.setCodLeitor(Integer.parseInt(txtCodLeitor.getText()));
					leitor.setNomeLeitor(txtNomeLeitor.getText());
					leitor.setTipoLeitor((String) cmbTipoLeitor.getSelectedItem());
					// abrir conexão
					dao = new LeitorDAO();
					// alterar
					dao.Alterar(leitor);
					JOptionPane.showMessageDialog(null, "ALTERADO COM SUCESSO !");
					limpar();
				} catch (Exception e1) {
					//System.out.println(e1);
					JOptionPane.showMessageDialog(null, "ERRO AO ALTERAR !");
					limpar();
				}
			}
		});
		btnAlterar.setFont(new Font("Arial", Font.BOLD, 10));
		btnAlterar.setBounds(249, 219, 82, 23);
		contentPane.add(btnAlterar);
		
		btnExcluir = new JButton("EXCLUIR");
		btnExcluir.setToolTipText("EXCLUIR O LEITOR");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//metodo Excluir
				try {
					// abrir conexão
					dao = new LeitorDAO();
					// excluir
					int codigo = Integer.parseInt(txtCodLeitor.getText());
					dao.Excluir(codigo);
					JOptionPane.showMessageDialog(null, "EXCLUIDO COM SUCESSO !");
					limpar();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO AO EXCLUIR !");
					limpar();
				}
			}
		});
		btnExcluir.setFont(new Font("Arial", Font.BOLD, 10));
		btnExcluir.setBounds(353, 219, 82, 23);
		contentPane.add(btnExcluir);
		
		btnListar = new JButton("LISTAR");
		btnListar.setToolTipText("LISTAR LEITORES CADASTRADOS NO BANCO DE DADOS");
		btnListar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<Leitor> lista = new ArrayList<Leitor>();
					// abrir conexão
					dao = new LeitorDAO();
					lista = dao.listarTodos();
					for(Leitor leitor: lista) {
						txtListar.append("==========================================="  + "\n\n");
						txtListar.append("Codido do Leitor : " + leitor.getCodLeitor() + "\n");
						txtListar.append("Nome do Leitor : " + leitor.getNomeLeitor() + "\n");
						txtListar.append("Tipo do Leitor : " + leitor.getTipoLeitor() + "\n\n");
						txtListar.append("==========================================="  + "\n\n");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "ERRO AO LISTAR !");
					limpar();
				}				
			}
		});
		btnListar.setFont(new Font("Arial", Font.BOLD, 10));
		btnListar.setBounds(457, 219, 82, 23);
		contentPane.add(btnListar);
		
	}
	// metodo limpar
	private void limpar() {
		txtCodLeitor.setText(null);
		txtNomeLeitor.setText(null);
		cmbTipoLeitor.setSelectedIndex(0);
		txtListar.setText(null);
	}
	
}
