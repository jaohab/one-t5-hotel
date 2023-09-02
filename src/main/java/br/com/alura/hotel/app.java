package main.java.br.com.alura.hotel;

import java.awt.EventQueue;

import main.java.br.com.alura.hotel.views.MenuPrincipal;

public class app {

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
    
}