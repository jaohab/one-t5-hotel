package main.java.br.com.alura.hotel.util;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import keeptoo.KGradientPanel;

public abstract class View extends JFrame {

    int xMouse, yMouse;

    /**
     * Constrói a janela
     */
    public JPanel window(int width, int height) {
        setIconImage(Toolkit.getDefaultToolkit()
                .getImage(View.class.getResource("/main/java/br/com/alura/hotel/res/aH-40px.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, width, height);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        contentPane.setLayout(null);
        return contentPane;
    }

    /**
     * Constói o header
     */
    public JPanel header(int width, boolean mostrarVoltar, View janelaParaVoltar, boolean mostrarFechar) {

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
        header.setLayout(null);
        header.setBackground(Color.DARK_GRAY);
        header.setBounds(0, 0, width, 36);

        // Botão Voltar "<"
        if (mostrarVoltar) {

            JLabel labelAtras = new JLabel("<");
            labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
            labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
            labelAtras.setForeground(Color.WHITE);
            labelAtras.setBounds(0, 0, 53, 36);

            JPanel btnAtras = new JPanel();
            btnAtras.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    janelaParaVoltar.setVisible(true);
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

        }

        // Botão Fechar "X"
        if (mostrarFechar) {

            JLabel labelExit = new JLabel("X");
            labelExit.setHorizontalAlignment(SwingConstants.CENTER);
            labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
            labelExit.setForeground(Color.WHITE);
            labelExit.setBounds(0, 0, 53, 36);

            JPanel btnExit = new JPanel();
            btnExit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int res = JOptionPane.showConfirmDialog(null, "Tem certeza que quer fechar o programa?", "ATENÇÃO",
                            0);
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
            btnExit.setBounds(width - 53, 0, 53, 36);
            btnExit.add(labelExit);
            header.add(btnExit);

        }

        return header;
    }

    /**
     * Constrói o Footer
     */
    public JPanel footer(int y, int width, boolean mostrarInfo) {
        JPanel footer = new JPanel();
        footer.setBackground(Color.DARK_GRAY);
        footer.setBounds(0, y - 36, width, 36);
        footer.setLayout(null);

        // Footer - Copyright
        JLabel lblCopyright = new JLabel("Desenvolvido por João Henrique © 2023");
        lblCopyright.setFont(new Font("Roboto", Font.PLAIN, 12));
        lblCopyright.setForeground(Color.WHITE);
        lblCopyright.setBounds(width - 270, 11, 220, 13);
        footer.add(lblCopyright);

        // Footer - Relógio
        Date dataAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy  -  HH:mm").format(dataAtual);
        JLabel lblData = new JLabel(data);
        lblData.setFont(new Font("Roboto", Font.PLAIN, 12));
        lblData.setForeground(Color.WHITE);
        lblData.setBounds(50, 11, 220, 13);
        footer.add(lblData);

        if (mostrarInfo) {

        }

        return footer;
    }

    /**
     * Constrói Quadro Cor Sólida
     */
    public Panel quadroSolido(int x, int y, int width, int height, Color cor) {
        Panel panel = new Panel();
        panel.setBackground(cor);
        panel.setBounds(x, y, width, height);
        panel.setLayout(null);
        return panel;
    }

    /**
     * Constrói Quadro Gradiente
     */ 
    public KGradientPanel quadroGradiente(int x, int y, int width, int height) {
        KGradientPanel panel = new KGradientPanel();
        panel.setkStartColor(ColorList.G_START_COLOR);
        panel.setkEndColor(ColorList.G_END_COLOR);
        panel.setBounds(x, y, width, height);
        panel.setLayout(null);
        return panel;
    }

    /**
     * Constói Label Titulo
     */ 
    public JLabel labelTitulo(int x, int y, int width, int height, String msg) {
        JLabel label = new JLabel(msg);
        label.setFont(new Font("Roboto LIGHT", Font.PLAIN, 32));
        label.setForeground(Color.DARK_GRAY);
        label.setBounds(x, y, width, height);
        return label;
    }

    /**
     * Constói Label
     */ 
    public JLabel label(int x, int y, int width, int height, Color color, String msg) {
        JLabel label = new JLabel(msg);
        label.setFont(new Font("Roboto Black", Font.PLAIN, 16));
        label.setForeground(color);
        label.setBounds(x, y, width, height);
        return label;
    }

    /**
     * Constói Texto
     */ 
    public JLabel texto(int x, int y, int width, int height, Color color, int fontStyle, int fontSize, String msg) {
        JLabel label = new JLabel(msg);
        label.setFont(new Font("Roboto", fontStyle, fontSize));
        label.setForeground(color);
        label.setBounds(x, y, width, height);
        return label;
    }

    /**
     * Constrói Campo de Texto
     */ 
    public JTextField campoTxt(int x, int y, int width, int height, boolean editavel) {
        JTextField field = new JTextField();
        field.setFont(new Font("Roboto", Font.PLAIN, 14));
        // field.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        field.setBounds(x, y, width, height);
        field.setBorder(new RoundedBorder(35, 2, Color.LIGHT_GRAY, 10, 0));
        field.setEditable(editavel);
        return field;
    }

    /**
     * Constrói Campo de Senha
     */
    public JPasswordField campoPass(int x, int y, int width, int height, boolean editavel) {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Roboto", Font.PLAIN, 14));
        // field.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        field.setBounds(x, y, width, height);
        field.setBorder(new RoundedBorder(35, 2, Color.LIGHT_GRAY, 10, 0));
        field.setEditable(editavel);
        return field;
    }

    /**
     * Constrói Campo de Telefone
     * @throws ParseException
     */
    public JFormattedTextField campoTelefone(int x, int y, int width, int height, boolean editavel) throws ParseException {
        MaskFormatter maskFormatter = new MaskFormatter("(###) # ####-####");
		JFormattedTextField field = new JFormattedTextField(maskFormatter);

        field.setFont(new Font("Roboto", Font.PLAIN, 14));
        field.setBounds(x, y, width, height);
        field.setBorder(new RoundedBorder(35, 2, Color.LIGHT_GRAY, 10, 0));
        field.setEditable(editavel);
        return field;
    }

    /**
     * Constrói Campo ComboBox
     */
    public JComboBox<String> campoCombo(int x, int y, int width, int height) {
        JComboBox<String> field = new JComboBox<>();
        field.setFont(new Font("Roboto", Font.PLAIN, 14));
        field.setBounds(x, y, width, height);

        field.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = super.createArrowButton();
                button.setVisible(false); // Oculta a seta
                return button;
            }
        });

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(null);
            }
        });

        field.setBackground(Color.WHITE);
        field.setOpaque(false);
        return field;
    }

    /**
     * Constrói Campo DateChooser
     */
    public JDateChooser campoDateChooser(int x, int y, int width, int height) {
        Font font = new Font("Roboto", Font.PLAIN, 14);
        JDateChooser field = new JDateChooser();

        // CAMPO - Ajusta a font do campo
        field.setFont(font);
        // CAMPO - Ajusta posição (x, y) e dimensções do campo (width, height)
        field.setBounds(x, y, width, height);
        // CAMPO - Máscara
        field.setDateFormatString("yyyy-MM-dd");
        // CAMPO - Remove as bordas do campo
        ((JComponent) field.getDateEditor()).setBorder(BorderFactory.createEmptyBorder());

        // BOTÃO - Cor de fundo do botão
        field.getCalendarButton().setBackground(ColorList.G_START_COLOR);
        // BOTÃO - Altera a ponteira do cursor
        field.getCalendarButton().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        // BOTÃO - Remove as bordas
        field.getCalendarButton().setBorder(BorderFactory.createEmptyBorder());
        // BOTÃO - Substitui o Icone do botão
        field.getCalendarButton().setIcon(
                new ImageIcon(View.class.getResource("/main/java/br/com/alura/hotel/res/icon-n-calendario.png")));
        // BOTÃO - Ajusta o tamanho do botão
        field.getCalendarButton().setPreferredSize(new Dimension(30, 30));
        // BOTÃO - Alinha o icone (Vertical e Horizontal)
        field.getCalendarButton().setVerticalAlignment(SwingConstants.CENTER);
        field.getCalendarButton().setHorizontalAlignment(SwingConstants.CENTER);

        field.getCalendarButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                field.getCalendarButton().setBackground(ColorList.G_END_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                field.getCalendarButton().setBackground(ColorList.G_START_COLOR);
            }
        });

        return field;
    }

    // Constrói Botão
    public JPanel botao(int x, int y, String titulo) {
        // Determina o tamanho do texto
        Font font = new Font("Roboto", Font.BOLD, 16);
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D bounds = font.getStringBounds(titulo, frc);
        int txtWidth = (int) bounds.getWidth();

        // Forma
        JPanel botao = new JPanel();
        botao.setLayout(new BorderLayout());
        botao.setBackground(ColorList.G_START_COLOR);
        botao.setBounds(x, y, txtWidth + 50, 40);
        botao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(ColorList.G_END_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(ColorList.G_START_COLOR);
            }
        });

        // Título
        JLabel lblBotao = new JLabel(titulo);
        lblBotao.setFont(font);
        lblBotao.setForeground(Color.DARK_GRAY);
        lblBotao.setHorizontalAlignment(SwingConstants.CENTER);

        botao.add(lblBotao);
        return botao;
    }

    // Constrói Botão do Menu
    public JPanel botaoMenu(int x, int y, String titulo, String imgLink) {
        Font font = new Font("Roboto", Font.BOLD, 16);

        // Forma
        JPanel botao = new JPanel();
        botao.setBackground(Color.DARK_GRAY);
        botao.setBounds(x + 5, y, 250, 60);
        botao.setLayout(null);
        botao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(ColorList.G_START_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(Color.DARK_GRAY);
            }
        });

        // Título
        JLabel lblBotao = new JLabel(" " + titulo);
        lblBotao.setIcon(new ImageIcon(View.class.getResource(imgLink)));
        lblBotao.setFont(font);
        lblBotao.setForeground(Color.WHITE);
        lblBotao.setBounds(10, 12, 250, 34);
        lblBotao.setHorizontalAlignment(SwingConstants.LEFT);

        botao.add(lblBotao);
        return botao;
    }

    // Constói Separador
    public JSeparator line(int x, int y, int width, int height, Color color) {
        JSeparator separator = new JSeparator();
        separator.setForeground(color);
        separator.setBackground(color);
        separator.setBounds(x, y, width, height);
        return separator;
    }

    // Código para movimentar a janela pela tela seguindo a posição de "x" e "y"
    public void headerMousePressed(java.awt.event.MouseEvent evt) {
        xMouse = evt.getX();
        yMouse = evt.getY();
    }

    public void headerMouseDragged(java.awt.event.MouseEvent evt) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }
}

/**
 * Borda arredondada
 */

class RoundedBorder implements Border {
    private int radius;
    private int borderWidth;
    private Color borderColor;
    private int marginX;
    private int marginY;

    public RoundedBorder(int radius, int borderWidth, Color borderColor, int marginX, int marginY) {
        this.radius = radius;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
        this.marginX = marginX;
        this.marginY = marginY;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(borderWidth));

        g2d.setColor(borderColor);

        g2d.drawRoundRect(x + borderWidth / 2, y + borderWidth / 2, width - borderWidth, height - borderWidth, radius,
                radius);

        g2d.setStroke(originalStroke);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(marginY, marginX, marginY, marginX);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
