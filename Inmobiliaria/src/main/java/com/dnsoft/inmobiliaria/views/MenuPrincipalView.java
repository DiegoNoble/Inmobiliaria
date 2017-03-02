package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.controllers.ConsultaContratosController;
import com.dnsoft.inmobiliaria.controllers.InmueblesController;
import com.dnsoft.inmobiliaria.controllers.ContratosController;
import com.dnsoft.inmobiliaria.controllers.ControlDeCajaController;
import com.dnsoft.inmobiliaria.controllers.InquilinosController;
import com.dnsoft.inmobiliaria.controllers.PropietariosController;
import com.dnsoft.inmobiliaria.views.detalles.DetalleBancos;
import com.dnsoft.inmobiliaria.views.detalles.DetalleBarrios;
import com.dnsoft.inmobiliaria.views.detalles.DetalleCalles;
import com.dnsoft.inmobiliaria.views.detalles.DetalleCiudades;
import com.dnsoft.inmobiliaria.views.detalles.DetalleComodidades;
import com.dnsoft.inmobiliaria.views.detalles.DetalleTipoDocumento;
import com.dnsoft.inmobiliaria.views.detalles.DetalleTipoInmueble;
import com.dnsoft.inmobiliaria.views.detalles.DetalleTipoReajuste;
import javax.swing.*;

public final class MenuPrincipalView extends javax.swing.JFrame {

    public String perfil;
    JDesktopPane desktopPane = new JDesktopPane();
    ImageIcon fot;
    ImageIcon icono;

    public MenuPrincipalView() {
        initComponents();
        setSize(1060, 768);
        setLocationRelativeTo(null);

        add(desktopPane);
        //imagenDeFondo();
        CotizacionView c = new CotizacionView();
        c.setVisible(true);
        c.toFront();
        this.desktopPane.add(c);

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
        mnuCotizacion = new javax.swing.JMenuItem();
        mnuCotizacion1 = new javax.swing.JMenuItem();
        mnuCotizacion2 = new javax.swing.JMenuItem();
        mnuCotizacion3 = new javax.swing.JMenuItem();
        mnuCotizacion4 = new javax.swing.JMenuItem();
        mnuCotizacion5 = new javax.swing.JMenuItem();
        mnuCotizacion6 = new javax.swing.JMenuItem();
        mnuCotizacion7 = new javax.swing.JMenuItem();
        mnuCotizacion8 = new javax.swing.JMenuItem();
        mnuInquilinos = new javax.swing.JMenuItem();
        mnuItemPropietarios = new javax.swing.JMenuItem();
        mnuItemPropiedades = new javax.swing.JMenuItem();
        mnuItemPropiedades1 = new javax.swing.JMenuItem();
        mnuItemPropiedades2 = new javax.swing.JMenuItem();
        mnuVentas = new javax.swing.JMenu();
        mnuItemRegistrarPrestamo = new javax.swing.JMenuItem();
        mnuAyuda = new javax.swing.JMenu();
        mnuItemConsultaCuentasProveedores1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de control comercial - D.N.Soft .-");

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

        mnuSistema.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuSistema.setText("  Sistema  ");
        mnuSistema.setBorderPainted(true);
        mnuSistema.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

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

        mnuRegistros.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuRegistros.setText("  Registros  ");
        mnuRegistros.setBorderPainted(true);
        mnuRegistros.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        jMenu1.setText("  Globales");
        jMenu1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        mnuCotizacion.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion.setText(" Cotización");
        mnuCotizacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion.setBorderPainted(true);
        mnuCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacionActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion);

        mnuCotizacion1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuCotizacion1.setText(" Tipos de reajustes ");
        mnuCotizacion1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuCotizacion1.setBorderPainted(true);
        mnuCotizacion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCotizacion1ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCotizacion1);

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

        mnuRegistros.add(jMenu1);

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

        mnuItemPropiedades1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades1.setText(" Contratos ");
        mnuItemPropiedades1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades1.setBorderPainted(true);
        mnuItemPropiedades1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades1ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemPropiedades1);

        mnuItemPropiedades2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemPropiedades2.setText(" Movimientos de caja ");
        mnuItemPropiedades2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemPropiedades2.setBorderPainted(true);
        mnuItemPropiedades2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPropiedades2ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemPropiedades2);

        jMenuBar1.add(mnuRegistros);

        mnuVentas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas.setText(" Consultas ");
        mnuVentas.setBorderPainted(true);
        mnuVentas.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemRegistrarPrestamo.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarPrestamo.setText(" Contratos ");
        mnuItemRegistrarPrestamo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarPrestamo.setBorderPainted(true);
        mnuItemRegistrarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarPrestamoActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuItemRegistrarPrestamo);

        jMenuBar1.add(mnuVentas);

        mnuAyuda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda.setText("  Ayuda  ");
        mnuAyuda.setBorderPainted(true);
        mnuAyuda.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuAyuda.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

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

        CotizacionView c = new CotizacionView();
        c.setVisible(true);
        c.toFront();
        this.desktopPane.add(c);
    }//GEN-LAST:event_mnuCotizacionActionPerformed

    private void mnuInquilinosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInquilinosActionPerformed

        InquilinosView view = new InquilinosView();
        InquilinosController controller = new InquilinosController(view);

        this.desktopPane.add(view);
        controller.go();
    }//GEN-LAST:event_mnuInquilinosActionPerformed

    private void mnuItemRegistrarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarPrestamoActionPerformed

        ConsultaContratoView view = new ConsultaContratoView();
        ConsultaContratosController controller = new ConsultaContratosController(view, desktopPane);

        controller.go();
    }//GEN-LAST:event_mnuItemRegistrarPrestamoActionPerformed

    private void mnuItemSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSesionActionPerformed


    }//GEN-LAST:event_mnuItemSesionActionPerformed

    private void mnuItemConsultaCuentasProveedores1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores1ActionPerformed

        JOptionPane.showMessageDialog(null, "<html><font size=6 face=Verdana color=black><center>Sistema de Prestamos<br><br>"
                + "Desarrollado por D.N.Soft</center><br><br>"
                + "<font size=5 face=Verdana color=black>Contactos: Diego Noble<br><br>"
                + "cel: 095639839<br><br>"
                + "E-mail: dnoblemello@gmail.com<br><br></html>");

    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores1ActionPerformed

    private void mnuItemPropietariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropietariosActionPerformed

        PropietarioView view = new PropietarioView();
        PropietariosController controller = new PropietariosController(view);

        this.desktopPane.add(view);
        controller.go();

    }//GEN-LAST:event_mnuItemPropietariosActionPerformed

    private void mnuItemPropiedadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedadesActionPerformed
        InmueblesView view = new InmueblesView();
        InmueblesController controller = new InmueblesController(view, this.desktopPane);

        this.desktopPane.add(view);
        controller.go();

    }//GEN-LAST:event_mnuItemPropiedadesActionPerformed

    private void mnuItemPropiedades1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedades1ActionPerformed

        ContratosView view = new ContratosView();
        ContratosController controller = new ContratosController(view, this.desktopPane);

        this.desktopPane.add(view);
        controller.go();


    }//GEN-LAST:event_mnuItemPropiedades1ActionPerformed

    private void mnuItemPropiedades2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPropiedades2ActionPerformed
        ControlDeCajaView view = new ControlDeCajaView();
        ControlDeCajaController controller = new ControlDeCajaController(view);

        this.desktopPane.add(view);
        controller.go();

    }//GEN-LAST:event_mnuItemPropiedades2ActionPerformed

    private void mnuCotizacion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCotizacion1ActionPerformed
        DetalleTipoReajuste detalleTipoReajuste = new DetalleTipoReajuste(this, true, this);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondo;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenu mnuAyuda;
    private javax.swing.JMenuItem mnuCotizacion;
    private javax.swing.JMenuItem mnuCotizacion1;
    private javax.swing.JMenuItem mnuCotizacion2;
    private javax.swing.JMenuItem mnuCotizacion3;
    private javax.swing.JMenuItem mnuCotizacion4;
    private javax.swing.JMenuItem mnuCotizacion5;
    private javax.swing.JMenuItem mnuCotizacion6;
    private javax.swing.JMenuItem mnuCotizacion7;
    private javax.swing.JMenuItem mnuCotizacion8;
    private javax.swing.JMenuItem mnuInquilinos;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores1;
    private javax.swing.JMenuItem mnuItemPropiedades;
    private javax.swing.JMenuItem mnuItemPropiedades1;
    private javax.swing.JMenuItem mnuItemPropiedades2;
    private javax.swing.JMenuItem mnuItemPropietarios;
    private javax.swing.JMenuItem mnuItemRegistrarPrestamo;
    private javax.swing.JMenuItem mnuItemSalir;
    private javax.swing.JMenuItem mnuItemSesion;
    private javax.swing.JMenu mnuRegistros;
    private javax.swing.JMenu mnuSistema;
    private javax.swing.JMenu mnuVentas;
    // End of variables declaration//GEN-END:variables
}
