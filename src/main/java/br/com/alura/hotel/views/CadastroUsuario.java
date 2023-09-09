package main.java.br.com.alura.hotel.views;

import java.awt.EventQueue;
import javax.swing.JPanel;
import main.java.br.com.alura.hotel.dao.UsuarioDao;
import main.java.br.com.alura.hotel.modelo.Usuario;
import main.java.br.com.alura.hotel.util.JPAUtil;
import main.java.br.com.alura.hotel.util.View;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Panel;

import javax.swing.JPasswordField;
import keeptoo.KGradientPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CadastroUsuario extends View {

	private final int WIDTH = 700;
	private final int HEIGHT = 400;

	private JPanel contentPane;
	private JPanel header;

	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private JPasswordField txtConfirmarSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroUsuario frame = new CadastroUsuario();
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
	public CadastroUsuario() {

		/**
		 * 
		 * ELEMENTOS
		 * 
		 */

		// Constrói a Janela
		contentPane = window(WIDTH, HEIGHT);

		// Constói o header
		header = header(WIDTH, true, new Login(), true);
		contentPane.add(header);

		// Footer
		JPanel footer = footer(HEIGHT, WIDTH, false);
		contentPane.add(footer);

		// Fundo Gradiente
		KGradientPanel bg = quadroGradiente(0, 0, WIDTH / 2, HEIGHT);
		contentPane.add(bg);

		// Quadro branco
		Panel quadro = quadroSolido(WIDTH / 2, 36, WIDTH, HEIGHT - 36 * 2, Color.WHITE);
		contentPane.add(quadro);

		// Título
		bg.add(labelTitulo(WIDTH / 4 - 90, HEIGHT / 3 - 36, 180, 26, "CADASTRAR"));
		bg.add(labelTitulo(WIDTH / 4 - 114, HEIGHT / 3, 227, 26, "NOVO USUÁRIO"));

		// IMG - LOGO HOTEL
		JLabel logo = new JLabel("");
		logo.setBounds((WIDTH / 4) - 50, (HEIGHT / 2) - 25, 100, 100);
		logo.setIcon(new ImageIcon(CadastroUsuario.class.getResource("/main/java/br/com/alura/hotel/res/aH-100px.png")));
		bg.add(logo);

		// Label Campo Usuário
		quadro.add(label(25, 10, 324, 26, Color.GRAY, "USUÁRIO"));

		// Campo Usuário
		txtUsuario = campoTxt(25, 40, WIDTH / 2 - 50, 40, true);
		quadro.add(txtUsuario);

		// Label Campo Senha
		quadro.add(label(25, 90, 324, 26, Color.GRAY, "SENHA"));

		// Campo Senha
		txtSenha = campoPass(25, 120, WIDTH / 2 - 50, 40, true);
		quadro.add(txtSenha);

		// Label Campo Confirmar Senha
		quadro.add(label(25, 170, 324, 26, Color.GRAY, "CONFIRMAR SENHA"));

		// Campo Confirmar Senha
		txtConfirmarSenha = campoPass(25, 200, WIDTH / 2 - 50, 40, true);
		quadro.add(txtConfirmarSenha);

		// Botão "CADASTRAR"
		JPanel btnCadastrar = botao(25, 265, "CADASTRAR");
		quadro.add(btnCadastrar);

		// Botão "CANCELAR"
		JPanel btnCancelar = botao(194, 265, "CANCELAR");
		quadro.add(btnCancelar);

		/**
		 * 
		 * LÓGICA
		 * 
		 */

		// Lógica Campo Usuário
		txtUsuario.setForeground(Color.LIGHT_GRAY);
		txtUsuario.setText("Digite seu nome de usuário");
		txtUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (txtUsuario.getText().equals("Digite seu nome de usuário")) {
					txtUsuario.setText("");
					txtUsuario.setForeground(Color.DARK_GRAY);
				}
				if (String.valueOf(txtSenha.getPassword()).isEmpty()
						|| String.valueOf(txtSenha.getPassword()).equals("********")) {
					txtSenha.setForeground(Color.LIGHT_GRAY);
				}
				if (String.valueOf(txtConfirmarSenha.getPassword()).isEmpty()
						|| String.valueOf(txtConfirmarSenha.getPassword()).equals("********")) {
					txtConfirmarSenha.setForeground(Color.LIGHT_GRAY);
				}
			}
		});

		// Lógica Campo Senha
		txtSenha.setForeground(Color.LIGHT_GRAY);
		txtSenha.setText("********");
		txtSenha.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (String.valueOf(txtSenha.getPassword()).equals("********")) {
					txtSenha.setText("");
					txtSenha.setForeground(Color.DARK_GRAY);
				}
				if (txtUsuario.getText().isEmpty()) {
					txtUsuario.setForeground(Color.LIGHT_GRAY);
				}
				if (String.valueOf(txtConfirmarSenha.getPassword()).isEmpty()
						|| String.valueOf(txtConfirmarSenha.getPassword()).equals("********")) {
					txtConfirmarSenha.setForeground(Color.LIGHT_GRAY);
				}
			}
		});

		// Lógica Campo Confirmar Senha
		txtConfirmarSenha.setForeground(Color.LIGHT_GRAY);
		txtConfirmarSenha.setText("********");
		txtConfirmarSenha.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (String.valueOf(txtConfirmarSenha.getPassword()).equals("********")) {
					txtConfirmarSenha.setText("");
					txtConfirmarSenha.setForeground(Color.DARK_GRAY);
				}
				if (txtUsuario.getText().isEmpty()) {
					txtUsuario.setForeground(Color.LIGHT_GRAY);
				}
				if (String.valueOf(txtSenha.getPassword()).isEmpty()
						|| String.valueOf(txtSenha.getPassword()).equals("********")) {
					txtSenha.setForeground(Color.LIGHT_GRAY);
				}
			}
		});

		// Lógica Botão "CADASTRAR"
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CadastrarUsuario(txtUsuario.getText(), new String(txtSenha.getPassword()),
						new String(txtConfirmarSenha.getPassword()));
			}
		});

		// Lógica Botão "CANCELAR"
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
	}

	/**
	 * 
	 * MÉTODOS
	 * 
	 */

	private void CadastrarUsuario(String usuario, String senha, String confirmarSenha) {

		// Verifica o campo do usuário
		if (usuario.isEmpty() || usuario.equalsIgnoreCase("Digite seu nome de usuário")) {
			JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios.");
			txtUsuario.setText("");
			txtSenha.setText("");
			txtConfirmarSenha.setText("");
			throw new IllegalArgumentException("> Campo do usuário vazio.");
		}

		// Verifica o campo da senha

		if (senha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios.");
			txtUsuario.setText("");
			txtSenha.setText("");
			txtConfirmarSenha.setText("");
			throw new IllegalArgumentException("> Campo da senha vazio.");
		}

		// Verifica o campo de confirmar senha

		if (confirmarSenha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios.");
			txtUsuario.setText("");
			txtSenha.setText("");
			txtConfirmarSenha.setText("");
			throw new IllegalArgumentException("> Campo de confirmar senha vazio.");
		}

		// Verifica se as senhas são iguais

		if (senha.equals(confirmarSenha)) {
			EntityManager em = JPAUtil.getEntityManager();
			UsuarioDao usuarioDao = new UsuarioDao(em);

			// Permanencia dos dados
			em.getTransaction().begin();
			usuarioDao.cadastrar(new Usuario(usuario, senha));
			em.getTransaction().commit();
			em.close();

			JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso.");

			Login login = new Login();
			login.setVisible(true);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "As senhas não são iguais.");
			txtSenha.setText("");
			txtConfirmarSenha.setText("");
			throw new IllegalArgumentException("> Senhas diferentes.");
		}
	}
}
