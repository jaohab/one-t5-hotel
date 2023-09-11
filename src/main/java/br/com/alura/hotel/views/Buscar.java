package main.java.br.com.alura.hotel.views;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import keeptoo.KGradientPanel;
import main.java.br.com.alura.hotel.dao.HospedeDao;
import main.java.br.com.alura.hotel.dao.ReservaDao;
import main.java.br.com.alura.hotel.dao.UsuarioDao;
import main.java.br.com.alura.hotel.modelo.Hospede;
import main.java.br.com.alura.hotel.modelo.Reserva;
import main.java.br.com.alura.hotel.modelo.Usuario;
import main.java.br.com.alura.hotel.util.CustomFonts;
import main.java.br.com.alura.hotel.util.JPAUtil;
import main.java.br.com.alura.hotel.util.View;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Insets;
import java.awt.Panel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class Buscar extends View {

	private final int WIDTH = 944; // 910
	private final int HEIGHT = 609; // 571


	private JPanel contentPane;
	private JPanel header;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buscar frame = new Buscar();
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

	public Buscar() {
		
        try {
            CustomFonts.registrarFont(CustomFonts.chamarFont("Roboto-Regular"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

		/**
		 * 
		 * ELEMENTOS
		 * 
		 */

		// Constrói a Janela
		contentPane = window(WIDTH, HEIGHT);

		// Constói o header
		header = header(WIDTH, true, new MenuUsuario(), true);
		contentPane.add(header);

		// Footer
		JPanel footer = footer(HEIGHT, WIDTH, true);
		contentPane.add(footer);

		// Fundo Gradiente
		KGradientPanel bg = quadroGradiente(0, 36, WIDTH, HEIGHT - 72);
		contentPane.add(bg);

		// Quadro branco
		Panel quadro = quadroSolido(26, 478, 893, 59, Color.WHITE);
		bg.add(quadro);

		// Título
		bg.add(labelTitulo(150, 20+25, 162, 26, "SISTEMA"));
		bg.add(labelTitulo(150, 20+25+36, 188, 26, "DE BUSCA"));

		// IMG - LOGO HOTEL
		JLabel logo = new JLabel("");
		logo.setBounds(25, 25, 100, 100);
		logo.setIcon(new ImageIcon(RegistroReserva.class.getResource("/main/java/br/com/alura/hotel/res/aH-100px.png")));
		bg.add(logo);

		// Quadro de Resultados
		UIManager.put("TabbedPane.selected", Color.DARK_GRAY);
		UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		UIManager.put("TabbedPane.borderHightlightColor", new ColorUIResource( Color.DARK_GRAY ));
		UIManager.put("TabbedPane.darkShadow", new ColorUIResource( Color.DARK_GRAY ));

		JTabbedPane quadroReultados = new JTabbedPane(JTabbedPane.TOP);
		quadroReultados.setBackground(Color.GRAY);
		quadroReultados.setForeground(Color.WHITE);
		quadroReultados.setFont(new Font("Roboto", Font.PLAIN, 16));
		quadroReultados.setBounds(25, 150, WIDTH-50, 328);
		bg.add(quadroReultados);

		// Campo Buscar
		JTextField txtBuscar = campoTxt(25, 14, 200, 31, true);
		quadro.add(txtBuscar);

		// Botão "BUSCAR"
		JPanel btnBuscar = botao(250, 9, "BUSCAR");
		quadro.add(btnBuscar);

		// Botão "EDITAR"
		JPanel btnEditar = botao(617, 9, "EDITAR");
		quadro.add(btnEditar);

		// Botão "DELETAR"
		JPanel btnDeletar = botao(748, 9, "DELETAR");
		quadro.add(btnDeletar);

		/**
		 * 
		 * ABAS
		 * 
		 */

		// Aba RESERVAS
		JTable tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 14));
		
		JScrollPane scroll_tableReservas = new JScrollPane(tbReservas);
		quadroReultados.addTab("Reservas", new ImageIcon(Buscar.class.getResource("/main/java/br/com/alura/hotel/res/icon-buscar-booking.png")), scroll_tableReservas, null);
		scroll_tableReservas.setVisible(true);

		DefaultTableModel modeloReservas = (DefaultTableModel) tbReservas.getModel();
		modeloReservas.addColumn("Número da Reserva");
		modeloReservas.addColumn("Data Check In");
		modeloReservas.addColumn("Data Check Out");
		modeloReservas.addColumn("Valor");
		modeloReservas.addColumn("Forma de Pagamento");

		// Aba HÓSPEDES
		JTable tbHospedes = new JTable();
		tbHospedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHospedes.setFont(new Font("Roboto", Font.PLAIN, 14));
		
		JScrollPane scroll_tableHospedes = new JScrollPane(tbHospedes);
		quadroReultados.addTab("Hóspedes", new ImageIcon(Buscar.class.getResource("/main/java/br/com/alura/hotel/res/icon-buscar-guest.png")), scroll_tableHospedes, null);
		scroll_tableHospedes.setVisible(true);
		
		DefaultTableModel modeloHospedes = (DefaultTableModel) tbHospedes.getModel();
		modeloHospedes.addColumn("ID do Hóspede");
		modeloHospedes.addColumn("Nome");
		modeloHospedes.addColumn("Sobrenome");
		modeloHospedes.addColumn("Data de Nascimento");
		modeloHospedes.addColumn("Nacionalidade");
		modeloHospedes.addColumn("Telefone");
		modeloHospedes.addColumn("Número da Reserva");

		// Aba USUÁRIOS
		JTable tbUsers = new JTable();
		tbUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbUsers.setFont(new Font("Roboto", Font.PLAIN, 14));
		
		JScrollPane scroll_tableUser = new JScrollPane(tbUsers);
		quadroReultados.addTab("Usuários", new ImageIcon(Buscar.class.getResource("/main/java/br/com/alura/hotel/res/icon-buscar-user.png")), scroll_tableUser, null);
		scroll_tableUser.setVisible(true);
		
		DefaultTableModel modeloUsers = (DefaultTableModel) tbUsers.getModel();
		modeloUsers.addColumn("ID");
		modeloUsers.addColumn("Login");
		modeloUsers.addColumn("Senha");
		modeloUsers.addColumn("Nome");
		modeloUsers.addColumn("Sobrenome");
		modeloUsers.addColumn("E-mail");
		modeloUsers.addColumn("Data de cadastro");
		modeloUsers.addColumn("Último acesso");

		/**
		 * 
		 * BANCO DE DADOS
		 * 
		 */

		EntityManager em = JPAUtil.getEntityManager();

		HospedeDao hospedeDao = new HospedeDao(em);
		List<Hospede> todosHospedes = hospedeDao.buscarTodos();

		ReservaDao reservaDao = new ReservaDao(em);
		List<Reserva> todasReservas = reservaDao.buscarTodos();

		UsuarioDao usuarioDao = new UsuarioDao(em);
		List<Usuario> todosUsuarios = usuarioDao.buscarTodos();

		/**
		 * 
		 * LÓGICA
		 * 
		 */

		// Lógica Aba RESERVAS
		for (Reserva reserva : todasReservas) {
			Object[] linha = {
				reserva.getId(),
				reserva.getDataSaida(),
				reserva.getDataSaida(),
				reserva.getValor(),
				reserva.getFormaPagamento().getPagamento()
			};
			modeloReservas.addRow(linha);
		}
		
		// Lógica Aba HÓSPEDES
		for (Hospede hospede : todosHospedes) {
			Object[] linha = {
				hospede.getId(),
				hospede.getNome(),
				hospede.getSobrenome(),
				hospede.getDataNascimento(),
				hospede.getNacionalidade(),
				hospede.getTelefone(),
				hospede.getReserva().getId()
			};
			modeloHospedes.addRow(linha);
		}
		
		// Lógica Aba USUÁRIOS
		for (Usuario usuario : todosUsuarios) {
			Object[] linha = {
				usuario.getId(),
				usuario.getLogin(),
				usuario.getSenha().replaceAll(".", "*"),
				usuario.getNome(),
				usuario.getSobrenome(),
				usuario.getEmail(),
				usuario.getDataEHoraDoCadastro(),
				usuario.getUltimoAcesso()
			};
			modeloUsers.addRow(linha);
		}

		// Lógica Botão "BUSCAR"
		btnBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		// Lógica Botão "EDITAR"
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		// Lógica Botão "DELETAR"
		btnDeletar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

	}

}
