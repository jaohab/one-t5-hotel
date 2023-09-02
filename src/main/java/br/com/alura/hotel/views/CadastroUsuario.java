package main.java.br.com.alura.hotel.views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.br.com.alura.hotel.dao.UsuarioDao;
import main.java.br.com.alura.hotel.modelo.Usuario;
import main.java.br.com.alura.hotel.util.JPAUtil;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class CadastroUsuario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private JPasswordField txtConfirmarSenha;
	int xMouse, yMouse;
	private JLabel labelAtras;
	private JLabel labelExit;

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
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 788, 628);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		// Fundo Branco

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 788, 628);
		panel.setBackground(Color.WHITE);
		contentPane.add(panel);
		panel.setLayout(null);

		// Coluna Azul

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(12, 138, 199));
		panel_1.setBounds(484, 0, 304, 628);
		panel.add(panel_1);
		panel_1.setLayout(null);

		// Imagem Hotel

		JLabel imgHotel = new JLabel("");
		imgHotel.setBounds(0, 0, 304, 538);
		panel_1.add(imgHotel);
		imgHotel.setIcon( new ImageIcon(Login.class.getResource("/main/java/br/com/alura/hotel/res/img-hotel-login-.png")));

		// Header 

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setBackground(SystemColor.window);
		header.setBounds(0, 0, 784, 36);
		panel.add(header);
		header.setLayout(null);

		// Botão Voltar "<"

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		// Botão Fechar "X"

		JPanel btnexit = new JPanel();
		btnexit.setBounds(251, 0, 53, 36);
		panel_1.add(btnexit);
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnexit.setBackground(new Color(12, 138, 199));
				labelExit.setForeground(Color.white);
			}
		});
		btnexit.setBackground(new Color(12, 138, 199));
		btnexit.setLayout(null);
		btnexit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		labelExit = new JLabel("X");
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		labelExit.setForeground(SystemColor.text);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);

		// Logo

		JLabel logo = new JLabel("");
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setIcon(new ImageIcon(Login.class.getResource("/main/java/br/com/alura/hotel/res/lOGO-50PX.png")));
		logo.setBounds(65, 65, 48, 59);
		panel.add(logo);

		// Título da janela

		JLabel labelTitulo = new JLabel("CADASTRAR NOVO USUÁRIO");
		labelTitulo.setForeground(SystemColor.textHighlight);
		labelTitulo.setFont(new Font("Roboto Black", Font.PLAIN, 26));
		labelTitulo.setBounds(65, 150, 348, 26);
		panel.add(labelTitulo);

		// Label Usuário

		JLabel LabelUsuario = new JLabel("USUÁRIO");
		LabelUsuario.setForeground(SystemColor.textInactiveText);
		LabelUsuario.setFont(new Font("Roboto Black", Font.PLAIN, 20));
		LabelUsuario.setBounds(65, 215, 107, 26);
		panel.add(LabelUsuario);

		// Campo Usuário

		txtUsuario = new JTextField();
		txtUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (txtUsuario.getText().equals("Digite seu nome de usuário")) {
					txtUsuario.setText("");
					txtUsuario.setForeground(Color.black);
				}
				if (String.valueOf(txtSenha.getPassword()).isEmpty()) {
					txtSenha.setForeground(Color.gray);
				}
				if (String.valueOf(txtConfirmarSenha.getPassword()).isEmpty()) {
					txtConfirmarSenha.setForeground(Color.gray);
				}
			}
		});
		txtUsuario.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtUsuario.setText("Digite seu nome de usuário");
		txtUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtUsuario.setForeground(SystemColor.activeCaptionBorder);
		txtUsuario.setBounds(65, 252, 324, 32);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);

		// Barra azul Usuário

		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 120, 215));
		separator.setBounds(65, 292, 324, 2);
		panel.add(separator);

		// Label Senha

		JLabel lblSenha = new JLabel("SENHA");
		lblSenha.setForeground(SystemColor.textInactiveText);
		lblSenha.setFont(new Font("Roboto Black", Font.PLAIN, 20));
		lblSenha.setBounds(65, 316, 140, 26);
		panel.add(lblSenha);

		// Campo Senha

		txtSenha = new JPasswordField();
		txtSenha.setText("********");
		txtSenha.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (String.valueOf(txtSenha.getPassword()).equals("********")) {
					txtSenha.setText("");
					txtSenha.setForeground(Color.black);
				}
				if (txtUsuario.getText().isEmpty()) {
					txtUsuario.setForeground(Color.gray);
				}
				if (String.valueOf(txtConfirmarSenha.getPassword()).isEmpty()) {
					txtConfirmarSenha.setForeground(Color.gray);
				}
			}
		});
		txtSenha.setForeground(SystemColor.activeCaptionBorder);
		txtSenha.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtSenha.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtSenha.setBounds(65, 353, 324, 32);
		panel.add(txtSenha);

		// Barra azul Senha

		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(SystemColor.textHighlight);
		separator_1.setBounds(65, 393, 324, 2);
		panel.add(separator_1);

		// Label Confirmar Senha

		JLabel lblConfirmarSenha = new JLabel("CONFIRMAR SENHA");
		lblConfirmarSenha.setForeground(SystemColor.textInactiveText);
		lblConfirmarSenha.setFont(new Font("Roboto Black", Font.PLAIN, 20));
		lblConfirmarSenha.setBounds(65, 417, 187, 26);
		panel.add(lblConfirmarSenha);

		// Campo Confirmar Senha

		txtConfirmarSenha = new JPasswordField();
		txtConfirmarSenha.setText("********");
		txtConfirmarSenha.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (String.valueOf(txtConfirmarSenha.getPassword()).equals("********")) {
					txtConfirmarSenha.setText("");
					txtConfirmarSenha.setForeground(Color.black);
				}
				if (txtUsuario.getText().isEmpty()) {
					txtUsuario.setForeground(Color.gray);
				}
				if (String.valueOf(txtSenha.getPassword()).isEmpty()) {
					txtSenha.setForeground(Color.gray);
				}
			}
		});
		txtConfirmarSenha.setForeground(SystemColor.activeCaptionBorder);
		txtConfirmarSenha.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtConfirmarSenha.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtConfirmarSenha.setBounds(65, 454, 324, 32);
		panel.add(txtConfirmarSenha);

		// Barra azul Confirmar Senha

		JSeparator separator_2 = new JSeparator();
		separator_2.setBackground(SystemColor.textHighlight);
		separator_2.setBounds(65, 494, 324, 2);
		panel.add(separator_2);

		// Botão "CADASTRAR"

		JPanel btnCadastrar = new JPanel();
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCadastrar.setBackground(new Color(0, 156, 223));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCadastrar.setBackground(SystemColor.textHighlight);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				CadastrarUsuario();
			}
		});
		btnCadastrar.setBackground(SystemColor.textHighlight);
		btnCadastrar.setBounds(65, 532, 157, 44);
		panel.add(btnCadastrar);
		btnCadastrar.setLayout(null);
		btnCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		JLabel lblCadastrar = new JLabel("CADASTRAR");
		lblCadastrar.setBounds(0, 0, 157, 44);
		btnCadastrar.add(lblCadastrar);
		lblCadastrar.setForeground(SystemColor.controlLtHighlight);
		lblCadastrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastrar.setFont(new Font("Roboto", Font.PLAIN, 18));

		// Botão "CANCELAR"

		JPanel btnCancelar = new JPanel();
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCancelar.setBackground(new Color(0, 156, 223));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCancelar.setBackground(SystemColor.textHighlight);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
		btnCancelar.setBackground(SystemColor.textHighlight);
		btnCancelar.setBounds(244, 532, 145, 44);
		panel.add(btnCancelar);
		btnCancelar.setLayout(null);
		btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		JLabel lblCancelar = new JLabel("CANCELAR");
		lblCancelar.setBounds(0, 0, 145, 44);
		btnCancelar.add(lblCancelar);
		lblCancelar.setForeground(SystemColor.controlLtHighlight);
		lblCancelar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancelar.setFont(new Font("Roboto", Font.PLAIN, 18));

	}

	private void CadastrarUsuario() {
		String usuario = txtUsuario.getText();
		String senha = new String(txtSenha.getPassword());
		String confirmarSenha = new String(txtConfirmarSenha.getPassword());

		// System.out.println(usuario);
		// System.out.println(senha);
		// System.out.println(confirmarSenha);

		if (usuario.isEmpty() || usuario.equalsIgnoreCase("Digite seu nome de usuário") ||senha.isEmpty() || confirmarSenha.isEmpty()) {
			txtUsuario.setText("");
			txtSenha.setText("");
			txtConfirmarSenha.setText("");

			JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios.");
		} else {
			if (senha.equals(confirmarSenha)) {
				EntityManager em = JPAUtil.getEntityManager();
				UsuarioDao usuarioDao = new UsuarioDao(em);
				
				em.getTransaction().begin();
				usuarioDao.cadastrar(new Usuario(usuario, senha));
				em.getTransaction().commit();
				em.close();

				JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso.");

				Login login = new Login();
				login.setVisible(true);
				dispose();
			} else {
				txtSenha.setText("");
				txtConfirmarSenha.setText("");

				JOptionPane.showMessageDialog(null, "As senhas não são iguais, tente novamente.");
			}
		}
	}

	// Código que permite movimentar a janela pela tela seguindo a posição de "x" e "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}
