package main.java.br.com.alura.hotel.views;

import java.awt.EventQueue;
import javax.swing.JPanel;

import main.java.br.com.alura.hotel.util.CustomFonts;
import main.java.br.com.alura.hotel.util.View;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.FontFormatException;

import javax.swing.SwingConstants;

import keeptoo.KGradientPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MenuPrincipal extends View {

	private final int WIDTH = 700;
	private final int HEIGHT = 400;

	private JPanel contentPane;
	private JPanel header;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
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
	public MenuPrincipal() {

		// Constrói a Janela
		contentPane = window(WIDTH, HEIGHT);

		// Constói o header 
		header = header(WIDTH, false, null, true);
		contentPane.add(header);

		// Fundo Gradiente
		KGradientPanel bg = quadroGradiente(0, 0, WIDTH, HEIGHT);
		contentPane.add(bg);

		// Footer
		JPanel footer = footer(HEIGHT, WIDTH, false);
		bg.add(footer);

		// IMG - LOGO HOTEL
		JLabel logo = new JLabel("");
		logo.setBounds((WIDTH/4)-75, (HEIGHT/2)-75, 150, 150);
		logo.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/main/java/br/com/alura/hotel/res/aH-150px.png")));
		bg.add(logo);

		// TXT - LOGIN
		try {
            CustomFonts.registrarFont(CustomFonts.chamarFont("Roboto-Light"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

		JLabel lblTitulo = new JLabel("LOGIN");
		lblTitulo.setFont(new Font("Roboto Light", Font.PLAIN, 24));
		lblTitulo.setForeground(Color.DARK_GRAY);
		lblTitulo.setBounds(((WIDTH/4)*3)-35, HEIGHT/2-45, 68, 22);
		bg.add(lblTitulo);

		// BTN - IMG - LOGIN
		JPanel btnLogin = new JPanel();
		btnLogin.setBounds(((WIDTH/4)*3)-32, HEIGHT/2-15, 64, 64);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
		btnLogin.setLayout(null);
		btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setOpaque(false);
		bg.add(btnLogin);

		JLabel imageLogin = new JLabel("");
		imageLogin.setBounds(0, 0, 64, 64);
		btnLogin.add(imageLogin);
		imageLogin.setHorizontalAlignment(SwingConstants.CENTER);
		imageLogin.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/main/java/br/com/alura/hotel/res/login.png")));

	}

}
