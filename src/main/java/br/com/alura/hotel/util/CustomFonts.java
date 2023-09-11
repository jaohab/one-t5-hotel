package main.java.br.com.alura.hotel.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public abstract class CustomFonts {

    public static Font chamarFont(String nomeDaFont) throws FontFormatException, IOException {
            return Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/br/com/alura/hotel/res/fonts/" + nomeDaFont + ".ttf"));
    }

    public static void registrarFont(Font font) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
    }
    
}
