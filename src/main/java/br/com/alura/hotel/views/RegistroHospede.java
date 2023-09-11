package main.java.br.com.alura.hotel.views;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.EventQueue;

import com.toedter.calendar.JDateChooser;

import keeptoo.KGradientPanel;
import main.java.br.com.alura.hotel.dao.HospedeDao;
import main.java.br.com.alura.hotel.dao.ReservaDao;
import main.java.br.com.alura.hotel.lists.NacionalidadeEnum;
import main.java.br.com.alura.hotel.modelo.Hospede;
import main.java.br.com.alura.hotel.util.ColorList;
import main.java.br.com.alura.hotel.util.CustomFonts;
import main.java.br.com.alura.hotel.util.JPAUtil;
import main.java.br.com.alura.hotel.util.View;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Panel;

import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.time.ZoneId;

import javax.swing.SwingConstants;

public class RegistroHospede extends View {

	private final int WIDTH = 944; // 910
	private final int HEIGHT = 609; // 634

	private JPanel contentPane;
	private JPanel header;

	private JTextField txtNome;
	private JTextField txtSobrenome;
	private JDateChooser txtDataN;
	private JComboBox<String> txtNacionalidade;
	private JFormattedTextField txtTelefone;
	private JTextField txtNumReserva;

	private Long reservaId;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroHospede frame = new RegistroHospede(0l);
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

	public RegistroHospede(Long reservaId) {
		this. reservaId = reservaId;

		/**
		 * 
		 * ELEMENTOS
		 * 
		 */

		// Constrói a Janela
		contentPane = window(WIDTH, HEIGHT);

		// Constói o header
		header = header(WIDTH, false, null, false);
		contentPane.add(header);

		// Footer
		JPanel footer = footer(HEIGHT, WIDTH, true);
		contentPane.add(footer);

		// Fundo Gradiente
		KGradientPanel bg = quadroGradiente(0, 36, WIDTH / 2, HEIGHT - 72);
		contentPane.add(bg);

		// Quadro branco
		Panel quadro = quadroSolido(WIDTH / 2, 36, WIDTH / 2, HEIGHT - 72, Color.WHITE);
		contentPane.add(quadro);

		// Título
		bg.add(labelTitulo(WIDTH / 4 - 81, HEIGHT / 2 - 146, 162, 26, "CADASTRO"));
		bg.add(labelTitulo(WIDTH / 4 - 94, HEIGHT / 2 - 110, 188, 26, "DE HÓSPEDE"));

		// IMG - LOGO HOTEL
		JLabel logo = new JLabel("");
		logo.setBounds((WIDTH / 4) - 75, (HEIGHT / 2) - 60, 150, 150);
		logo.setIcon(
				new ImageIcon(RegistroReserva.class.getResource("/main/java/br/com/alura/hotel/res/aH-150px.png")));
		bg.add(logo);

		// Label Campo Nome
		quadro.add(label(50, 10, 324, 26, Color.GRAY, "NOME"));

		// Campo Nome
		txtNome = campoTxt(50, 35, WIDTH / 2 - 100, 40, true);
		quadro.add(txtNome);

		// Label Campo Sobrenome
		quadro.add(label(50, 85, 324, 26, Color.GRAY, "SOBRENOME"));

		// Campo Sobrenome
		txtSobrenome = campoTxt(50, 110, WIDTH / 2 - 100, 40, true);
		quadro.add(txtSobrenome);

		// Label Campo Data de Nascimento
		quadro.add(label(50, 160, 324, 26, Color.GRAY, "DATA DE NASCIMENTO"));

		// Campo Data de Nascimento
		int yDN = 185;
			// Campo
		txtDataN = campoDateChooser(50 + 10, yDN + 5, WIDTH / 2 - 100 - 30, 40 - 10);
		quadro.add(txtDataN);
			// Borda
		JTextField bordaDataN = campoTxt(50, yDN, WIDTH / 2 - 142, 40, false);
		bordaDataN.setOpaque(false);
		quadro.add(bordaDataN);

		// Label Campo Nacionalidade
		quadro.add(label(50, 235, 324, 26, Color.GRAY, "NACIONALIDADE"));

		// Campo Nacionalidade
		int yNa = 260;
			// Seta
		JLabel seta = new JLabel("");
		seta.setBounds(390, yNa + 20, 14, 7);
		seta.setIcon(new ImageIcon(RegistroReserva.class.getResource("/main/java/br/com/alura/hotel/res/seta.png")));
		quadro.add(seta);
			// Campo
		txtNacionalidade = campoCombo(50 + 10, yNa + 5, WIDTH / 2 - 100 - 20, 40 - 10);
		quadro.add(txtNacionalidade);
			// Borda
		JTextField bordaNacionalidade = campoTxt(50, yNa, WIDTH / 2 - 100, 40, false);
		bordaNacionalidade.setOpaque(false);
		quadro.add(bordaNacionalidade);		

		// Label Campo Telefone
		quadro.add(label(50, 310, 324, 26, Color.GRAY, "TELEFONE"));

		// Campo Telefone
		try {
			txtTelefone = campoTelefone(50, 335, WIDTH / 2 - 100, 40, true);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		quadro.add(txtTelefone);

		// Label Campo Nº da Reserva
		quadro.add(label(50, 385, 324, 26, Color.GRAY, "Nº DA RESERVA"));

		// Campo Nº da Reserva
		txtNumReserva = campoTxt(50, 410, WIDTH / 2 - 100, 40, false);
		txtNumReserva.setBackground(Color.WHITE);
		quadro.add(txtNumReserva);

		// Botão "CADASTRAR"
		JPanel btnCadastrar = botao(50, 480, "CADASTRAR");
		quadro.add(btnCadastrar);

		// Botão "CANCELAR"
		JPanel btnCancelar = botao(291 - 3, 480, "CANCELAR");
		quadro.add(btnCancelar);

		/**
		 * 
		 * BANCO DE DADOS
		 * 
		 */

		EntityManager em = JPAUtil.getEntityManager();
		HospedeDao hospedeDao = new HospedeDao(em);
		ReservaDao reservaDao = new ReservaDao(em);

		/**
		 * 
		 * LÓGICA
		 * 
		 */

		// Botão Voltar "<" com função de remoção de registro de reserva
		try {
            CustomFonts.registrarFont(CustomFonts.chamarFont("Roboto-Regular"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

		JLabel labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setForeground(Color.WHITE);
		labelAtras.setBounds(0, 0, 53, 36);
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				em.getTransaction().begin();
				reservaDao.remover(reservaDao.buscarPorNumeroDaReserva(reservaId));
				em.getTransaction().commit();
				em.close();
				new RegistroReserva().setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(ColorList.G_START_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.DARK_GRAY);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnAtras.setBackground(Color.DARK_GRAY);
		btnAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		header.add(btnAtras);

		// Botão Fechar "X" com função de remoção de registro de reserva
		JLabel labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setForeground(Color.WHITE);
		labelExit.setBounds(0, 0, 53, 36);
		JPanel btnExit = new JPanel();
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int res = JOptionPane.showConfirmDialog(null, "Tem certeza que quer fechar o programa?", "ATENÇÃO", 0);
				em.getTransaction().begin();
				reservaDao.remover(reservaDao.buscarPorNumeroDaReserva(reservaId));
				em.getTransaction().commit();
				em.close();
				if (res == 0)
					System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setBackground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setBackground(Color.DARK_GRAY);
			}
		});
		btnExit.setLayout(null);
		btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnExit.setBackground(Color.DARK_GRAY);
		btnExit.setBounds(WIDTH - 53, 0, 53, 36);
		btnExit.add(labelExit);
		header.add(btnExit);

		// Lógica Campo Nacionalidade
		NacionalidadeEnum[] nacionalidadeLista = NacionalidadeEnum.values();
		String[] opcoes = new String[nacionalidadeLista.length];
		for (int i = 0; i < opcoes.length; i++) {
			opcoes[i] = nacionalidadeLista[i].name().toUpperCase();
		}

		txtNacionalidade.setModel(new DefaultComboBoxModel<String>(opcoes));
		txtNacionalidade.setSelectedIndex(0);

		// Lógica Campo Nª da Reserva
		txtNumReserva.setText(getReservaId().toString());

		// Lógica Botão "CADASTRAR"
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!txtNome.getText().isEmpty() &&
						!txtSobrenome.getText().isEmpty() &&
						txtDataN.getDate() != null &&
						!txtTelefone.getText().equals("(   )       -    ")) {

				// 	// System.out.println("nome: " + txtNome.getText());
				// 	// System.out.println("sobrenome: " + txtSobrenome.getText());
				// 	// System.out.println("data nascimento" +
				// 	// txtDataN.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				// 	// System.out.println("nacionalidade: " + txtNacionalidade.getName());
				// 	// System.out.println("telefone: " + txtTelefone.getText());
				// 	// System.out.println(reserva.toString());

					Hospede hospede = new Hospede(txtNome.getText(),
							txtSobrenome.getText(),
							txtDataN.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
							txtNacionalidade.getName(),
							txtTelefone.getText(),
							reservaDao.buscarPorNumeroDaReserva(reservaId));
					
							em.getTransaction().begin();
					hospedeDao.atualizar(hospede);
					em.getTransaction().commit();
					em.close();

					Sucesso sucesso = new Sucesso();
					sucesso.setVisible(true);
					dispose();

				} else {
					JOptionPane.showMessageDialog(null, "Deve preencher todos os campos.");
				}
			}
		});

		// Lógica Botão "CANCELAR"
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				em.getTransaction().begin();
				reservaDao.remover(reservaDao.buscarPorNumeroDaReserva(reservaId));
				em.getTransaction().commit();
				em.close();
				new MenuUsuario().setVisible(true);
				//dispose();
			}
		});

	}

	public void setReservaId(Long reservaId) {
		this.reservaId = reservaId;
	}

	public Long getReservaId() {
		return reservaId;
	}

}
