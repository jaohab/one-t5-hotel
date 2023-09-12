package main.java.br.com.alura.hotel.views;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import keeptoo.KGradientPanel;
import main.java.br.com.alura.hotel.dao.HospedeDao;
import main.java.br.com.alura.hotel.dao.PagamentoDao;
import main.java.br.com.alura.hotel.dao.ReservaDao;
import main.java.br.com.alura.hotel.dao.UsuarioDao;
import main.java.br.com.alura.hotel.modelo.Hospede;
import main.java.br.com.alura.hotel.modelo.Reserva;
import main.java.br.com.alura.hotel.modelo.Usuario;
import main.java.br.com.alura.hotel.util.CustomFonts;
import main.java.br.com.alura.hotel.util.JPAUtil;
import main.java.br.com.alura.hotel.util.View;
import javax.swing.JTable;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
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
		bg.add(labelTitulo(150, 20 + 25, 162, 26, "SISTEMA"));
		bg.add(labelTitulo(150, 20 + 25 + 36, 188, 26, "DE BUSCA"));

		// IMG - LOGO HOTEL
		JLabel logo = new JLabel("");
		logo.setBounds(25, 25, 100, 100);
		logo.setIcon(
				new ImageIcon(RegistroReserva.class.getResource("/main/java/br/com/alura/hotel/res/aH-100px.png")));
		bg.add(logo);

		// Quadro de Resultados
		UIManager.put("TabbedPane.selected", Color.DARK_GRAY);
		UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		UIManager.put("TabbedPane.borderHightlightColor", new ColorUIResource(Color.DARK_GRAY));
		UIManager.put("TabbedPane.darkShadow", new ColorUIResource(Color.DARK_GRAY));

		JTabbedPane quadroReultados = new JTabbedPane(JTabbedPane.TOP);
		quadroReultados.setBackground(Color.GRAY);
		quadroReultados.setForeground(Color.WHITE);
		quadroReultados.setFont(new Font("Roboto", Font.PLAIN, 16));
		quadroReultados.setBounds(25, 150, WIDTH - 50, 328);
		bg.add(quadroReultados);

		// Campo Buscar
		JTextField txtBuscar = campoTxt(25, 14, 300, 31, true);
		quadro.add(txtBuscar);
		txtBuscar.setText("Busca por nº da reserva");
		txtBuscar.setForeground(Color.LIGHT_GRAY);

		// Botão "BUSCAR"
		JPanel btnBuscar = botao(350, 9, "BUSCAR");
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
		DefaultTableModel modeloReservas = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return !(column == 0 || column == 3);
			}
		};

		JTable tbReservas = new JTable(modeloReservas);
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 14));

		JScrollPane scroll_tableReservas = new JScrollPane(tbReservas);
		quadroReultados.addTab("Reservas", new ImageIcon(Buscar.class.getResource("/main/java/br/com/alura/hotel/res/icon-buscar-booking.png")), scroll_tableReservas, null);
		scroll_tableReservas.setVisible(true);

		modeloReservas.addColumn("Número da Reserva");
		modeloReservas.addColumn("Data Check In");
		modeloReservas.addColumn("Data Check Out");
		modeloReservas.addColumn("Valor");
		modeloReservas.addColumn("Forma de Pagamento");
		

		// Aba HÓSPEDES
		DefaultTableModel modeloHospedes = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return !(column == 0 || column == 6);
			}
		};

		JTable tbHospedes = new JTable(modeloHospedes);
		tbHospedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHospedes.setFont(new Font("Roboto", Font.PLAIN, 14));

		JScrollPane scroll_tableHospedes = new JScrollPane(tbHospedes);
		quadroReultados.addTab("Hóspedes", new ImageIcon(Buscar.class.getResource("/main/java/br/com/alura/hotel/res/icon-buscar-guest.png")), scroll_tableHospedes, null);
		scroll_tableHospedes.setVisible(false);

		modeloHospedes.addColumn("ID do Hóspede");
		modeloHospedes.addColumn("Nome");
		modeloHospedes.addColumn("Sobrenome");
		modeloHospedes.addColumn("Data de Nascimento");
		modeloHospedes.addColumn("Nacionalidade");
		modeloHospedes.addColumn("Telefone");
		modeloHospedes.addColumn("Número da Reserva");

		// Aba USUÁRIOS
		DefaultTableModel modeloUsers = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return !(column == 0 || column == 6 || column == 7);
			}
		};

		JTable tbUsers = new JTable(modeloUsers);
		tbUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbUsers.setFont(new Font("Roboto", Font.PLAIN, 14));

		JScrollPane scroll_tableUser = new JScrollPane(tbUsers);
		quadroReultados.addTab("Usuários", new ImageIcon(Buscar.class.getResource("/main/java/br/com/alura/hotel/res/icon-buscar-user.png")), scroll_tableUser, null);
		scroll_tableUser.setVisible(false);
		
		modeloUsers.addColumn("ID");
		modeloUsers.addColumn("Login");
		modeloUsers.addColumn("Senha");
		modeloUsers.addColumn("Nome");
		modeloUsers.addColumn("Sobrenome");
		modeloUsers.addColumn("E-mail");
		modeloUsers.addColumn("Data de cadastro");
		modeloUsers.addColumn("Último acesso");

		// Abas Não editaveis - Cor  de fundo
		DefaultTableCellRenderer novaCor = new DefaultTableCellRenderer();
        novaCor.setBackground(new Color(238, 238, 238));

		tbReservas.getColumnModel().getColumn(0).setCellRenderer(novaCor);
		tbReservas.getColumnModel().getColumn(3).setCellRenderer(novaCor);
		tbHospedes.getColumnModel().getColumn(0).setCellRenderer(novaCor);
		tbHospedes.getColumnModel().getColumn(6).setCellRenderer(novaCor);
		tbUsers.getColumnModel().getColumn(0).setCellRenderer(novaCor);
		tbUsers.getColumnModel().getColumn(6).setCellRenderer(novaCor);
		tbUsers.getColumnModel().getColumn(7).setCellRenderer(novaCor);


		/**
		 * 
		 * BANCO DE DADOS
		 * 
		 */

		EntityManager em = JPAUtil.getEntityManager();
		ReservaDao reservaDao = new ReservaDao(em);
		HospedeDao hospedeDao = new HospedeDao(em);
		UsuarioDao usuarioDao = new UsuarioDao(em);
		PagamentoDao pagamentoDao = new PagamentoDao(em);

		/**
		 * 
		 * LÓGICA
		 * 
		 */

		// Lógica Campo de Busca
		quadroReultados.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int abaSelecionada = quadroReultados.getSelectedIndex();
				// Aba Reservas
				if (abaSelecionada == 0) {
					txtBuscar.setText("Busca por nº da reserva");
					txtBuscar.setForeground(Color.LIGHT_GRAY);
				}

				// Aba Hóspedes
				if (abaSelecionada == 1) {
					txtBuscar.setText("Busca por sobrenome");
					txtBuscar.setForeground(Color.LIGHT_GRAY);
				}

				// Aba Usuários
				if (abaSelecionada == 2) {
					txtBuscar.setText("Busca por ID do usuário");
					txtBuscar.setForeground(Color.LIGHT_GRAY);
				}
			}
		});

		txtBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int abaSelecionada = quadroReultados.getSelectedIndex();
				// Aba Reservas
				if (abaSelecionada == 0) {
					if (txtBuscar.getText().equals("Busca por nº da reserva")) {
						txtBuscar.setText("");
						txtBuscar.setForeground(Color.DARK_GRAY);
					}
				}

				// Aba Hóspedes
				if (abaSelecionada == 1) {
					if (txtBuscar.getText().equals("Busca por sobrenome")) {
						txtBuscar.setText("");
						txtBuscar.setForeground(Color.DARK_GRAY);
					}
				}

				// Aba Usuários
				if (abaSelecionada == 2) {
					if (txtBuscar.getText().equals("Busca por ID do usuário")) {
						txtBuscar.setText("");
						txtBuscar.setForeground(Color.DARK_GRAY);
					}
				}
			}
		});

		// Lógica Aba RESERVAS
		carregaListaReservas(reservaDao.buscarTodos(), modeloReservas);

		// Lógica Aba HÓSPEDES
		carregaListaHospedes(hospedeDao.buscarTodos(), modeloHospedes);

		// Lógica Aba USUÁRIOS
		carregaListaUsuarios(usuarioDao.buscarTodos(), modeloUsers);

		// Lógica Botão "BUSCAR"
		btnBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int abaSelecionada = quadroReultados.getSelectedIndex();
				// Aba Reservas
				if (abaSelecionada == 0) {
					Reserva reserva = reservaDao.buscarPorNumeroDaReserva(Long.parseLong(txtBuscar.getText()));
					carregaListaReservas(reserva, modeloReservas);
				}

				// Aba Hóspedes
				if (abaSelecionada == 1) {
					List<Hospede> hospede = hospedeDao.buscarPorSobrenome(txtBuscar.getText());
					carregaListaHospedes(hospede, modeloHospedes);
				}

				// Aba Usuários
				if (abaSelecionada == 2) {
					Usuario usuario = usuarioDao.buscarPorId(Long.parseLong(txtBuscar.getText()));
					carregaListaUsuarios(usuario, modeloUsers);
				}
			}
		});

		// Lógica Botão "EDITAR"
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int abaSelecionada = quadroReultados.getSelectedIndex();
				int res = 1;

				// Aba Reservas
				if (abaSelecionada == 0) {
					res = JOptionPane.showConfirmDialog(null, "Deseja mesmo atualizar a reserva nº: " + tbReservas.getModel().getValueAt(tbReservas.getSelectedRow(), 0) + " ?", "ATENÇÃO - ATUALIZAR REGISTRO", JOptionPane.OK_CANCEL_OPTION);

					if (res == 0) {
						em.getTransaction().begin();

						try {

							LocalDate checkIn = LocalDate.parse(tbReservas.getModel().getValueAt(tbReservas.getSelectedRow(), 1).toString());
							LocalDate checkOut = LocalDate.parse(tbReservas.getModel().getValueAt(tbReservas.getSelectedRow(), 2).toString());
							BigDecimal novoValor = CalcularDiaria(checkIn, checkOut);

							Reserva reserva = reservaDao.buscarPorNumeroDaReserva((Long) tbReservas.getModel().getValueAt(tbReservas.getSelectedRow(), 0));

							reserva.setDataEntrada(checkIn);
							reserva.setDataSaida(checkOut);
							reserva.setValor(novoValor);
							reserva.setFormaPagamento(pagamentoDao.buscarPorPagamento(tbReservas.getModel().getValueAt(tbReservas.getSelectedRow(), 4).toString()));

							reservaDao.atualizar(reserva);
							em.getTransaction().commit();
							carregaListaReservas(reservaDao.buscarTodos(), modeloReservas);

						} catch (Exception e1) {
							e1.printStackTrace();
						}

						// em.close();
					}
				}

				// Aba Hóspedes
				if (abaSelecionada == 1) {
					res = JOptionPane.showConfirmDialog(null,
							"Deseja mesmo atualizar o hóspede nº: " + tbHospedes.getModel().getValueAt(tbHospedes.getSelectedRow(), 0) + "?", "ATENÇÃO - ATUALIZAR REGISTRO", JOptionPane.OK_CANCEL_OPTION);

					if (res == 0) {
						em.getTransaction().begin();

						Hospede hospede = hospedeDao.buscarPorId((Long) tbHospedes.getModel().getValueAt(tbHospedes.getSelectedRow(), 0));

						hospede.setNome(tbHospedes.getModel().getValueAt(tbHospedes.getSelectedRow(), 1).toString());
						hospede.setSobrenome(tbHospedes.getModel().getValueAt(tbHospedes.getSelectedRow(), 2).toString());
						hospede.setDataNascimento(LocalDate.parse(tbHospedes.getModel().getValueAt(tbHospedes.getSelectedRow(), 3).toString()));
						hospede.setNacionalidade(tbHospedes.getModel().getValueAt(tbHospedes.getSelectedRow(), 4).toString());
						hospede.setTelefone(tbHospedes.getModel().getValueAt(tbHospedes.getSelectedRow(), 5).toString());
						
						hospedeDao.atualizar(hospede);
						em.getTransaction().commit();
						carregaListaHospedes(hospedeDao.buscarTodos(), modeloHospedes);

						// em.close();
					}
				}

				// Aba Usuários
				if (abaSelecionada == 2) {
					res = JOptionPane.showConfirmDialog(null,
							"Deseja mesmo atualizar o usuário nº: " + tbUsers.getModel().getValueAt(tbUsers.getSelectedRow(), 0) + "?", "ATENÇÃO - ATUALIZAR REGISTRO", JOptionPane.OK_CANCEL_OPTION);

					if (res == 0) {
						em.getTransaction().begin();

						Usuario usuario = usuarioDao.buscarPorId((Long) tbUsers.getModel().getValueAt(tbUsers.getSelectedRow(), 0));

						usuario.setLogin(tbUsers.getModel().getValueAt(tbUsers.getSelectedRow(), 1).toString());
						
						if (!tbUsers.getModel().getValueAt(tbUsers.getSelectedRow(), 2).toString().equals(usuario.getSenha().replaceAll(".", "*")) ) {
							usuario.setSenha(tbUsers.getModel().getValueAt(tbUsers.getSelectedRow(), 2).toString());
						}
						
						usuario.setNome(tbUsers.getModel().getValueAt(tbUsers.getSelectedRow(), 3).toString());
						usuario.setSobrenome(tbUsers.getModel().getValueAt(tbUsers.getSelectedRow(), 4).toString());
						usuario.setEmail(tbUsers.getModel().getValueAt(tbUsers.getSelectedRow(), 5).toString());

						usuarioDao.atualizar(usuario);
						em.getTransaction().commit();
						carregaListaUsuarios(usuarioDao.buscarTodos(), modeloUsers);

						// em.close();
					}
				}
			}
		});

		// Lógica Botão "DELETAR"
		btnDeletar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int abaSelecionada = quadroReultados.getSelectedIndex();
				int res = 1;

				// Aba Reservas
				if (abaSelecionada == 0) {
					res = JOptionPane.showConfirmDialog(null,
							"Deseja mesmo excluir a reserva nº: "
									+ tbReservas.getModel().getValueAt(tbReservas.getSelectedRow(), 0)
									+ " ?\nO hóspede vínculado a essa reserva também será EXCLUÍDO.",
							"ATENÇÃO - EXCLUIR REGISTRO", JOptionPane.OK_CANCEL_OPTION);

					if (res == 0) {
						em.getTransaction().begin();

						hospedeDao.remover(hospedeDao.buscarPorNumeroDaReserva((Long) tbReservas.getModel().getValueAt(tbReservas.getSelectedRow(), 0)));
						reservaDao.remover(reservaDao.buscarPorNumeroDaReserva((Long) tbReservas.getModel().getValueAt(tbReservas.getSelectedRow(), 0)));

						em.getTransaction().commit();
						carregaListaReservas(reservaDao.buscarTodos(), modeloReservas);
						carregaListaHospedes(hospedeDao.buscarTodos(), modeloHospedes);
						// em.close();
					}
				}

				// Aba Hóspedes
				if (abaSelecionada == 1) {
					res = JOptionPane.showConfirmDialog(null,
							"Deseja mesmo excluir o hóspede nº: "
									+ tbHospedes.getModel().getValueAt(tbHospedes.getSelectedRow(), 0)
									+ "?\nA reserva vínculada a esse hóspede também será EXCLUÍDA.",
							"ATENÇÃO - EXCLUIR REGISTRO", JOptionPane.OK_CANCEL_OPTION);

					if (res == 0) {
						em.getTransaction().begin();

						reservaDao.remover(reservaDao.buscarPorNumeroDaReserva(
								(Long) tbHospedes.getModel().getValueAt(tbHospedes.getSelectedRow(), 6)));
						hospedeDao.remover(hospedeDao
								.buscarPorId((Long) tbHospedes.getModel().getValueAt(tbHospedes.getSelectedRow(), 0)));

						em.getTransaction().commit();
						carregaListaHospedes(hospedeDao.buscarTodos(), modeloHospedes);
						carregaListaReservas(reservaDao.buscarTodos(), modeloReservas);
						// em.close();
					}
				}

				// Aba Usuários
				if (abaSelecionada == 2) {
					res = JOptionPane.showConfirmDialog(null,
							"Deseja mesmo excluir o usuário nº: "
									+ tbUsers.getModel().getValueAt(tbUsers.getSelectedRow(), 0) + "?",
							"ATENÇÃO - EXCLUIR REGISTRO", JOptionPane.OK_CANCEL_OPTION);

					if (res == 0) {
						em.getTransaction().begin();
						usuarioDao.remover(usuarioDao
								.buscarPorId((Long) tbUsers.getModel().getValueAt(tbUsers.getSelectedRow(), 0)));
						em.getTransaction().commit();
						carregaListaUsuarios(usuarioDao.buscarTodos(), modeloUsers);
						// em.close();
					}
				}

			}
		});

	}

	private void carregaListaReservas(Reserva reserva, DefaultTableModel aba) {
		aba.getDataVector().clear();
		Object[] linha = {
				reserva.getId(),
				reserva.getDataEntrada(),
				reserva.getDataSaida(),
				reserva.getValor(),
				reserva.getFormaPagamento().getPagamento()
		};
		aba.addRow(linha);
	}

	private void carregaListaReservas(List<Reserva> reservas, DefaultTableModel aba) {
		aba.getDataVector().clear();
		for (Reserva reserva : reservas) {
			Object[] linha = {
					reserva.getId(),
					reserva.getDataEntrada(),
					reserva.getDataSaida(),
					reserva.getValor(),
					reserva.getFormaPagamento().getPagamento()
			};
			aba.addRow(linha);
		}
	}

	private void carregaListaHospedes(List<Hospede> hospedes, DefaultTableModel aba) {
		aba.getDataVector().clear();
		for (Hospede hospede : hospedes) {
			Object[] linha = {
					hospede.getId(),
					hospede.getNome(),
					hospede.getSobrenome(),
					hospede.getDataNascimento(),
					hospede.getNacionalidade(),
					hospede.getTelefone(),
					hospede.getReserva().getId()
			};
			aba.addRow(linha);
		}
	}

	private void carregaListaUsuarios(Usuario usuario, DefaultTableModel aba) {
		aba.getDataVector().clear();
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
		aba.addRow(linha);

	}

	private void carregaListaUsuarios(List<Usuario> usuarios, DefaultTableModel aba) {
		aba.getDataVector().clear();
		for (Usuario usuario : usuarios) {
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
			aba.addRow(linha);
		}
	}

	private BigDecimal CalcularDiaria(LocalDate checkIn, LocalDate checkOut) throws Exception {
		Validacao(checkIn, checkOut);
		Long dias = Duration.between(checkIn.atStartOfDay(), checkOut.atStartOfDay()).toDays();
		long valor = dias * RegistroReserva.DIARIA;
		return BigDecimal.valueOf(valor);
	}

	private void Validacao(LocalDate checkIn, LocalDate checkOut) throws Exception {

		// As datas do Check-in precisa ser posterior a data de hoje
		if (checkIn.isBefore(LocalDate.now())) {
			JOptionPane.showMessageDialog(null, "A data do Check-in precisa ser posterior a data de hoje.");
			throw new Exception("A data do Check-in precisa ser posterior a data de hoje.", null);
		}
		// As datas do Check-out precisa ser posterior a data de hoje
		if (checkOut.isBefore(LocalDate.now())) {
			JOptionPane.showMessageDialog(null, "A data do Check-out precisa ser posterior a data de hoje.");
			throw new Exception("A data do Check-out precisa ser posterior a data de hoje.", null);
		}

		// A data do Check-out deve ser posterior a data do Check-in
		if (checkOut.isBefore(checkIn)) {
			JOptionPane.showMessageDialog(null, "A data do Check-out precisa ser posterior a data do Check-in.");
			throw new Exception("A data do Check-out precisa ser posterior a data do Check-in.", null);
		}
	}

}
