package main.java.br.com.alura.hotel.views;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import keeptoo.KGradientPanel;
import main.java.br.com.alura.hotel.dao.PagamentoDao;
import main.java.br.com.alura.hotel.dao.ReservaDao;
import main.java.br.com.alura.hotel.modelo.Pagamento;
import main.java.br.com.alura.hotel.modelo.Reserva;
import main.java.br.com.alura.hotel.util.JPAUtil;
import main.java.br.com.alura.hotel.util.View;

import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import javax.swing.JComboBox;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RegistroReserva extends View {

	private final int WIDTH = 944; // 910
	private final int HEIGHT = 609; // 560

	private JPanel contentPane;
	private JPanel header;

	public static JDateChooser txtDataE;
	public static JDateChooser txtDataS;
	public static JTextField txtValor;
	public static JComboBox<String> txtFormaPagamento;

	private String valor;
	private final int DIARIA = 79;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroReserva frame = new RegistroReserva();
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
	public RegistroReserva() {

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
		KGradientPanel bg = quadroGradiente(0, 36, WIDTH / 2, HEIGHT - 72);
		contentPane.add(bg);

		// Quadro branco
		Panel quadro = quadroSolido(WIDTH / 2, 36, WIDTH / 2, HEIGHT - 72, Color.WHITE);
		contentPane.add(quadro);

		// Título
		bg.add(labelTitulo(WIDTH / 4 - 81, HEIGHT / 2 - 146, 162, 26, "CADASTRO"));
		bg.add(labelTitulo(WIDTH / 4 - 91, HEIGHT / 2 - 110, 182, 26, "DE RESERVA"));

		// IMG - LOGO HOTEL
		JLabel logo = new JLabel("");
		logo.setBounds((WIDTH / 4) - 75, (HEIGHT / 2) - 60, 150, 150);
		logo.setIcon(
				new ImageIcon(RegistroReserva.class.getResource("/main/java/br/com/alura/hotel/res/aH-150px.png")));
		bg.add(logo);

		// Label Campo Check-In
		quadro.add(label(50, 50, 324, 26, Color.GRAY, "DATA DE CHECK-IN"));

		// Campo Check-In
			// Campo
		txtDataE = campoDateChooser(50 + 10, 80 + 5, WIDTH / 2 - 100 - 30, 40 - 10);
		quadro.add(txtDataE);
			// Borda
		JTextField bordaCheckIn = campoTxt(50, 80, WIDTH / 2 - 142, 40, false);
		//bordaCheckIn.setBackground(Color.WHITE);
		bordaCheckIn.setOpaque(false);
		quadro.add(bordaCheckIn);

		// Label Campo Check-Out
		quadro.add(label(50, 150, 324, 26, Color.GRAY, "DATA DE CHECK-OUT"));

		// Campo Check-Out
			// Campo
		txtDataS = campoDateChooser(50 + 10, 180 + 5, WIDTH / 2 - 100 - 30, 40 - 10);
		quadro.add(txtDataS);
			// Borda
		JTextField bordaCheckOut = campoTxt(50, 180, WIDTH / 2 - 142, 40, false);
		//bordaCheckOut.setBackground(Color.WHITE);
		bordaCheckOut.setOpaque(false);
		quadro.add(bordaCheckOut);

		// Label Campo Valor da Reserva
		quadro.add(label(50, 250, 324, 26, Color.GRAY, "VALOR DA RESERVA"));

		// Campo Valor da Reserva
		txtValor = campoTxt(50, 280, WIDTH / 2 - 100, 40, false);
		txtValor.setBackground(Color.WHITE);
		quadro.add(txtValor);

		// Label Campo Forma de Pagamento
		quadro.add(label(50, 350, 324, 26, Color.GRAY, "FORMA DE PAGAMENTO"));

		// Campo Forma de Pagamento
		int yFP = 380;
			// Seta
		JLabel seta = new JLabel("");
		seta.setBounds(390, yFP + 20, 14, 7);
		seta.setIcon(new ImageIcon(RegistroReserva.class.getResource("/main/java/br/com/alura/hotel/res/seta.png")));
		quadro.add(seta);
			// Campo
		txtFormaPagamento = campoCombo(50 + 10, yFP + 5, WIDTH / 2 - 100 - 20, 40 - 10);
		quadro.add(txtFormaPagamento);
			// Borda
		JTextField bordaPagamento = campoTxt(50, yFP, WIDTH / 2 - 100, 40, false);
		bordaPagamento.setOpaque(false);
		quadro.add(bordaPagamento);

		// Botão "PRÓXIMO"
		JPanel btnProximo = botao(50, 450, "PRÓXIMO");
		quadro.add(btnProximo);

		// Botão "CANCELAR"
		JPanel btnCancelar = botao(291 - 3, 450, "CANCELAR");
		quadro.add(btnCancelar);

		/**
		 * 
		 * BANCO DE DADOS
		 * 
		 */
		EntityManager em = JPAUtil.getEntityManager();
		PagamentoDao pagamentoDao = new PagamentoDao(em);
		ReservaDao reservaDao = new ReservaDao(em);

		/**
		 * 
		 * LÓGICA
		 * 
		 */

		// Lógica Campo Check-In
		txtDataE.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (txtDataE.getDate() != null && txtDataS.getDate() != null) {
					try {
						valor = CalcularDiaria(txtDataE, txtDataS);
						txtValor.setText("R$ " + valor + ".00");
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		});

		// Lógica Campo Check-Out
		txtDataS.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (txtDataE.getDate() != null && txtDataS.getDate() != null) {
					try {
						valor = CalcularDiaria(txtDataE, txtDataS);
						txtValor.setText("R$ " + valor + ".00");
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		});

		// Lógica Campo Forma de Pagamento
		List<Pagamento> listaPagamento = pagamentoDao.buscarTodos();
		String[] arrayPagamento = new String[listaPagamento.size()];
		AtomicInteger counter = new AtomicInteger();
		listaPagamento.stream().forEach(lista -> arrayPagamento[counter.getAndIncrement()] = lista.getPagamento());

		txtFormaPagamento.setModel(new DefaultComboBoxModel<String>(arrayPagamento));

		// Lógica Botão "PRÓXIMO"
		btnProximo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (RegistroReserva.txtDataE.getDate() != null &&
						RegistroReserva.txtDataS.getDate() != null) {

					// Constrói o objeto RESERVA
					Reserva reserva = new Reserva(
							txtDataE.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
							txtDataS.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
							new BigDecimal(valor),
							listaPagamento.get(txtFormaPagamento.getSelectedIndex()));

					// Adiciona ao Banco de dados pra gerar uma ID
					em.getTransaction().begin();
					reservaDao.cadastrar(reserva);
					em.getTransaction().commit();
					em.close();

					// Passa para próxima janela, cadastrar o hóspede
					RegistroHospede hospede = new RegistroHospede(reserva.getId());
					hospede.setVisible(true);
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
				new MenuUsuario().setVisible(true);
				dispose();
			}
		});

	}

	private String CalcularDiaria(JDateChooser CheckIn, JDateChooser CheckOut) throws Exception {

		LocalDate d1 = CheckIn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate d2 = CheckOut.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		Validacao(d1, d2);

		Long dias = Duration.between(d1.atStartOfDay(), d2.atStartOfDay()).toDays();
		Long valor = dias * DIARIA;

		return valor.toString();
	}

	private void Validacao(LocalDate CheckIn, LocalDate CheckOut) throws Exception {

		// As datas do Check-in precisa ser posterior a data de hoje
		if (CheckIn.isBefore(LocalDate.now())) {
			txtValor.setText("");
			JOptionPane.showMessageDialog(null, "A data do Check-in precisa ser posterior a data de hoje.");
			throw new Exception("A data do Check-in precisa ser posterior a data de hoje.", null);
		}
		// As datas do Check-out precisa ser posterior a data de hoje
		if (CheckOut.isBefore(LocalDate.now())) {
			txtValor.setText("");
			JOptionPane.showMessageDialog(null, "A data do Check-out precisa ser posterior a data de hoje.");
			throw new Exception("A data do Check-out precisa ser posterior a data de hoje.", null);
		}

		// A data do Check-out deve ser posterior a data do Check-in
		if (CheckOut.isBefore(CheckIn)) {
			txtValor.setText("");
			JOptionPane.showMessageDialog(null, "A data do Check-out precisa ser posterior a data do Check-in.");
			throw new Exception("A data do Check-out precisa ser posterior a data do Check-in.", null);
		}
	}

}
