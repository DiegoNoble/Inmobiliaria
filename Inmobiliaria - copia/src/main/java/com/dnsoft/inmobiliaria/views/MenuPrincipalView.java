package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.controllers.ConsultaCCPropietariosController;
import com.dnsoft.inmobiliaria.controllers.ConsultaContratosController;
import com.dnsoft.inmobiliaria.controllers.ConsultaDeCajaController;
import com.dnsoft.inmobiliaria.controllers.ConsultaGastosInmueblesController;
import com.dnsoft.inmobiliaria.controllers.InmueblesController;
import com.dnsoft.inmobiliaria.controllers.ContratosController;
import com.dnsoft.inmobiliaria.controllers.ControlDeCajaController;
import com.dnsoft.inmobiliaria.controllers.InquilinosController;
import com.dnsoft.inmobiliaria.controllers.PropietariosController;
import com.dnsoft.inmobiliaria.controllers.ProveedoresController;
import com.dnsoft.inmobiliaria.utils.DecorateDesktopPane;
import javax.swing.*;

public final class MenuPrincipalView extends javax.swing.JFrame {

    public String perfil;
    DecorateDesktopPane desktopPane = new DecorateDesktopPane("/imagenes/fondo.jpg");
    //JDesktopPane desktopPane = new JDesktopPane();
    ImageIcon fot;
    ImageIcon icono;

    public MenuPrincipalView() {
        initComponents();

        setSize(1060, 768);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        add(desktopPane);
        //imagenDeFondo();

        CotizacionesView nuevaCotizacion = new CotizacionesView();
        nuevaCotizacion.setVisible(true);
        nuevaCotizacion.toFront();
        desktopPane.add(nuevaCotizacion);

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
        mnuAyuda2 = new javax.swing.JMenu();
        mnuCotizacion = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        mnuItemPropiedades2 = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        mnuItemPropiedades6 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
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
        mnuCotizacion9 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion12 = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JPopupMenu.Separator();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        mnuInquilinos1 = new javax.swing.JMenuItem();
        jSeparator22 = new javax.swing.JPopupMenu.Separator();
        mnuInquilinos = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        mnuItemPropietarios = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        mnuItemPropiedades = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        mnuVentas = new javax.swing.JMenu();
        mnuCotizacion1 = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JPopupMenu.Separator();
        mnuCotizacion11 = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        mnuItemPropiedades1 = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        mnuItemRegistrarPrestamo = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        mnuVentas1 = new javax.swing.JMenu();
        mnuItemRegistrarPrestamo1 = new javax.swing.JMenuItem();
        mnuAyuda1 = new javax.swing.JMenu();
        mnuItemPropiedades3 = new javax.swing.JMenuItem();
        mnuItemPropiedades4 = new javax.swing.JMenuItem();
        mnuItemPropiedades5 = new javax.swing.JMenuItem();
        mnuAyuda = new javax.swing.JMenu();
        mnuItemConsultaCuentasProveedores1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Inmobiliario - D.N.Soft .-");

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
        mnuSistema.setText("  Sistema  ");
        mnuSistema.setBorderPainted(true);
        mnuSistema.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

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

        mnuAyuda2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda2.setText(" Caja ");
        mnuAyuda2.setBorderPainted(true);
        mnuAyuda2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuAyuda2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        mnuCotizacion.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion.setText(" Cotización monedas");
        mnuCotizacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion.setBorderPainted(true);
        mnuCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacionActionPerformed(evt);
            }
        });
        mnuAyuda2.add(mnuCotizacion);
        mnuAyuda2.add(jSeparator16);

        mnuItemPropiedades2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades2.setText(" Movimientos de caja ");
        mnuItemPropiedades2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades2.setBorderPainted(true);
        mnuItemPropiedades2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades2ActionPerformed(evt);
            }
        });
        mnuAyuda2.add(mnuItemPropiedades2);
        mnuAyuda2.add(jSeparator15);

        mnuItemPropiedades6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades6.setText(" Consulta movimientos de caja ");
        mnuItemPropiedades6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades6.setBorderPainted(true);
        mnuItemPropiedades6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades6ActionPerformed(evt);
            }
        });
        mnuAyuda2.add(mnuItemPropiedades6);
        mnuAyuda2.add(jSeparator14);

        jMenuBar1.add(mnuAyuda2);

        mnuRegistros.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuRegistros.setText("  Registros  ");
        mnuRegistros.setBorderPainted(true);
        mnuRegistros.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

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

        mnuRegistros.add(jMenu1);
        mnuRegistros.add(jSeparator13);

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
        mnuRegistros.add(jSeparator22);

        mnuInquilinos.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuInquilinos.setText(" Inquilinos ");
        mnuInquilinos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuInquilinos.setBorderPainted(true);
        mnuInquilinos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInquilinosActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuInquilinos);
        mnuRegistros.add(jSeparator12);

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
        mnuRegistros.add(jSeparator11);

        mnuItemPropiedades.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades.setText(" Inmuebles / Asocia propietarios ");
        mnuItemPropiedades.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades.setBorderPainted(true);
        mnuItemPropiedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedadesActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemPropiedades);
        mnuRegistros.add(jSeparator10);

        jMenuBar1.add(mnuRegistros);

        mnuVentas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas.setText(" Contratos ");
        mnuVentas.setBorderPainted(true);
        mnuVentas.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        mnuCotizacion1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion1.setText(" Tipos de reajustes ");
        mnuCotizacion1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion1.setBorderPainted(true);
        mnuCotizacion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion1ActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuCotizacion1);
        mnuVentas.add(jSeparator20);

        mnuCotizacion11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion11.setText(" Cotizaciones reajustes");
        mnuCotizacion11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion11.setBorderPainted(true);
        mnuCotizacion11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion11ActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuCotizacion11);
        mnuVentas.add(jSeparator19);

        mnuItemPropiedades1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades1.setText(" Nuevo Contrato ");
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
        mnuItemRegistrarPrestamo.setText(" Consultas / Modificaciones  y pagos de alquileres ");
        mnuItemRegistrarPrestamo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo.setBorderPainted(true);
        mnuItemRegistrarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamoActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuItemRegistrarPrestamo);
        mnuVentas.add(jSeparator17);

        jMenuBar1.add(mnuVentas);

        mnuVentas1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas1.setText(" Cuentas Corrientes ");
        mnuVentas1.setBorderPainted(true);
        mnuVentas1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        mnuItemRegistrarPrestamo1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo1.setText(" Propietarios");
        mnuItemRegistrarPrestamo1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo1.setBorderPainted(true);
        mnuItemRegistrarPrestamo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamo1ActionPerformed(evt);
            }
        });
        mnuVentas1.add(mnuItemRegistrarPrestamo1);

        jMenuBar1.add(mnuVentas1);

        mnuAyuda1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda1.setText(" Control de Gastos de Inmuebles ");
        mnuAyuda1.setBorderPainted(true);
        mnuAyuda1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuAyuda1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        mnuItemPropiedades3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades3.setText(" Consulta Gastos por Propietarios ");
        mnuItemPropiedades3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades3.setBorderPainted(true);
        mnuItemPropiedades3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades3ActionPerformed(evt);
            }
        });
        mnuAyuda1.add(mnuItemPropiedades3);

        mnuItemPropiedades4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades4.setText(" Consulta Gastos por Inquilino");
        mnuItemPropiedades4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades4.setBorderPainted(true);
        mnuItemPropiedades4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades4ActionPerformed(evt);
            }
        });
        mnuAyuda1.add(mnuItemPropiedades4);

        mnuItemPropiedades5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades5.setText("Consulta gastos por Inmueble");
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
        mnuAyuda.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

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

        ConsultaCotizacionDialog consultaCotizacionDialog = new ConsultaCotizacionDialog(null, true);
        consultaCotizacionDialog.setVisible(true);
        consultaCotizacionDialog.toFront();
    }//GEN-LAST:event_mnuCotizacionActionPerformed

    private void mnuInquilinosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInquilinosActionPerformed

        InquilinosDlg view = new InquilinosDlg(this, true);
        InquilinosController controller = new InquilinosController(view);

        controller.go();
    }//GEN-LAST:event_mnuInquilinosActionPerformed

    private void mnuItemRegistrarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamoActionPerformed

        ConsultaContratosDialog view = new ConsultaContratosDialog(this, true);
        ConsultaContratosController controller = new ConsultaContratosController(view);

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

        ContratosDialog view = new ContratosDialog(this, true);
        ContratosController controller = new ContratosController(view);

        controller.go();


    }//GEN-LAST:event_mnuItemPropiedades1ActionPerformed

    private void mnuItemPropiedades2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedades2ActionPerformed
        ControlDeCajaView view = new ControlDeCajaView();
        ControlDeCajaController controller = new ControlDeCajaController(view, desktopPane);

        this.desktopPane.add(view);
        controller.go();

    }//GEN-LAST:event_mnuItemPropiedades2ActionPerformed

    private void mnuCotizacion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion1ActionPerformed
        TipoReajusteDialog detalleTipoReajuste = new TipoReajusteDialog(this, true, this);
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

    private void mnuCotizacion11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion11ActionPerformed
        CotizacionReajustesVariablesDialog cotizacionIndicesDialog = new CotizacionReajustesVariablesDialog(this, true);
        cotizacionIndicesDialog.setVisible(true);
        cotizacionIndicesDialog.toFront();
    }//GEN-LAST:event_mnuCotizacion11ActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondo;
    private javax.swing.JMenu jMenu1;
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
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
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
    private javax.swing.JMenuItem mnuCotizacion2;
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
    private javax.swing.JMenuItem mnuItemSalir;
    private javax.swing.JMenuItem mnuItemSesion;
    private javax.swing.JMenu mnuRegistros;
    private javax.swing.JMenu mnuSistema;
    private javax.swing.JMenu mnuVentas;
    private javax.swing.JMenu mnuVentas1;
    // End of variables declaration//GEN-END:variables
}
