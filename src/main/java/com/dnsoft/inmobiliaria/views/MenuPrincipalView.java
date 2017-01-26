package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.controllers.ConsultaCCPropietariosController;
import com.dnsoft.inmobiliaria.controllers.ConsultaContratosController;
import com.dnsoft.inmobiliaria.controllers.ConsultaDeCajaController;
import com.dnsoft.inmobiliaria.controllers.ConsultaGastosInmueblesController;
import com.dnsoft.inmobiliaria.controllers.InmueblesController;
import com.dnsoft.inmobiliaria.controllers.ContratosController;
import com.dnsoft.inmobiliaria.controllers.ControlDeCajaController;
import com.dnsoft.inmobiliaria.controllers.InformeComisionesController;
import com.dnsoft.inmobiliaria.controllers.InquilinosController;
import com.dnsoft.inmobiliaria.controllers.PropietariosController;
import com.dnsoft.inmobiliaria.controllers.ProveedoresController;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.DecorateDesktopPane;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.*;

public final class MenuPrincipalView extends javax.swing.JFrame {

    public String perfil;
    DecorateDesktopPane desktopPane = new DecorateDesktopPane("/imagenes/fondo.jpg");
    //JDesktopPane desktopPane = new JDesktopPane();
    ImageIcon fot;
    ImageIcon icono;
    Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png"));

    public MenuPrincipalView() {
        initComponents();
        //setIconImage(icon);
        /*Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png"));
         setIconImage(icon);
         setVisible(true);*/

        setSize(1060, 768);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        add(desktopPane);
        //imagenDeFondo();

        CotizacionesView nuevaCotizacion = new CotizacionesView();
        nuevaCotizacion.setVisible(true);
        nuevaCotizacion.toFront();
        desktopPane.add(nuevaCotizacion);

        //mnuItemPropiedades2ActionPerformed(null);
        //actualizaContratos();
    }

    void actualizaContratos() {

        new Thread() {
            @Override
            public void run() {
                Container container = Container.getInstancia();
                IContratoDAO contratosDAO = container.getBean(IContratoDAO.class);

                List<Contrato> listContratos = contratosDAO.findAll();

                for (Contrato contrato : listContratos) {
                    contrato.setDescripcionInmueble(contrato.getInmueble().toString());
                    contrato.setDescripcionInquilino(contrato.getInquilino().toString());
                    contratosDAO.save(contrato);
                }
            }
        }.start();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar2 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel3 = new javax.swing.JPanel();
        fondo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuSistema = new javax.swing.JMenu();
        mnuItemSesion = new javax.swing.JMenuItem();
        mnuItemSalir = new javax.swing.JMenuItem();
        mnuRegistros = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        mnuCotizacion10 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion2 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion4 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion5 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion6 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion7 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion8 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion3 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion28 = new javax.swing.JMenuItem();
        jSeparator48 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion29 = new javax.swing.JMenuItem();
        jSeparator49 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion9 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion12 = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion26 = new javax.swing.JMenuItem();
        jSeparator50 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion27 = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        mnuInquilinos = new javax.swing.JMenuItem();
        jSeparator22 = new javax.swing.JPopupMenu.Separator();
        mnuItemPropietarios = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        mnuItemPropiedades = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        mnuInquilinos1 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        mnuAyuda2 = new javax.swing.JMenu();
        mnuItemPropiedades2 = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        mnuItemPropiedades6 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        mnuVentas = new javax.swing.JMenu();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        mnuItemPropiedades1 = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion1 = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        mnuVentas1 = new javax.swing.JMenu();
        mnuItemRegistrarPrestamo1 = new javax.swing.JMenuItem();
        jSeparator23 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo2 = new javax.swing.JMenuItem();
        mnuVentas2 = new javax.swing.JMenu();
        mnuItemRegistrarPrestamo3 = new javax.swing.JMenuItem();
        jSeparator24 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo4 = new javax.swing.JMenuItem();
        jSeparator27 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo5 = new javax.swing.JMenuItem();
        jSeparator26 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo6 = new javax.swing.JMenuItem();
        jSeparator25 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo7 = new javax.swing.JMenuItem();
        mnuVentas3 = new javax.swing.JMenu();
        mnuItemRegistrarPrestamo8 = new javax.swing.JMenuItem();
        jSeparator28 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo9 = new javax.swing.JMenuItem();
        jSeparator31 = new javax.swing.JPopupMenu.Separator();
        jMenu2 = new javax.swing.JMenu();
        mnuCotizacion11 = new javax.swing.JMenuItem();
        jSeparator36 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion13 = new javax.swing.JMenuItem();
        jSeparator37 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion14 = new javax.swing.JMenuItem();
        jSeparator38 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion15 = new javax.swing.JMenuItem();
        jSeparator39 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion16 = new javax.swing.JMenuItem();
        jSeparator40 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion17 = new javax.swing.JMenuItem();
        jSeparator41 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion18 = new javax.swing.JMenuItem();
        jSeparator29 = new javax.swing.JPopupMenu.Separator();
        jMenu3 = new javax.swing.JMenu();
        mnuCotizacion19 = new javax.swing.JMenuItem();
        jSeparator42 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion20 = new javax.swing.JMenuItem();
        jSeparator43 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion21 = new javax.swing.JMenuItem();
        jSeparator44 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion22 = new javax.swing.JMenuItem();
        jSeparator45 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion23 = new javax.swing.JMenuItem();
        jSeparator46 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion24 = new javax.swing.JMenuItem();
        jSeparator47 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion25 = new javax.swing.JMenuItem();
        jSeparator30 = new javax.swing.JPopupMenu.Separator();
        mnuVentas4 = new javax.swing.JMenu();
        mnuItemRegistrarPrestamo12 = new javax.swing.JMenuItem();
        jSeparator32 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo13 = new javax.swing.JMenuItem();
        jSeparator33 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo14 = new javax.swing.JMenuItem();
        jSeparator34 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo15 = new javax.swing.JMenuItem();
        jSeparator35 = new javax.swing.JPopupMenu.Separator();
        mnuAyuda1 = new javax.swing.JMenu();
        mnuItemPropiedades3 = new javax.swing.JMenuItem();
        mnuItemPropiedades4 = new javax.swing.JMenuItem();
        mnuItemPropiedades5 = new javax.swing.JMenuItem();
        mnuAyuda = new javax.swing.JMenu();
        mnuItemConsultaCuentasProveedores1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Inmobiliario - D.N.Soft .-");
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/logoTrans.png")).getImage());

        jToolBar2.setRollover(true);

        jPanel1.setLayout(new java.awt.GridBagLayout());
        jToolBar2.add(jPanel1);

        getContentPane().add(jToolBar2, java.awt.BorderLayout.SOUTH);

        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        jPanel3.setLayout(new java.awt.GridBagLayout());
        jToolBar1.add(jPanel3);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.WEST);

        fondo.setName("fondo"); // NOI18N
        getContentPane().add(fondo, java.awt.BorderLayout.CENTER);

        jMenuBar1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N

        mnuSistema.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuSistema.setText(" SISTEMA ");
        mnuSistema.setBorderPainted(true);
        mnuSistema.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        mnuItemSesion.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemSesion.setText(" Cerrar sesión ");
        mnuItemSesion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemSesion.setBorderPainted(true);
        mnuItemSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSesionActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemSesion);

        mnuItemSalir.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemSalir.setText(" Salir ");
        mnuItemSalir.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemSalir.setBorderPainted(true);
        mnuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSalirActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemSalir);

        jMenuBar1.add(mnuSistema);

        mnuRegistros.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuRegistros.setText(" REGISTROS ");
        mnuRegistros.setBorderPainted(true);
        mnuRegistros.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jMenu1.setText("  Globales");
        jMenu1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        mnuCotizacion10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion10.setText(" Parametros Globales ");
        mnuCotizacion10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion10.setBorderPainted(true);
        mnuCotizacion10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion10ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion10);
        jMenu1.add(jSeparator6);

        mnuCotizacion2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion2.setText(" Calles");
        mnuCotizacion2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion2.setBorderPainted(true);
        mnuCotizacion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion2ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion2);
        jMenu1.add(jSeparator5);

        mnuCotizacion4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion4.setText(" Ciudades");
        mnuCotizacion4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion4.setBorderPainted(true);
        mnuCotizacion4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion4ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion4);
        jMenu1.add(jSeparator4);

        mnuCotizacion5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion5.setText(" Comodidades ");
        mnuCotizacion5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion5.setBorderPainted(true);
        mnuCotizacion5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion5ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion5);
        jMenu1.add(jSeparator3);

        mnuCotizacion6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion6.setText(" Tipos de inmueble");
        mnuCotizacion6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion6.setBorderPainted(true);
        mnuCotizacion6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion6ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion6);
        jMenu1.add(jSeparator2);

        mnuCotizacion7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion7.setText(" Bancos");
        mnuCotizacion7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion7.setBorderPainted(true);
        mnuCotizacion7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion7ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion7);
        jMenu1.add(jSeparator1);

        mnuCotizacion8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion8.setText(" Tipos de documento");
        mnuCotizacion8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion8.setBorderPainted(true);
        mnuCotizacion8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion8ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion8);
        jMenu1.add(jSeparator8);

        mnuCotizacion3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion3.setText(" Barrios");
        mnuCotizacion3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion3.setBorderPainted(true);
        mnuCotizacion3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion3ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion3);
        jMenu1.add(jSeparator7);

        mnuCotizacion28.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion28.setText(" Garantías alquileres");
        mnuCotizacion28.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion28.setBorderPainted(true);
        mnuCotizacion28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion28ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion28);
        jMenu1.add(jSeparator48);

        mnuCotizacion29.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion29.setText(" Destino alquileres");
        mnuCotizacion29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion29.setBorderPainted(true);
        mnuCotizacion29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion29ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion29);
        jMenu1.add(jSeparator49);

        mnuCotizacion9.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion9.setText(" Rubros ");
        mnuCotizacion9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion9.setBorderPainted(true);
        mnuCotizacion9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion9ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion9);
        jMenu1.add(jSeparator9);

        mnuCotizacion12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion12.setText(" Tipos de Caja ");
        mnuCotizacion12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion12.setBorderPainted(true);
        mnuCotizacion12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion12ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion12);
        jMenu1.add(jSeparator21);

        mnuCotizacion26.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion26.setText(" Actualiza descripción Contratos");
        mnuCotizacion26.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion26.setBorderPainted(true);
        mnuCotizacion26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion26ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion26);
        jMenu1.add(jSeparator50);

        mnuCotizacion27.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion27.setText(" Lugar y forma de pago");
        mnuCotizacion27.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion27.setBorderPainted(true);
        mnuCotizacion27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion27ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion27);

        mnuRegistros.add(jMenu1);
        mnuRegistros.add(jSeparator13);

        mnuInquilinos.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuInquilinos.setText(" Inquilinos / Compradores");
        mnuInquilinos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuInquilinos.setBorderPainted(true);
        mnuInquilinos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInquilinosActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuInquilinos);
        mnuRegistros.add(jSeparator22);

        mnuItemPropietarios.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropietarios.setText(" Propietarios ");
        mnuItemPropietarios.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropietarios.setBorderPainted(true);
        mnuItemPropietarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropietariosActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemPropietarios);
        mnuRegistros.add(jSeparator12);

        mnuItemPropiedades.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades.setText(" Inmuebles ");
        mnuItemPropiedades.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades.setBorderPainted(true);
        mnuItemPropiedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedadesActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemPropiedades);
        mnuRegistros.add(jSeparator11);

        mnuInquilinos1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuInquilinos1.setText(" Proveedores ");
        mnuInquilinos1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuInquilinos1.setBorderPainted(true);
        mnuInquilinos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInquilinos1ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuInquilinos1);
        mnuRegistros.add(jSeparator10);

        jMenuBar1.add(mnuRegistros);

        mnuAyuda2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda2.setText(" CAJA ");
        mnuAyuda2.setBorderPainted(true);
        mnuAyuda2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuAyuda2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        mnuItemPropiedades2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades2.setText(" Movimientos  ");
        mnuItemPropiedades2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades2.setBorderPainted(true);
        mnuItemPropiedades2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades2ActionPerformed(evt);
            }
        });
        mnuAyuda2.add(mnuItemPropiedades2);
        mnuAyuda2.add(jSeparator16);

        mnuItemPropiedades6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades6.setText(" Consultas ");
        mnuItemPropiedades6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades6.setBorderPainted(true);
        mnuItemPropiedades6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades6ActionPerformed(evt);
            }
        });
        mnuAyuda2.add(mnuItemPropiedades6);
        mnuAyuda2.add(jSeparator14);

        mnuCotizacion.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion.setText(" Cotizaciónes");
        mnuCotizacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion.setBorderPainted(true);
        mnuCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacionActionPerformed(evt);
            }
        });
        mnuAyuda2.add(mnuCotizacion);
        mnuAyuda2.add(jSeparator15);

        jMenuBar1.add(mnuAyuda2);

        mnuVentas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas.setText(" CONTRATOS ");
        mnuVentas.setBorderPainted(true);
        mnuVentas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        mnuVentas.add(jSeparator19);

        mnuItemPropiedades1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades1.setText(" Nuevo ");
        mnuItemPropiedades1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades1.setBorderPainted(true);
        mnuItemPropiedades1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades1ActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuItemPropiedades1);
        mnuVentas.add(jSeparator18);

        mnuItemRegistrarPrestamo.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo.setText(" Consultas/Modifica/Pagos ");
        mnuItemRegistrarPrestamo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo.setBorderPainted(true);
        mnuItemRegistrarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamoActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuItemRegistrarPrestamo);
        mnuVentas.add(jSeparator20);

        mnuCotizacion1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion1.setText(" Reajustes ");
        mnuCotizacion1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion1.setBorderPainted(true);
        mnuCotizacion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion1ActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuCotizacion1);
        mnuVentas.add(jSeparator17);

        jMenuBar1.add(mnuVentas);

        mnuVentas1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas1.setText(" PROPIETARIOS ");
        mnuVentas1.setBorderPainted(true);
        mnuVentas1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        mnuItemRegistrarPrestamo1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo1.setText(" Estado de cuenta/Retiros");
        mnuItemRegistrarPrestamo1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo1.setBorderPainted(true);
        mnuItemRegistrarPrestamo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo1ActionPerformed(evt);
            }
        });
        mnuVentas1.add(mnuItemRegistrarPrestamo1);
        mnuVentas1.add(jSeparator23);

        mnuItemRegistrarPrestamo2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo2.setText(" Movimientos ");
        mnuItemRegistrarPrestamo2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo2.setBorderPainted(true);
        mnuItemRegistrarPrestamo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo2ActionPerformed(evt);
            }
        });
        mnuVentas1.add(mnuItemRegistrarPrestamo2);

        jMenuBar1.add(mnuVentas1);

        mnuVentas2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas2.setText(" CONSULTAS/LISTADOS");
        mnuVentas2.setBorderPainted(true);
        mnuVentas2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        mnuItemRegistrarPrestamo3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo3.setText(" Propietarios ");
        mnuItemRegistrarPrestamo3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo3.setBorderPainted(true);
        mnuItemRegistrarPrestamo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo3ActionPerformed(evt);
            }
        });
        mnuVentas2.add(mnuItemRegistrarPrestamo3);
        mnuVentas2.add(jSeparator24);

        mnuItemRegistrarPrestamo4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo4.setText(" Inquilinos ");
        mnuItemRegistrarPrestamo4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo4.setBorderPainted(true);
        mnuItemRegistrarPrestamo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo4ActionPerformed(evt);
            }
        });
        mnuVentas2.add(mnuItemRegistrarPrestamo4);
        mnuVentas2.add(jSeparator27);

        mnuItemRegistrarPrestamo5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo5.setText(" Viviendas ");
        mnuItemRegistrarPrestamo5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo5.setBorderPainted(true);
        mnuItemRegistrarPrestamo5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo5ActionPerformed(evt);
            }
        });
        mnuVentas2.add(mnuItemRegistrarPrestamo5);
        mnuVentas2.add(jSeparator26);

        mnuItemRegistrarPrestamo6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo6.setText(" Garantías ");
        mnuItemRegistrarPrestamo6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo6.setBorderPainted(true);
        mnuItemRegistrarPrestamo6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo6ActionPerformed(evt);
            }
        });
        mnuVentas2.add(mnuItemRegistrarPrestamo6);
        mnuVentas2.add(jSeparator25);

        mnuItemRegistrarPrestamo7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo7.setText(" Contratos encerrados ");
        mnuItemRegistrarPrestamo7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo7.setBorderPainted(true);
        mnuItemRegistrarPrestamo7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo7ActionPerformed(evt);
            }
        });
        mnuVentas2.add(mnuItemRegistrarPrestamo7);

        jMenuBar1.add(mnuVentas2);

        mnuVentas3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas3.setText(" ESTADÍSTICAS ");
        mnuVentas3.setBorderPainted(true);
        mnuVentas3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        mnuItemRegistrarPrestamo8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo8.setText(" Movimientos por rubro ");
        mnuItemRegistrarPrestamo8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo8.setBorderPainted(true);
        mnuItemRegistrarPrestamo8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo8ActionPerformed(evt);
            }
        });
        mnuVentas3.add(mnuItemRegistrarPrestamo8);
        mnuVentas3.add(jSeparator28);

        mnuItemRegistrarPrestamo9.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo9.setText(" Depósitos en garantía ");
        mnuItemRegistrarPrestamo9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo9.setBorderPainted(true);
        mnuItemRegistrarPrestamo9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo9ActionPerformed(evt);
            }
        });
        mnuVentas3.add(mnuItemRegistrarPrestamo9);
        mnuVentas3.add(jSeparator31);

        jMenu2.setText(" Ganancias ");
        jMenu2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        mnuCotizacion11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion11.setText(" Comisiones alquileres");
        mnuCotizacion11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion11.setBorderPainted(true);
        mnuCotizacion11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion11ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuCotizacion11);
        jMenu2.add(jSeparator36);

        mnuCotizacion13.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion13.setText(" Comisiones cuotas ");
        mnuCotizacion13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion13.setBorderPainted(true);
        mnuCotizacion13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion13ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuCotizacion13);
        jMenu2.add(jSeparator37);

        mnuCotizacion14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion14.setText(" Comisiones ventas");
        mnuCotizacion14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion14.setBorderPainted(true);
        mnuCotizacion14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion14ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuCotizacion14);
        jMenu2.add(jSeparator38);

        mnuCotizacion15.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion15.setText(" Honorarios contratos");
        mnuCotizacion15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion15.setBorderPainted(true);
        mnuCotizacion15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion15ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuCotizacion15);
        jMenu2.add(jSeparator39);

        mnuCotizacion16.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion16.setText(" Moras (adm.)");
        mnuCotizacion16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion16.setBorderPainted(true);
        mnuCotizacion16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion16ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuCotizacion16);
        jMenu2.add(jSeparator40);

        mnuCotizacion17.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion17.setText(" Gestiones de pago");
        mnuCotizacion17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion17.setBorderPainted(true);
        mnuCotizacion17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion17ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuCotizacion17);
        jMenu2.add(jSeparator41);

        mnuCotizacion18.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion18.setText(" Gestiones mantenimiento");
        mnuCotizacion18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion18.setBorderPainted(true);
        mnuCotizacion18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion18ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuCotizacion18);

        mnuVentas3.add(jMenu2);
        mnuVentas3.add(jSeparator29);

        jMenu3.setText(" Contratos ");
        jMenu3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        mnuCotizacion19.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion19.setText(" Comisiones alquileres");
        mnuCotizacion19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion19.setBorderPainted(true);
        mnuCotizacion19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion19ActionPerformed(evt);
            }
        });
        jMenu3.add(mnuCotizacion19);
        jMenu3.add(jSeparator42);

        mnuCotizacion20.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion20.setText(" Comisiones cuotas ");
        mnuCotizacion20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion20.setBorderPainted(true);
        mnuCotizacion20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion20ActionPerformed(evt);
            }
        });
        jMenu3.add(mnuCotizacion20);
        jMenu3.add(jSeparator43);

        mnuCotizacion21.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion21.setText(" Comisiones ventas");
        mnuCotizacion21.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion21.setBorderPainted(true);
        mnuCotizacion21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion21ActionPerformed(evt);
            }
        });
        jMenu3.add(mnuCotizacion21);
        jMenu3.add(jSeparator44);

        mnuCotizacion22.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion22.setText(" Honorarios contratos");
        mnuCotizacion22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion22.setBorderPainted(true);
        mnuCotizacion22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion22ActionPerformed(evt);
            }
        });
        jMenu3.add(mnuCotizacion22);
        jMenu3.add(jSeparator45);

        mnuCotizacion23.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion23.setText(" Moras (adm.)");
        mnuCotizacion23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion23.setBorderPainted(true);
        mnuCotizacion23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion23ActionPerformed(evt);
            }
        });
        jMenu3.add(mnuCotizacion23);
        jMenu3.add(jSeparator46);

        mnuCotizacion24.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion24.setText(" Gestiones de pago");
        mnuCotizacion24.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion24.setBorderPainted(true);
        mnuCotizacion24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion24ActionPerformed(evt);
            }
        });
        jMenu3.add(mnuCotizacion24);
        jMenu3.add(jSeparator47);

        mnuCotizacion25.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion25.setText(" Gestiones mantenimiento");
        mnuCotizacion25.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion25.setBorderPainted(true);
        mnuCotizacion25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion25ActionPerformed(evt);
            }
        });
        jMenu3.add(mnuCotizacion25);

        mnuVentas3.add(jMenu3);
        mnuVentas3.add(jSeparator30);

        jMenuBar1.add(mnuVentas3);

        mnuVentas4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas4.setText(" ADMINISTRACIÓN ");
        mnuVentas4.setBorderPainted(true);
        mnuVentas4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        mnuItemRegistrarPrestamo12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo12.setText("Cierre mes asegurado ");
        mnuItemRegistrarPrestamo12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo12.setBorderPainted(true);
        mnuItemRegistrarPrestamo12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo12ActionPerformed(evt);
            }
        });
        mnuVentas4.add(mnuItemRegistrarPrestamo12);
        mnuVentas4.add(jSeparator32);

        mnuItemRegistrarPrestamo13.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo13.setText(" Facturación ");
        mnuItemRegistrarPrestamo13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo13.setBorderPainted(true);
        mnuItemRegistrarPrestamo13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo13ActionPerformed(evt);
            }
        });
        mnuVentas4.add(mnuItemRegistrarPrestamo13);
        mnuVentas4.add(jSeparator33);

        mnuItemRegistrarPrestamo14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo14.setText(" Datos Beta ");
        mnuItemRegistrarPrestamo14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo14.setBorderPainted(true);
        mnuItemRegistrarPrestamo14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo14ActionPerformed(evt);
            }
        });
        mnuVentas4.add(mnuItemRegistrarPrestamo14);
        mnuVentas4.add(jSeparator34);

        mnuItemRegistrarPrestamo15.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo15.setText(" Usuarios ");
        mnuItemRegistrarPrestamo15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo15.setBorderPainted(true);
        mnuItemRegistrarPrestamo15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo15ActionPerformed(evt);
            }
        });
        mnuVentas4.add(mnuItemRegistrarPrestamo15);
        mnuVentas4.add(jSeparator35);

        jMenuBar1.add(mnuVentas4);

        mnuAyuda1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda1.setText(" Control de Gastos");
        mnuAyuda1.setBorderPainted(true);
        mnuAyuda1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuAyuda1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        mnuItemPropiedades3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades3.setText(" Gastos por Propietarios ");
        mnuItemPropiedades3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades3.setBorderPainted(true);
        mnuItemPropiedades3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades3ActionPerformed(evt);
            }
        });
        mnuAyuda1.add(mnuItemPropiedades3);

        mnuItemPropiedades4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades4.setText(" Gastos por Inquilino");
        mnuItemPropiedades4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades4.setBorderPainted(true);
        mnuItemPropiedades4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades4ActionPerformed(evt);
            }
        });
        mnuAyuda1.add(mnuItemPropiedades4);

        mnuItemPropiedades5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades5.setText(" Gastos por Inmueble");
        mnuItemPropiedades5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades5.setBorderPainted(true);
        mnuItemPropiedades5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades5ActionPerformed(evt);
            }
        });
        mnuAyuda1.add(mnuItemPropiedades5);

        jMenuBar1.add(mnuAyuda1);

        mnuAyuda.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda.setText("  Ayuda  ");
        mnuAyuda.setBorderPainted(true);
        mnuAyuda.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuAyuda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        mnuItemConsultaCuentasProveedores1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores1.setText("  Sobre  ");
        mnuItemConsultaCuentasProveedores1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores1.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores1ActionPerformed(evt);
            }
        });
        mnuAyuda.add(mnuItemConsultaCuentasProveedores1);

        jMenuBar1.add(mnuAyuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuItemSalirActionPerformed

    private void mnuCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacionActionPerformed

        CotizacionesView nuevaCotizacion = new CotizacionesView();
        desktopPane.add(nuevaCotizacion);
        nuevaCotizacion.setVisible(true);
        nuevaCotizacion.toFront();

    }//GEN-LAST:event_mnuCotizacionActionPerformed

    private void mnuInquilinosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInquilinosActionPerformed

        InquilinosDialog view = new InquilinosDialog(null, false);
        InquilinosController controller = new InquilinosController(view, desktopPane);

        controller.go();


    }//GEN-LAST:event_mnuInquilinosActionPerformed

    private void mnuItemRegistrarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamoActionPerformed

        ConsultaContratosInternal view = new ConsultaContratosInternal();
        ConsultaContratosController controller = new ConsultaContratosController(view, desktopPane);
        view.setVisible(true);
        view.toFront();
        desktopPane.add(view);
        view.setSize(desktopPane.getSize());

        controller.go();
    }//GEN-LAST:event_mnuItemRegistrarPrestamoActionPerformed

    private void mnuItemSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSesionActionPerformed


    }//GEN-LAST:event_mnuItemSesionActionPerformed

    private void mnuItemConsultaCuentasProveedores1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores1ActionPerformed

        JOptionPane.showMessageDialog(null, "<html><font size=6 face=Verdana color=black><center>Sistema de control Inmobiliario<br><br>"
                + "Desarrollado por D.N.Soft</center><br><br>"
                + "<font size=5 face=Verdana color=black>Contactos: Diego Noble<br><br>"
                + "cel: 095639839<br><br>"
                + "E-mail: dnoblemello@gmail.com<br><br></html>");

    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores1ActionPerformed

    private void mnuItemPropietariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropietariosActionPerformed

        PropietariosDialog view = new PropietariosDialog(this, true);
        PropietariosController controller = new PropietariosController(view);

        controller.go();

    }//GEN-LAST:event_mnuItemPropietariosActionPerformed

    private void mnuItemPropiedadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedadesActionPerformed
        InmueblesDialog view = new InmueblesDialog(this, true);
        InmueblesController controller = new InmueblesController(view);

        controller.go();

    }//GEN-LAST:event_mnuItemPropiedadesActionPerformed

    private void mnuItemPropiedades1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedades1ActionPerformed

        ContratosDialog view = new ContratosDialog(this, false);
        ContratosController controller = new ContratosController(view, desktopPane, null);

        controller.go();


    }//GEN-LAST:event_mnuItemPropiedades1ActionPerformed

    private void mnuItemPropiedades2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedades2ActionPerformed
        ControlDeCajaView view = new ControlDeCajaView();
        ControlDeCajaController controller = new ControlDeCajaController(view, desktopPane);

        this.desktopPane.add(view);
        controller.go();

    }//GEN-LAST:event_mnuItemPropiedades2ActionPerformed

    private void mnuCotizacion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion1ActionPerformed
        TipoReajusteDialog detalleTipoReajuste = new TipoReajusteDialog(this, false, this, null);
        detalleTipoReajuste.setVisible(true);
        detalleTipoReajuste.toFront();
    }//GEN-LAST:event_mnuCotizacion1ActionPerformed

    private void mnuCotizacion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion2ActionPerformed
        DetalleCalles calles = new DetalleCalles(this, true, this);
        calles.setVisible(true);
        calles.toFront();
    }//GEN-LAST:event_mnuCotizacion2ActionPerformed

    private void mnuCotizacion3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion3ActionPerformed
        DetalleBarrios barrios = new DetalleBarrios(this, true, this);
        barrios.setVisible(true);
        barrios.toFront();
    }//GEN-LAST:event_mnuCotizacion3ActionPerformed

    private void mnuCotizacion4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion4ActionPerformed
        DetalleCiudades ciudades = new DetalleCiudades(this, true, this);
        ciudades.setVisible(true);
        ciudades.toFront();
    }//GEN-LAST:event_mnuCotizacion4ActionPerformed

    private void mnuCotizacion5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion5ActionPerformed
        DetalleComodidades comodidades = new DetalleComodidades(this, true, this);
        comodidades.setVisible(true);
        comodidades.toFront();

    }//GEN-LAST:event_mnuCotizacion5ActionPerformed

    private void mnuCotizacion6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion6ActionPerformed
        DetalleTipoInmueble detalleTipoInmueble = new DetalleTipoInmueble(this, true, this);
        detalleTipoInmueble.setVisible(true);
        detalleTipoInmueble.toFront();

    }//GEN-LAST:event_mnuCotizacion6ActionPerformed

    private void mnuCotizacion7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion7ActionPerformed

        DetalleBancos detalleBancos = new DetalleBancos(this, true, this);
        detalleBancos.setVisible(true);
        detalleBancos.toFront();


    }//GEN-LAST:event_mnuCotizacion7ActionPerformed

    private void mnuCotizacion8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion8ActionPerformed

        DetalleTipoDocumento detalleDetalleTipoDocumento = new DetalleTipoDocumento(this, true, this);
        detalleDetalleTipoDocumento.setVisible(true);
        detalleDetalleTipoDocumento.toFront();

    }//GEN-LAST:event_mnuCotizacion8ActionPerformed

    private void mnuCotizacion9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion9ActionPerformed

        RubroDlg detalleDetalleTipoGasto = new RubroDlg(this, true, this);
        detalleDetalleTipoGasto.setVisible(true);
        detalleDetalleTipoGasto.toFront();
    }//GEN-LAST:event_mnuCotizacion9ActionPerformed

    private void mnuItemPropiedades3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedades3ActionPerformed
        GastosInmueblePropietario detalleGastosPropietarioPropietario = new GastosInmueblePropietario(this, true);
        detalleGastosPropietarioPropietario.setVisible(true);
        detalleGastosPropietarioPropietario.toFront();

    }//GEN-LAST:event_mnuItemPropiedades3ActionPerformed

    private void mnuItemPropiedades4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedades4ActionPerformed
        GastosInmuebleInquilino detalleGastosPropietarioInquilino = new GastosInmuebleInquilino(this, true);
        detalleGastosPropietarioInquilino.setVisible(true);
        detalleGastosPropietarioInquilino.toFront();

    }//GEN-LAST:event_mnuItemPropiedades4ActionPerformed

    private void mnuItemPropiedades5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedades5ActionPerformed

        ConsultaGastosInmueblesDialog view = new ConsultaGastosInmueblesDialog(this, true);
        ConsultaGastosInmueblesController controller = new ConsultaGastosInmueblesController(view);

        controller.go();


    }//GEN-LAST:event_mnuItemPropiedades5ActionPerformed

    private void mnuItemRegistrarPrestamo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo1ActionPerformed
        ConsultaCCPropietarioView view = new ConsultaCCPropietarioView();
        ConsultaCCPropietariosController controller = new ConsultaCCPropietariosController(view);

        this.desktopPane.add(view);
        controller.go();
    }//GEN-LAST:event_mnuItemRegistrarPrestamo1ActionPerformed

    private void mnuItemPropiedades6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedades6ActionPerformed
        ConsultaDeCajaView view = new ConsultaDeCajaView();
        ConsultaDeCajaController controller = new ConsultaDeCajaController(view, desktopPane);

        this.desktopPane.add(view);
        controller.go();
    }//GEN-LAST:event_mnuItemPropiedades6ActionPerformed

    private void mnuCotizacion10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion10ActionPerformed
        ParametrosView parametrosView = new ParametrosView(this, true);
        parametrosView.setVisible(true);
        parametrosView.toFront();
    }//GEN-LAST:event_mnuCotizacion10ActionPerformed

    private void mnuCotizacion12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion12ActionPerformed
        TipoDeCajaDlg tipoDeCajaDlg = new TipoDeCajaDlg(this, true);
        tipoDeCajaDlg.setVisible(true);
        tipoDeCajaDlg.toFront();
    }//GEN-LAST:event_mnuCotizacion12ActionPerformed

    private void mnuInquilinos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInquilinos1ActionPerformed

        ProveedoresDlg view = new ProveedoresDlg(this, true);
        ProveedoresController controller = new ProveedoresController(view);

        controller.go();

    }//GEN-LAST:event_mnuInquilinos1ActionPerformed

    private void mnuItemRegistrarPrestamo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo2ActionPerformed

    private void mnuItemRegistrarPrestamo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo3ActionPerformed

    private void mnuItemRegistrarPrestamo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo4ActionPerformed

    private void mnuItemRegistrarPrestamo5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo5ActionPerformed

    private void mnuItemRegistrarPrestamo6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo6ActionPerformed

    private void mnuItemRegistrarPrestamo7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo7ActionPerformed

    private void mnuItemRegistrarPrestamo8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo8ActionPerformed

    private void mnuItemRegistrarPrestamo9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo9ActionPerformed

    private void mnuItemRegistrarPrestamo12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo12ActionPerformed

    private void mnuItemRegistrarPrestamo13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo13ActionPerformed

        FacturacionView facturacion = new FacturacionView();
        this.desktopPane.add(facturacion);
        facturacion.setVisible(true);
        facturacion.toFront();

    }//GEN-LAST:event_mnuItemRegistrarPrestamo13ActionPerformed

    private void mnuItemRegistrarPrestamo14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo14ActionPerformed

    private void mnuItemRegistrarPrestamo15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamo15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuItemRegistrarPrestamo15ActionPerformed

    private void mnuCotizacion11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion11ActionPerformed

    private void mnuCotizacion13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion13ActionPerformed

    private void mnuCotizacion14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion14ActionPerformed

    private void mnuCotizacion15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion15ActionPerformed

    private void mnuCotizacion16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion16ActionPerformed

    private void mnuCotizacion17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion17ActionPerformed

    private void mnuCotizacion18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion18ActionPerformed

    private void mnuCotizacion19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion19ActionPerformed

    private void mnuCotizacion20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion20ActionPerformed

    private void mnuCotizacion21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion21ActionPerformed
        InformeComisiones view = new InformeComisiones(null, true);
        view.go();
    }//GEN-LAST:event_mnuCotizacion21ActionPerformed

    private void mnuCotizacion22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion22ActionPerformed

    private void mnuCotizacion23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion23ActionPerformed

    private void mnuCotizacion24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion24ActionPerformed

    private void mnuCotizacion25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCotizacion25ActionPerformed

    private void mnuCotizacion26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion26ActionPerformed
        actualizaContratos();

    }//GEN-LAST:event_mnuCotizacion26ActionPerformed

    private void mnuCotizacion28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion28ActionPerformed

        CRUDGarantiaAlquileres garantias = new CRUDGarantiaAlquileres(this, true, this, null);
        garantias.setVisible(true);
        garantias.toFront();

    }//GEN-LAST:event_mnuCotizacion28ActionPerformed

    private void mnuCotizacion29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion29ActionPerformed
        CRUDDestinoAlquiler destino = new CRUDDestinoAlquiler(this, true, this, null);
        destino.setVisible(true);
        destino.toFront();
    }//GEN-LAST:event_mnuCotizacion29ActionPerformed

    private void mnuCotizacion27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion27ActionPerformed
        DetalleLugarFormadePago lugarFormadePago = new DetalleLugarFormadePago(this, true, this);
        lugarFormadePago.setVisible(true);
        lugarFormadePago.toFront();
    }//GEN-LAST:event_mnuCotizacion27ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondo;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator15;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JPopupMenu.Separator jSeparator17;
    private javax.swing.JPopupMenu.Separator jSeparator18;
    private javax.swing.JPopupMenu.Separator jSeparator19;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator20;
    private javax.swing.JPopupMenu.Separator jSeparator21;
    private javax.swing.JPopupMenu.Separator jSeparator22;
    private javax.swing.JPopupMenu.Separator jSeparator23;
    private javax.swing.JPopupMenu.Separator jSeparator24;
    private javax.swing.JPopupMenu.Separator jSeparator25;
    private javax.swing.JPopupMenu.Separator jSeparator26;
    private javax.swing.JPopupMenu.Separator jSeparator27;
    private javax.swing.JPopupMenu.Separator jSeparator28;
    private javax.swing.JPopupMenu.Separator jSeparator29;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator30;
    private javax.swing.JPopupMenu.Separator jSeparator31;
    private javax.swing.JPopupMenu.Separator jSeparator32;
    private javax.swing.JPopupMenu.Separator jSeparator33;
    private javax.swing.JPopupMenu.Separator jSeparator34;
    private javax.swing.JPopupMenu.Separator jSeparator35;
    private javax.swing.JPopupMenu.Separator jSeparator36;
    private javax.swing.JPopupMenu.Separator jSeparator37;
    private javax.swing.JPopupMenu.Separator jSeparator38;
    private javax.swing.JPopupMenu.Separator jSeparator39;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator40;
    private javax.swing.JPopupMenu.Separator jSeparator41;
    private javax.swing.JPopupMenu.Separator jSeparator42;
    private javax.swing.JPopupMenu.Separator jSeparator43;
    private javax.swing.JPopupMenu.Separator jSeparator44;
    private javax.swing.JPopupMenu.Separator jSeparator45;
    private javax.swing.JPopupMenu.Separator jSeparator46;
    private javax.swing.JPopupMenu.Separator jSeparator47;
    private javax.swing.JPopupMenu.Separator jSeparator48;
    private javax.swing.JPopupMenu.Separator jSeparator49;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator50;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenu mnuAyuda;
    private javax.swing.JMenu mnuAyuda1;
    private javax.swing.JMenu mnuAyuda2;
    private javax.swing.JMenuItem mnuCotizacion;
    private javax.swing.JMenuItem mnuCotizacion1;
    private javax.swing.JMenuItem mnuCotizacion10;
    private javax.swing.JMenuItem mnuCotizacion11;
    private javax.swing.JMenuItem mnuCotizacion12;
    private javax.swing.JMenuItem mnuCotizacion13;
    private javax.swing.JMenuItem mnuCotizacion14;
    private javax.swing.JMenuItem mnuCotizacion15;
    private javax.swing.JMenuItem mnuCotizacion16;
    private javax.swing.JMenuItem mnuCotizacion17;
    private javax.swing.JMenuItem mnuCotizacion18;
    private javax.swing.JMenuItem mnuCotizacion19;
    private javax.swing.JMenuItem mnuCotizacion2;
    private javax.swing.JMenuItem mnuCotizacion20;
    private javax.swing.JMenuItem mnuCotizacion21;
    private javax.swing.JMenuItem mnuCotizacion22;
    private javax.swing.JMenuItem mnuCotizacion23;
    private javax.swing.JMenuItem mnuCotizacion24;
    private javax.swing.JMenuItem mnuCotizacion25;
    private javax.swing.JMenuItem mnuCotizacion26;
    private javax.swing.JMenuItem mnuCotizacion27;
    private javax.swing.JMenuItem mnuCotizacion28;
    private javax.swing.JMenuItem mnuCotizacion29;
    private javax.swing.JMenuItem mnuCotizacion3;
    private javax.swing.JMenuItem mnuCotizacion4;
    private javax.swing.JMenuItem mnuCotizacion5;
    private javax.swing.JMenuItem mnuCotizacion6;
    private javax.swing.JMenuItem mnuCotizacion7;
    private javax.swing.JMenuItem mnuCotizacion8;
    private javax.swing.JMenuItem mnuCotizacion9;
    private javax.swing.JMenuItem mnuInquilinos;
    private javax.swing.JMenuItem mnuInquilinos1;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores1;
    private javax.swing.JMenuItem mnuItemPropiedades;
    private javax.swing.JMenuItem mnuItemPropiedades1;
    private javax.swing.JMenuItem mnuItemPropiedades2;
    private javax.swing.JMenuItem mnuItemPropiedades3;
    private javax.swing.JMenuItem mnuItemPropiedades4;
    private javax.swing.JMenuItem mnuItemPropiedades5;
    private javax.swing.JMenuItem mnuItemPropiedades6;
    private javax.swing.JMenuItem mnuItemPropietarios;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo1;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo12;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo13;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo14;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo15;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo2;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo3;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo4;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo5;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo6;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo7;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo8;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo9;
    private javax.swing.JMenuItem mnuItemSalir;
    private javax.swing.JMenuItem mnuItemSesion;
    private javax.swing.JMenu mnuRegistros;
    private javax.swing.JMenu mnuSistema;
    private javax.swing.JMenu mnuVentas;
    private javax.swing.JMenu mnuVentas1;
    private javax.swing.JMenu mnuVentas2;
    private javax.swing.JMenu mnuVentas3;
    private javax.swing.JMenu mnuVentas4;
    // End of variables declaration//GEN-END:variables

}
