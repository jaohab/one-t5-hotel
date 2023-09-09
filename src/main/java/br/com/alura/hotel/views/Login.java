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
import java.util.Date;
import java.util.List;

public class Login extends View {

	private final int WIDTH = 700;
	private final int HEIGHT = 400;

	private JPanel contentPane;
	private JPanel header;

	private JTextField txtUsuario;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {

		/**
		 * 
		 * ELEMENTOS
		 * 
		 */

		// Constrói a Janela
		contentPane = window(WIDTH, HEIGHT);

		// Constói o header
		header = header(WIDTH, true, new MenuPrincipal(), true);
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
		bg.add(labelTitulo(WIDTH / 4 - 46, HEIGHT / 3, 93, 26, "LOGIN"));

		// IMG - LOGO HOTEL
		JLabel logo = new JLabel("");
		logo.setBounds((WIDTH / 4) - 50, (HEIGHT / 2) - 25, 100, 100);
		logo.setIcon(new ImageIcon(Login.class.getResource("/main/java/br/com/alura/hotel/res/aH-100px.png")));
		bg.add(logo);

		// Label Campo Usuário
		quadro.add(label(25, 40, 324, 26, Color.GRAY, "USUÁRIO"));

		// Campo Usuário
		txtUsuario = campoTxt(25, 70, WIDTH / 2 - 50, 40, true);
		quadro.add(txtUsuario);

		// Label Campo Senha
		quadro.add(label(25, 130, 324, 26, Color.GRAY, "SENHA"));

		// Campo Senha
		txtSenha = campoPass(25, 160, WIDTH / 2 - 50, 40, true);
		quadro.add(txtSenha);

		// Botão "ENTRAR"

		JPanel btnEntrar = botao(25, 250, "ENTRAR");
		quadro.add(btnEntrar);

		// Botão "NOVO USUÁRIO"

		JPanel btnNovoUser = botao(160, 250, "NOVO USUÁRIO");
		quadro.add(btnNovoUser);

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
			}
		});

		// Lógica Botão "ENTRAR"
		btnEntrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logon(txtUsuario.getText(), new String(txtSenha.getPassword()));
			}
		});

		// Lógica Botão "NOVO USUÁRIO"
		btnNovoUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CadastroUsuario cadastrar = new CadastroUsuario();
				cadastrar.setVisible(true);
				dispose();
			}
		});
	}

	/**
	 * 
	 * MÉTODOS
	 * 
	 */

	private void logon(String txtUsuario, String txtSenha) {

		String usuario = "admin";
		String senha = "admin";
		boolean naoValidou = false;

		// Acesso Administrador

		if (txtUsuario.equals(usuario) && txtSenha.equals(senha)) {
			MenuUsuario menu = new MenuUsuario();
			menu.setVisible(true);
			dispose();
		} else {
			naoValidou = true;
		}

		// Acesso Banco de Dados

		if (naoValidou) {
			EntityManager em = JPAUtil.getEntityManager();
			UsuarioDao usuarioDao = new UsuarioDao(em);
			List<Usuario> listaUsuarios = usuarioDao.buscarTodos();

			for (Usuario user : listaUsuarios) {

				if (txtUsuario.equals(user.getLogin())) {
					if (txtSenha.equals(user.getSenha())) {

						// Atualiza a data de Último Acesso
						em.getTransaction().begin();
						user.setUltimoAcesso(new Date());
						em.getTransaction().commit();
        				em.close();

						// Acessa a página
						MenuUsuario menu = new MenuUsuario();
						menu.setVisible(true);
						dispose();

						naoValidou = false;
						break;
					}
				}
			}
			em.close();
		}

		if (naoValidou) {
			JOptionPane.showMessageDialog(this, "Usuario ou Senha não válidos");
		}
	}

}
