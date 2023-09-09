package main.java.br.com.alura.hotel.views;

import java.awt.EventQueue;
import javax.swing.JPanel;

import main.java.br.com.alura.hotel.util.View;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import keeptoo.KGradientPanel;

public class MenuUsuario extends View {

	private final int WIDTH = 944;
	private final int HEIGHT = 609;

	private JPanel contentPane;
	private JPanel header;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuUsuario frame = new MenuUsuario();
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

	public MenuUsuario() {

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
		JPanel footer = footer(HEIGHT, WIDTH, true);
		contentPane.add(footer);

		// Fundo Gradiente
		KGradientPanel bg = quadroGradiente(260, 36, WIDTH - 260, HEIGHT - 36);
		contentPane.add(bg);

		// Quadro branco - Menu
		Panel quadro = quadroSolido(0, 36, 260, HEIGHT - 72, Color.WHITE);
		contentPane.add(quadro);

		// Botão REGISTRO DE RESERVA
		JPanel btnRegistrar = botaoMenu(0, 5, "REGISTRO DE RESERVA",
				"/main/java/br/com/alura/hotel/res/icon-n-reservas.png");
		quadro.add(btnRegistrar);

		// Botão BUSCAR RESERVAS
		JPanel btnBuscar = botaoMenu(0, 70, "BUSCAR RESERVAS", "/main/java/br/com/alura/hotel/res/icon-n-buscar.png");
		quadro.add(btnBuscar);

		// IMG - LOGO HOTEL
		JLabel logo = new JLabel("");
		logo.setBounds(50, 40, 150, 150);
		logo.setIcon(new ImageIcon(MenuUsuario.class.getResource("/main/java/br/com/alura/hotel/res/aH-150px.png")));
		bg.add(logo);

		// Título
		bg.add(labelTitulo(250, 78, 341, 26, "SISTEMA DE RESERVAS"));
		bg.add(labelTitulo(250, 114, 205, 26, "HOTEL ALURA"));

		// Linha Branca
		bg.add(line(50, 225, WIDTH - 360, 2, Color.WHITE));

		/**
		 * 
		 * TEXTO
		 * 
		 */

		// Título
		bg.add(texto(50, 255, WIDTH - 360, 45, Color.DARK_GRAY, 0, 24, "Bem-vindo"));

		// Bloco de Texto 1
		bg.add(texto(50, 300, WIDTH - 360, 45, Color.DARK_GRAY, 0, 16,
				"<html><body>Sistema de reservas de hotéis. Controle e gerencie de forma otimizada e fácil o fluxo de reservas e hóspedes do hotel.</body></html>"));

		// Bloco de Texto 2
		bg.add(texto(50, 355, WIDTH - 360, 55, Color.DARK_GRAY, 0, 16,
				"<html><body> Esta ferramenta permitirá que você mantenha um controle completo e detalhado de suas reservas e hóspedes, você terá acesso a ferramentas especiais para tarefas específicas como:</body></html>"));

		// Bloco de Texto 3 - Tópicos
		bg.add(texto(60, 425, WIDTH - 360, 20, Color.DARK_GRAY, 1, 15, "• Registro de reservas e hóspedes"));
		bg.add(texto(60, 450, WIDTH - 360, 20, Color.DARK_GRAY, 1, 15, "• Edição de reservas e hóspedes existentes"));
		bg.add(texto(60, 475, WIDTH - 360, 20, Color.DARK_GRAY, 1, 15, "• Excluir todos os tipos de registros"));
		bg.add(texto(60, 500, WIDTH - 360, 20, Color.DARK_GRAY, 1, 15, "• Edição de usuários do sistema"));

		/**
		 * 
		 * LÓGICA
		 * 
		 */

		// Lógica Botão "REGISTRO DE RESERVA"
		btnRegistrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegistroReserva reservas = new RegistroReserva();
				reservas.setVisible(true);
				dispose();
			}
		});

		// Lógica Botão "BUSCAR RESERVAS"
		btnBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Buscar buscar = new Buscar();
				buscar.setVisible(true);
				dispose();
			}
		});

	}

}
