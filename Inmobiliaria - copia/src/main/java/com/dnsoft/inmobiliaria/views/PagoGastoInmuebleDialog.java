package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.GastoInmueblePropietario;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.PagoGastoInmueble;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.beans.TipoPago;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmuebleInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmueblePropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPagoGastoInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDeCajaDAO;
import com.dnsoft.inmobiliaria.utils.ActualizaSaldos;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Diego Noble
 */
public final class PagoGastoInmuebleDialog extends javax.swing.JDialog {

    Container container;
    IGastoInmueblePropietarioDAO gastoInmueblePropietarioDAO;
    IGastoInmuebleInquilinoDAO gastoInmuebleInquilinoDAO;
    ICajaDAO cajaDAO;
    ITipoDeCajaDAO tipoDeCajaDAO;
    ICCPropietarioDAO cCPropietarioDAO;

    GastoInmuebleInquilino gastoInmuebleInquilino;
    GastoInmueblePropietario gastoInmueblePropietario;
    IPagoGastoInmuebleDAO pagoGastoInmuebleDAO;
    BigDecimal valorEntrega;
    BigDecimal saldo;
    Moneda moneda;

    public PagoGastoInmuebleDialog(java.awt.Frame parent, boolean modal, GastoInmuebleInquilino gastoInmuebleInquilino) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        this.gastoInmuebleInquilino = gastoInmuebleInquilino;
        inicio();
        moneda = gastoInmuebleInquilino.getMoneda();
        saldo = gastoInmuebleInquilino.getSaldo();
        txtImporte.setText(saldo.toString());
    }

    public PagoGastoInmuebleDialog(java.awt.Frame parent, boolean modal, GastoInmueblePropietario gastoInmueblePropietario ) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        this.gastoInmueblePropietario = gastoInmueblePropietario;
        inicio();
        moneda = gastoInmueblePropietario.getMoneda();
        saldo = gastoInmueblePropietario.getSaldo();
        txtImporte.setText(saldo.toString());
    }

    void inicio() {

        cajaDAO = container.getBean(ICajaDAO.class);
        gastoInmueblePropietarioDAO = container.getBean(IGastoInmueblePropietarioDAO.class);
        gastoInmuebleInquilinoDAO = container.getBean(IGastoInmuebleInquilinoDAO.class);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        pagoGastoInmuebleDAO = container.getBean(IPagoGastoInmuebleDAO.class);
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);

        setLocationRelativeTo(null);
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
        txtImporte.setDocument(new ControlarEntradaTexto(10, chs));
        accionesBotones();
        cargaTiposDeCaja();

    }

    void accionesBotones() {
        btnPagar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (gastoInmueblePropietario == null) {
                    pagaGastoInmuebleInquilino();
                    dispose();
                } else {
                    pagaGastoInmueblePropietario();
                    dispose();
                }
            }
        });
        btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                dispose();
            }
        });

    }

    void pagaGastoInmuebleInquilino() {

        try {
            PagoGastoInmueble pagoGastoInmueble = new PagoGastoInmueble();
            valorEntrega = new BigDecimal(txtImporte.getText()).setScale(2, RoundingMode.CEILING);

            if (valorEntrega.compareTo(saldo) == 0) {

                gastoInmuebleInquilino.setSituacion(Situacion.PAGO);
                gastoInmuebleInquilino.setSaldo(BigDecimal.ZERO);

                pagoGastoInmueble.setFecha(new Date());
                pagoGastoInmueble.setGastoInmuebleInquilino(gastoInmuebleInquilino);
                pagoGastoInmueble.setMoneda(moneda);
                pagoGastoInmueble.setTipoPago(TipoPago.TOTAL);
                pagoGastoInmueble.setValor(valorEntrega);

            } else {
                gastoInmuebleInquilino.setSituacion(Situacion.CON_SALDO);
                gastoInmuebleInquilino.setSaldo(gastoInmuebleInquilino.getSaldo().subtract(valorEntrega));

                pagoGastoInmueble.setFecha(new Date());
                pagoGastoInmueble.setGastoInmuebleInquilino(gastoInmuebleInquilino);
                pagoGastoInmueble.setMoneda(moneda);
                pagoGastoInmueble.setTipoPago(TipoPago.PARCIAL);
                pagoGastoInmueble.setValor(valorEntrega);

            }

            Caja movimientoCaja = new Caja();
            movimientoCaja.setDescripcion("Pago gasto inmueble " + gastoInmuebleInquilino.getInmueble() + ", Inquilino " + gastoInmuebleInquilino.getInquilino());
            movimientoCaja.setEntrada(valorEntrega);
            movimientoCaja.setFecha(new Date());
            movimientoCaja.setMoneda(moneda);
            movimientoCaja.setRubro(gastoInmuebleInquilino.getRubro());
            movimientoCaja.setSaldo(calculaSaldo().add(valorEntrega));
            movimientoCaja.setSalida(BigDecimal.ZERO);

            gastoInmuebleInquilinoDAO.save(gastoInmuebleInquilino);
            pagoGastoInmuebleDAO.save(pagoGastoInmueble);
            cajaDAO.save(movimientoCaja);
            JOptionPane.showMessageDialog(this, "Se registro el pago correctamente!", "Correcto", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar registros: " + e);

        }
    }

    void pagaGastoInmueblePropietario() {

        try {
            PagoGastoInmueble pagoGastoInmueble = new PagoGastoInmueble();
            valorEntrega = new BigDecimal(txtImporte.getText()).setScale(2, RoundingMode.CEILING);
            if (valorEntrega.compareTo(saldo) == 0) {

                gastoInmueblePropietario.setSituacion(Situacion.PAGO);
                gastoInmueblePropietario.setSaldo(BigDecimal.ZERO);

                pagoGastoInmueble.setFecha(new Date());
                pagoGastoInmueble.setGastoInmueblePropietario(gastoInmueblePropietario);
                pagoGastoInmueble.setMoneda(moneda);
                pagoGastoInmueble.setTipoPago(TipoPago.TOTAL);
                pagoGastoInmueble.setValor(valorEntrega);

            } else {
                gastoInmueblePropietario.setSituacion(Situacion.CON_SALDO);
                gastoInmueblePropietario.setSaldo(gastoInmueblePropietario.getSaldo().subtract(valorEntrega));

                pagoGastoInmueble.setFecha(new Date());
                pagoGastoInmueble.setGastoInmueblePropietario(gastoInmueblePropietario);
                pagoGastoInmueble.setMoneda(moneda);
                pagoGastoInmueble.setTipoPago(TipoPago.PARCIAL);
                pagoGastoInmueble.setValor(valorEntrega);

            }

            Caja movimientoCaja = new Caja();
            movimientoCaja.setDescripcion("Pago gasto inmueble " + gastoInmueblePropietario.getInmueble() + ", Propietario " + gastoInmueblePropietario.getPropietario());
            movimientoCaja.setEntrada(valorEntrega);
            movimientoCaja.setFecha(new Date());
            movimientoCaja.setMoneda(moneda);
            movimientoCaja.setRubro(gastoInmueblePropietario.getRubro());
            movimientoCaja.setSaldo(calculaSaldo().add(valorEntrega));
            movimientoCaja.setSalida(BigDecimal.ZERO);

            gastoInmueblePropietarioDAO.save(gastoInmueblePropietario);
            pagoGastoInmuebleDAO.save(pagoGastoInmueble);
            movimientoCCPropietario();
            cajaDAO.save(movimientoCaja);
            JOptionPane.showMessageDialog(this, "Se registro el pago correctamente!", "Correcto", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar registros: " + e);
        }

    }

    void movimientoCCPropietario() {
        CCPropietario movimientoCCPropietario = new CCPropietario();
        movimientoCCPropietario.setPropietario(gastoInmueblePropietario.getPropietario());
        movimientoCCPropietario.setDebito(BigDecimal.ZERO);
        movimientoCCPropietario.setCredito(valorEntrega);
        movimientoCCPropietario.setDescipcion("Pago Gasto Inmueble " + gastoInmueblePropietario.getInmueble());
        movimientoCCPropietario.setFecha(new Date());
        movimientoCCPropietario.setMoneda(moneda);
        movimientoCCPropietario.setGastoInmueblePropietario(gastoInmueblePropietario);

        cCPropietarioDAO.save(movimientoCCPropietario);
        ActualizaSaldos acSaldo = new ActualizaSaldos();

        List<CCPropietario> ccPropietario = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(gastoInmueblePropietario.getPropietario(), moneda);
        cCPropietarioDAO.save(acSaldo.ActualizaSaldosPropietarios(ccPropietario));
    }

    BigDecimal calculaSaldo() {
        BigDecimal toReturn;

        Caja ultimoMovimiento = cajaDAO.findUltimoMovimiento(moneda, (TipoDeCaja) cbTipoDeCaja.getSelectedItem());
        if (ultimoMovimiento == null) {
            toReturn = BigDecimal.ZERO;
        } else {
            toReturn = ultimoMovimiento.getSaldo();
        }
        return toReturn;

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

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtImporte = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnPagar = new botones.BotonPagar();
        btnVolver = new botones.BotonVolver();
        cbTipoDeCaja = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        txtImporte.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtImporte, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Import Total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel11, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(btnPagar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(btnVolver, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoDeCaja, gridBagConstraints);

        jLabel10.setText("Tipo de caja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonPagar btnPagar;
    private botones.BotonVolver btnVolver;
    public javax.swing.JComboBox cbTipoDeCaja;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtImporte;
    // End of variables declaration//GEN-END:variables
}
