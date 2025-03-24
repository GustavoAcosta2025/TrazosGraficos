import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class FrmTrazos extends JFrame {
    public String[] tipoTrazo = new String[]{"Línea", "Rectángulo", "Óvalo"};
    JComboBox<String> cmbTipoTrazo;
    JTextField txtInfo;
    JPanel pnlDibujo;
    int x, y;
    boolean trazando = false;
    BufferedImage imagen;
    BufferedImage imagenTemporal;

    private JButton btnSeleccionar;
    private JButton btnEliminar;
    private JButton btnGuardar;
    private JButton btnCargar;

    public FrmTrazos() {

        btnSeleccionar = new JButton();
        btnEliminar = new JButton();
        btnGuardar = new JButton();
        btnCargar = new JButton();

        setSize(500, 400);
        setTitle("Editor de gráficas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Botontes configuracion
        btnSeleccionar.setIcon(new ImageIcon(getClass().getResource("/imagenes/mouse.png")));
        btnSeleccionar.setToolTipText("Seleccionar Tipo Trazo");
        btnSeleccionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
        cmbTipoTrazo.add(btnSeleccionar);

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/imagenes/delete.png")));
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
        cmbTipoTrazo.add(btnEliminar);

        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/imagenes/save.png")));
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
        cmbTipoTrazo.add(btnGuardar);

        btnCargar.setIcon(new ImageIcon(getClass().getResource("/imagenes/Cargar.jpg")));
        btnGuardar.setToolTipText("Cargar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
        cmbTipoTrazo.add(btnGuardar);

        JToolBar tbTrazos = new JToolBar();
        cmbTipoTrazo = new JComboBox<>(tipoTrazo);
        tbTrazos.add(cmbTipoTrazo);

        txtInfo = new JTextField();
        tbTrazos.add(txtInfo);

        pnlDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagen, 0, 0, this);
                g.drawImage(imagenTemporal, 0, 0, this);
            }
        };
        pnlDibujo.setBackground(Color.BLACK);

        getContentPane().add(tbTrazos, BorderLayout.NORTH);
        getContentPane().add(pnlDibujo, BorderLayout.CENTER);

        imagen = new BufferedImage(500, 400, BufferedImage.TYPE_INT_ARGB);
        imagenTemporal = new BufferedImage(500, 400, BufferedImage.TYPE_INT_ARGB);

        pnlDibujo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                dibujar(me.getX(), me.getY());
            }
        });

        pnlDibujo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent me) {
                dibujarTemporal(me.getX(), me.getY());
            }
        });
    }

    private void dibujar(int x, int y) {
        Graphics g = imagen.getGraphics();
        g.setColor(Color.RED);

        if (!trazando) {
            trazando = true;
            this.x = x;
            this.y = y;
            txtInfo.setText("Trazando desde x=" + this.x + ", y=" + this.y);
        } else {
            trazando = false;
            Graphics gTemp = imagenTemporal.getGraphics();
            gTemp.setColor(Color.RED); 
            
            int ancho = Math.abs(this.x - x);
            int alto = Math.abs(this.y - y);
            int x1 = Math.min(this.x, x);
            int y1 = Math.min(this.y, y);

            switch (cmbTipoTrazo.getSelectedIndex()) {
                case 0 -> {
                    g.drawLine(this.x, this.y, x, y);
                    gTemp.drawLine(this.x, this.y, x, y);
                }
                case 1 -> {
                    g.drawRect(x1, y1, ancho, alto);
                    gTemp.drawRect(x1, y1, ancho, alto);
                }
                case 2 -> {
                    g.drawOval(x1, y1, ancho, alto);
                    gTemp.drawOval(x1, y1, ancho, alto);
                }
            }

            limpiarImagenTemporal();
            txtInfo.setText("");
            pnlDibujo.repaint();
        }
    }

    private void dibujarTemporal(int x, int y) {
        if (trazando) {
            limpiarImagenTemporal();
            Graphics2D g2 = imagenTemporal.createGraphics();
            g2.setColor(Color.YELLOW);

            int ancho = Math.abs(this.x - x);
            int alto = Math.abs(this.y - y);
            int x1 = Math.min(this.x, x);
            int y1 = Math.min(this.y, y);

            switch (cmbTipoTrazo.getSelectedIndex()) {
                case 0 -> g2.drawLine(this.x, this.y, x, y);
                case 1 -> g2.drawRect(x1, y1, ancho, alto);
                case 2 -> g2.drawOval(x1, y1, ancho, alto);
            }

            pnlDibujo.repaint();
        }
    }

    private void limpiarImagenTemporal() {
        Graphics2D g2 = imagenTemporal.createGraphics();
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, imagenTemporal.getWidth(), imagenTemporal.getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmTrazos().setVisible(true));
    }

}
