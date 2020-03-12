package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Cotizacion;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.Parametros;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.beans.TipoCotizacionContrato;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.ICotizacionDAO;
import com.dnsoft.inmobiliaria.daos.IParametrosDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDeCajaDAO;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import com.dnsoft.inmobiliaria.utils.PagarRecibo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Noble
 */
public class PagoReciboDlg1 extends javax.swing.JDialog {

    Recibo reciboSeleccionado;
    BigDecimal mora;
    BigDecimal valorEntrega;
    BigDecimal valorEntregaDifCotizacion;
    Container container;
    BigDecimal importeAPagar;
    ITipoDeCajaDAO tipoDeCajaDAO;
    ICotizacionDAO cotizacionDAO;
    IParametrosDAO parametrosDAO;
    ICajaDAO cajaDAO;
    Contrato contratoSeleccionado;
    Moneda moneda;
    Cotizacion cotizacion;
    Cotizacion cotizacionOficial;
    List<Caja> listMovimientos;
    Parametros parametros;
    DecimalFormat formato;
    DecimalFormatSymbols simbolos = new DecimalFormatSymbols();

    public PagoReciboDlg1(java.awt.Frame parent, boolean modal, Recibo reciboSeleccionado, BigDecimal mora) {
        super(parent, modal);
        initComponents();

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));

        simbolos.setDecimalSeparator('.');
        this.container = Container.getInstancia();
        setLocationRelativeTo(null);
        this.reciboSeleccionado = reciboSeleccionado;
        this.mora = mora;
        this.contratoSeleccionado = reciboSeleccionado.getContrato();
        this.moneda = contratoSeleccionado.getMoneda();
        lblMoneda.setText(moneda.toString());
        lblMoneda1.setText(moneda.toString());
        lblMoneda2.setText(moneda.toString());
        inicio();

    }

    void inicio() {

        lblDetalleRecibo.setText("Contrato: " + reciboSeleccionado.getContrato().getId() + ", Recibo nro: " + reciboSeleccionado.getNroRecibo());

        if (reciboSeleccionado.getContrato().getPaga_banco() == true) {
            lblPagaEnBanco.setText("Este Contrato debe ser abonado en el Banco!");
        }else{
            lblPagaEnBanco.setText("");
        }

        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};

        importeAPagar = reciboSeleccionado.getSaldo().add(mora);
        txtCuota.setDocument(new ControlarEntradaTexto(10, chs));
        txtMora.setDocument(new ControlarEntradaTexto(10, chs));
        txtAPagar.setDocument(new ControlarEntradaTexto(10, chs));
        txtCotizacion.setDocument(new ControlarEntradaTexto(10, chs));
        txtAPagar.setText(importeAPagar.toString());
        txtCuota.setText(reciboSeleccionado.getSaldo().toString());
        txtMora.setText(reciboSeleccionado.getMora().toString());
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);
        cotizacionDAO = container.getBean(ICotizacionDAO.class);
        parametrosDAO = container.getBean(IParametrosDAO.class);
        cajaDAO = container.getBean(ICajaDAO.class);
        lblMsj.setVisible(false);

        this.parametros = parametrosDAO.findAll().get(0);

        switch (reciboSeleccionado.getMoneda()) {
            case DOLARES:
                formato = new DecimalFormat("#,##0.00", simbolos);
                break;
            case UNIDADES_INDEXADAS:
                formato = new DecimalFormat("#,##0.0000", simbolos);
                break;
            case UNIDADES_REAJUSTABLES:
                formato = new DecimalFormat("#.00", simbolos);
                break;
        }
        cotizacionOficial = (Cotizacion) cotizacionDAO.findLastCotizacion(moneda);

        cargaTiposDeCaja();
        cbTipoCotizacion.setModel(new DefaultComboBoxModel(TipoCotizacionContrato.values()));
        accionesBotones();

        if (contratoSeleccionado.getTipoCotizacionContrato() == TipoCotizacionContrato.FIJA) {
            txtCotizacion.setText(formato.format(contratoSeleccionado.getCotizacion()));
            cbTipoCotizacion.setSelectedItem(TipoCotizacionContrato.FIJA);
            txtCotizacion.setEditable(true);
        } else if (contratoSeleccionado.getTipoCotizacionContrato() == TipoCotizacionContrato.OFICIAL) {
            cbTipoCotizacion.setSelectedItem(TipoCotizacionContrato.OFICIAL);
            txtCotizacion.setText(formato.format(cotizacionOficial.getValor()));
            txtCotizacion.setEditable(false);
        }
        if (contratoSeleccionado.getMoneda() == Moneda.PESOS) {
            rbPesos.setVisible(true);
            rbDolares.setVisible(true);
        } else if (contratoSeleccionado.getMoneda() == Moneda.DOLARES) {
            rbPesos.setVisible(true);
            rbDolares.setVisible(true);
        } else if (contratoSeleccionado.getMoneda() == Moneda.UNIDADES_INDEXADAS) {
            rbDolares.setVisible(false);
        } else if (contratoSeleccionado.getMoneda() == Moneda.UNIDADES_REAJUSTABLES) {
            rbDolares.setVisible(false);
        }
        calculaImporte();
    }

    void accionesBotones() {

        btnPagar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                VerificaImporte();
            }
        });
        btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                dispose();
            }
        });

        rbPesos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                calculaImporte();
            }
        });

        rbDolares.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                calculaImporte();
            }
        }
        );

        cbTipoCotizacion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbTipoCotizacion.getSelectedItem() == TipoCotizacionContrato.FIJA) {
                    if (contratoSeleccionado.getTipoCotizacionContrato() == TipoCotizacionContrato.FIJA) {
                        cotizacion = new Cotizacion(contratoSeleccionado.getCotizacion());

                    } else {
                        cotizacion = cotizacionOficial;

                    }
                    calculaImporte();
                    txtCotizacion.setEditable(true);
                } else if (cbTipoCotizacion.getSelectedItem() == TipoCotizacionContrato.OFICIAL) {
                    txtCotizacion.setEditable(false);
                    cotizacion = cotizacionOficial;
                    calculaImporte();
                }
                txtCotizacion.setText(formato.format(cotizacion.getValor()));
                //txtImporte.setText("0");
            }

        }
        );

        /*txtAPagar.addKeyListener(new KeyListener() {

         @Override
         public void keyTyped(KeyEvent e) {
         importeAPagar = new BigDecimal(txtAPagar.getText());
         }

         @Override
         public void keyPressed(KeyEvent e) {
         importeAPagar = new BigDecimal(txtAPagar.getText());
         }

         @Override
         public void keyReleased(KeyEvent e) {
         importeAPagar = new BigDecimal(txtAPagar.getText());
         }
         }
         );*/
        txtCotizacion.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (!txtCotizacion.getText().equals("")) {
                    cotizacion = new Cotizacion(new BigDecimal(txtCotizacion.getText()));
                    calculaImporte();
                }
            }
        });

        txtCotizacion.addKeyListener(
                new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                //NADA        
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!txtCotizacion.getText().equals("")) {
                        cotizacion = new Cotizacion(new BigDecimal(txtCotizacion.getText()));
                        calculaImporte();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //NADA
            }
        }
        );

        txtMora.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (!txtMora.getText().equals("")) {
                    //mora = new BigDecimal(txtMora.getText());
                    calculaImporte();
                } else {
                    txtMora.setText("0.00");
                    //mora = new BigDecimal(txtMora.getText());
                    calculaImporte();
                }
            }
        });

        txtMora.addKeyListener(
                new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                //NADA        
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!txtMora.getText().equals("")) {
                        //mora = new BigDecimal(txtMora.getText());
                        calculaImporte();
                    } else {
                        txtMora.setText("0.00");
                        //mora = new BigDecimal(txtMora.getText());
                        calculaImporte();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //NADA
            }
        }
        );

        txtAPagar.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (!txtAPagar.getText().equals("")) {

                    importeAPagar = new BigDecimal(txtAPagar.getText());
                    if (importeAPagar.doubleValue() > reciboSeleccionado.getSaldo().doubleValue()) {
                        txtAPagar.setText(reciboSeleccionado.getSaldo().toString());
                    }
                    calculaImporte();
                } else {
                    txtAPagar.setText("1.00");

                    //importeAPagar = new BigDecimal(txtAPagar.getText());
                    calculaImporte();
                }
            }
        });

        txtAPagar.addKeyListener(
                new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
               
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!txtAPagar.getText().equals("")) {

                        importeAPagar = new BigDecimal(txtAPagar.getText());
                        if (importeAPagar.doubleValue() > reciboSeleccionado.getSaldo().doubleValue()) {
                            txtAPagar.setText(reciboSeleccionado.getSaldo().toString());
                        }
                        calculaImporte();
                    } else {
                        txtAPagar.setText("1.00");
                        //importeAPagar = new BigDecimal(txtAPagar.getText());
                        calculaImporte();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //NADA
            }
        }
        );

    }

    void calculaImporte() {

        if (chpagoParcial.isSelected()) {
            importeAPagar = new BigDecimal(txtAPagar.getText());
            BigDecimal saldoReal = (reciboSeleccionado.getSaldo().add(reciboSeleccionado.getMora())).setScale(2, RoundingMode.CEILING);
            BigDecimal entregaCuota = (reciboSeleccionado.getSaldo().multiply(importeAPagar)).divide(saldoReal, BigDecimal.ROUND_UP).setScale(2, RoundingMode.CEILING);
            BigDecimal entregaMora = (reciboSeleccionado.getMora().multiply(importeAPagar)).divide(saldoReal, BigDecimal.ROUND_UP).setScale(2, RoundingMode.CEILING);

            txtCuota.setText(entregaCuota.toString());
            txtMora.setText(String.valueOf(entregaMora.doubleValue()));
            mora = new BigDecimal(txtMora.getText());
        } else {

            txtCuota.setText(reciboSeleccionado.getSaldo().toString());

            if (new Double(txtMora.getText()) != reciboSeleccionado.getMora().doubleValue()) {
                mora = new BigDecimal(txtMora.getText());
            } else {
                mora = reciboSeleccionado.getMora();
            }
            txtMora.setText(mora.toString());

            importeAPagar = reciboSeleccionado.getSaldo().add(mora);
            txtAPagar.setText(importeAPagar.toString());

        }

        if (!txtCotizacion.getText().equals("")) {
            if (rbPesos.isSelected()) {
                //importeAPagar = new BigDecimal(txtAPagar.getText());
                if (moneda == Moneda.PESOS) {
                    txtImporte.setText(importeAPagar.toString());
                    cbTipoCotizacion.setSelectedItem(TipoCotizacionContrato.OFICIAL);
                    txtCotizacion.setText("1.00");
                    txtCotizacion.setEnabled(false);
                } else {

                    txtImporte.setText(importeAPagar.multiply(cotizacion.getValor()).setScale(0, RoundingMode.CEILING).toString());
                    txtCotizacion.setEnabled(true);
                    txtCotizacion.setText(formato.format(cotizacion.getValor()));
                }
            } else if (rbDolares.isSelected()) {
                if (moneda == Moneda.PESOS) {

                    txtImporte.setText(importeAPagar.divide(cotizacion.getValor()).setScale(0, RoundingMode.CEILING).toString());
                    txtCotizacion.setText(formato.format(cotizacion.getValor()));

                } else if (moneda == Moneda.DOLARES) {
                    cbTipoCotizacion.setSelectedItem(TipoCotizacionContrato.OFICIAL);
                    txtCotizacion.setText("1.00");
                    txtCotizacion.setEnabled(false);
                    txtImporte.setText(importeAPagar.setScale(2, RoundingMode.CEILING).toString());
                }
            }
        }
    }

    void movimientoDeCaja(PagarRecibo pagarRecibo) {
        Caja movimiento = new Caja();
        BigDecimal importe = new BigDecimal(txtImporte.getText());
        movimiento.setDescripcion("Pago alquiler/cuotas, contrato: " + contratoSeleccionado.getId() + ", Inmueble: " + contratoSeleccionado.getInmueble());
        if (contratoSeleccionado.getTipoContrato() == TipoContrato.VENTA) {
            movimiento.setDescripcion("Pago cuota Nro: " + reciboSeleccionado.getNroRecibo() + ", Contrato: " + contratoSeleccionado.getId() + ", Inmueble: " + contratoSeleccionado.getInmueble());
        } else if (contratoSeleccionado.getTipoContrato() == TipoContrato.ALQUILER) {
            movimiento.setDescripcion("Pago alquiler período: " + reciboSeleccionado.getPeriodo_desde() + "-" + reciboSeleccionado.getPeriodo_hasta() + ", Contrato: " + contratoSeleccionado.getId() + ", Inmueble: " + contratoSeleccionado.getInmueble());
        }
        movimiento.setEntrada(importe);
        movimiento.setFecha(new Date());

        if (rbDolares.isSelected()) {
            movimiento.setMoneda(Moneda.DOLARES);
        } else if (rbPesos.isSelected()) {
            movimiento.setMoneda(Moneda.PESOS);
        }
        movimiento.setRubro(parametros.getRubroAlquileres());
        movimiento.setSaldo(calculaSaldo().add(importe));
        movimiento.setSalida(BigDecimal.ZERO);
        movimiento.setTipoDeCaja((TipoDeCaja) cbTipoDeCaja.getSelectedItem());

        PagoRecibo pagoRecibo = pagarRecibo.pagarRecibo();
        movimiento.setPagoRecibo(pagoRecibo);

        cajaDAO.save(movimiento);
    }

    BigDecimal calculaSaldo() {
        BigDecimal toReturn;
        Caja ultimoMovimiento = null;
        if (rbDolares.isSelected()) {
            ultimoMovimiento = cajaDAO.findUltimoMovimiento(Moneda.DOLARES, (TipoDeCaja) cbTipoDeCaja.getSelectedItem());
        } else if (rbPesos.isSelected()) {
            ultimoMovimiento = cajaDAO.findUltimoMovimiento(Moneda.PESOS, (TipoDeCaja) cbTipoDeCaja.getSelectedItem());
        }
        if (ultimoMovimiento == null) {
            toReturn = BigDecimal.ZERO;
        } else {
            toReturn = ultimoMovimiento.getSaldo();
        }
        return toReturn;

    }

    void VerificaImporte() {
        try {
            calculaImporte();
            PagarRecibo pagar;

            reciboSeleccionado.setMora(mora);
            valorEntrega = new BigDecimal(txtAPagar.getText()).setScale(2, RoundingMode.CEILING);

            if (rbDolares.isSelected()) {
                pagar = new PagarRecibo(reciboSeleccionado, valorEntrega.subtract(mora), mora, Moneda.DOLARES, 
                        (TipoCotizacionContrato) cbTipoCotizacion.getSelectedItem(), new BigDecimal(txtCotizacion.getText()));
                this.dispose();
                movimientoDeCaja(pagar);

            } else if (rbPesos.isSelected()) {

                pagar = new PagarRecibo(reciboSeleccionado, valorEntrega.subtract(mora), mora, Moneda.PESOS, 
                        (TipoCotizacionContrato) cbTipoCotizacion.getSelectedItem(), new BigDecimal(txtCotizacion.getText()));
                this.dispose();
                movimientoDeCaja(pagar);

            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar registros: " + e);

        }
    }

    private void cargaTiposDeCaja() {
        cbTipoDeCaja.removeAllItems();
        for (TipoDeCaja tipoDeCaja : tipoDeCajaDAO.findAll()) {
            cbTipoDeCaja.addItem(tipoDeCaja);
        }
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblDetalleRecibo = new javax.swing.JLabel();
        cbTipoCotizacion = new javax.swing.JComboBox();
        txtCotizacion = new javax.swing.JTextField();
        lblImporteDolares = new javax.swing.JLabel();
        lblTipoCotizacion = new javax.swing.JLabel();
        txtImporte = new javax.swing.JTextField();
        cbTipoDeCaja = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        rbDolares = new javax.swing.JRadioButton();
        rbPesos = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        txtCuota = new javax.swing.JTextField();
        lblMoneda2 = new javax.swing.JLabel();
        lbl = new javax.swing.JLabel();
        lbl1 = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        lblMoneda = new javax.swing.JLabel();
        lblMoneda1 = new javax.swing.JLabel();
        txtMora = new javax.swing.JTextField();
        txtAPagar = new javax.swing.JTextField();
        chpagoParcial = new javax.swing.JCheckBox();
        lblMsj = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnVolver = new botones.BotonVolver();
        btnPagar = new botones.BotonPagar();
        lblPagaEnBanco = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalles Pago");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblDetalleRecibo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblDetalleRecibo.setForeground(new java.awt.Color(0, 51, 204));
        lblDetalleRecibo.setText("Detalle recibo");
        lblDetalleRecibo.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(lblDetalleRecibo, gridBagConstraints);

        cbTipoCotizacion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoCotizacion, gridBagConstraints);

        txtCotizacion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtCotizacion, gridBagConstraints);

        lblImporteDolares.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblImporteDolares.setText("Importe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel1.add(lblImporteDolares, gridBagConstraints);

        lblTipoCotizacion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTipoCotizacion.setText("Cotización?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(lblTipoCotizacion, gridBagConstraints);

        txtImporte.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtImporte.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtImporte, gridBagConstraints);

        cbTipoDeCaja.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        cbTipoDeCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoDeCajaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoDeCaja, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Moneda a pagar", 0, 0, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(180, 86));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(rbDolares);
        rbDolares.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        rbDolares.setText("Dólares");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel3.add(rbDolares, gridBagConstraints);

        buttonGroup1.add(rbPesos);
        rbPesos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        rbPesos.setSelected(true);
        rbPesos.setText("Pesos");
        rbPesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPesosActionPerformed(evt);
            }
        });
        jPanel3.add(rbPesos, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 3;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        txtCuota.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCuota.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtCuota, gridBagConstraints);

        lblMoneda2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMoneda2.setForeground(new java.awt.Color(255, 51, 51));
        lblMoneda2.setText("moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel4.add(lblMoneda2, gridBagConstraints);

        lbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl.setText("Valor Cuota");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel4.add(lbl, gridBagConstraints);

        lbl1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl1.setText("Mora");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel4.add(lbl1, gridBagConstraints);

        lbl2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl2.setText("Total a pagar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel4.add(lbl2, gridBagConstraints);

        lblMoneda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMoneda.setForeground(new java.awt.Color(255, 51, 51));
        lblMoneda.setText("moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel4.add(lblMoneda, gridBagConstraints);

        lblMoneda1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMoneda1.setForeground(new java.awt.Color(255, 51, 51));
        lblMoneda1.setText("moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel4.add(lblMoneda1, gridBagConstraints);

        txtMora.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtMora, gridBagConstraints);

        txtAPagar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtAPagar.setToolTipText("Presione ENTER luego de modificar");
        txtAPagar.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtAPagar, gridBagConstraints);

        chpagoParcial.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chpagoParcial.setText("Pago parcial");
        chpagoParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chpagoParcialActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        jPanel4.add(chpagoParcial, gridBagConstraints);

        lblMsj.setText("Presione ENTER luego de modificar!");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel4.add(lblMsj, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        jPanel1.add(jPanel4, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Caja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel11, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new java.awt.GridBagLayout());

        btnVolver.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel5.add(btnVolver, gridBagConstraints);

        btnPagar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel5.add(btnPagar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jPanel5, gridBagConstraints);

        lblPagaEnBanco.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPagaEnBanco.setForeground(new java.awt.Color(255, 51, 51));
        lblPagaEnBanco.setText("Paga en banco");
        lblPagaEnBanco.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(lblPagaEnBanco, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbPesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPesosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbPesosActionPerformed

    private void chpagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chpagoParcialActionPerformed

        if (chpagoParcial.isSelected()) {
            lblMsj.setVisible(true);
            txtAPagar.setEnabled(true);
            txtMora.setEnabled(false);
            txtCuota.setText(reciboSeleccionado.getSaldo().toString());
            txtMora.setText(reciboSeleccionado.getMora().toString());
            txtAPagar.setText(reciboSeleccionado.getSaldo().add(reciboSeleccionado.getMora()).toString());
        } else {
            lblMsj.setVisible(false);
            txtAPagar.setEnabled(false);
            txtMora.setEnabled(true);
        }
        calculaImporte();

    }//GEN-LAST:event_chpagoParcialActionPerformed

    private void cbTipoDeCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoDeCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoDeCajaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonPagar btnPagar;
    private botones.BotonVolver btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JComboBox cbTipoCotizacion;
    public javax.swing.JComboBox cbTipoDeCaja;
    private javax.swing.JCheckBox chpagoParcial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbl;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lblDetalleRecibo;
    private javax.swing.JLabel lblImporteDolares;
    private javax.swing.JLabel lblMoneda;
    private javax.swing.JLabel lblMoneda1;
    private javax.swing.JLabel lblMoneda2;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JLabel lblPagaEnBanco;
    private javax.swing.JLabel lblTipoCotizacion;
    private javax.swing.JRadioButton rbDolares;
    private javax.swing.JRadioButton rbPesos;
    private javax.swing.JTextField txtAPagar;
    private javax.swing.JTextField txtCotizacion;
    private javax.swing.JTextField txtCuota;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtMora;
    // End of variables declaration//GEN-END:variables
}
