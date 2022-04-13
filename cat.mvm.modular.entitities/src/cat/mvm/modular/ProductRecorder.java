package cat.mvm.modular;
//Algunes llibreries queden seleccionades en la seva totalitat pel propi IntelliJ al seleccionar mes de x subllibreries.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;


/**
 * Class that contains all the elements and different< methods that will take part in the public methods
 * productRecorder and initcomponents.
 *
 * @version 1.0 20 May 2021
 * @author Pau Guarch
*/
public class ProductRecorder extends JFrame {
    /**Class containing all the elements that will be displayed in the JFrame extened class*/
    private JLabel jlTittle;
    private JLabel jlStatistics;
    private JLabel jlStatistics1;
    private JLabel jlStatistics2;
    private JLabel jlStatistics3;
    private JLabel jlStatistics4;
    private JLabel jlProductTittle;
    private JLabel jlCode;
    private JLabel jlName;
    private JLabel jlFamily;
    private JLabel jlType;
    private JLabel jlPrice;
    private JLabel jlAmount;
    protected static JLabel jlCodeError;
    protected static JLabel jlNameError;
    protected static JLabel jlFamilyError;
    protected static JLabel jlTypeError;
    protected static JLabel jlPriceError;
    protected static JLabel jlAmountError;
    protected static JTextField jtfCode;
    protected static JTextField jtfName;
    protected static JTextField jtfType;
    protected static JTextField jtfPrice;
    protected static JTextField jtfAmount;
    protected static JTextArea jtaOut;
    private JScrollPane scrollPane;
    private JButton jbRecord;
    private JButton jbStatistics;
    protected static JRadioButton jrbCosmetics;
    protected static JRadioButton jrbPerfumery;
    protected static JRadioButton jrbMakeup;


    /**
     * Method that defines all the components that will take part in the JFrame and calls to method innitcomponents
     */
    public ProductRecorder() { //definicio del objecte productRecorder que extén tipus JFrame
        /** Method that defines a Jframe and calls the method initComponents() in it.*/
        //missatge per informar a l'usuari de les accions que podrà realitzar.
        JOptionPane.showMessageDialog(null, "BENVINGUTS AL GESTOR DE STOCKS \nPrograma per " +
                "enregistrar productes nous a la base de dades\n o consultar i exportar un arxiu txt amb " +
                "les estadístiques dels productes");
        this.setSize(760, 670);
        this.setTitle("Gestor de Stocks Perfumerias MVM S.L.");
        this.setLocation(400, 100);
        initComponents(); //crida a la funcio on tenim definit el JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Color backgroundFrame = new Color(240, 223, 188);
        this.getContentPane().setBackground( backgroundFrame);
    }

    /**
     * Method that defines all the components that will take part in the JFrame.
     * It contains definitions for Labels, Textfields with listeners, JRadio Buttons ,buttons and text area.
     *
     */
    private void initComponents(){ //definicio del metode que defineix tots els elements del JFrame

        jlTittle = new JLabel();
        jlStatistics = new JLabel();
        jlStatistics1 = new JLabel();
        jlStatistics2 = new JLabel();
        jlStatistics3 = new JLabel();
        jlStatistics4 = new JLabel();
        jlProductTittle = new JLabel();
        jlCode = new JLabel();
        jlName = new JLabel();
        jlFamily = new JLabel();
        jlType = new JLabel();
        jlPrice = new JLabel();
        jlAmount = new JLabel();
        jlCodeError = new JLabel();
        jlNameError = new JLabel();
        jlFamilyError = new JLabel();
        jlTypeError = new JLabel();
        jlPriceError = new JLabel();
        jlAmountError = new JLabel();
        jtfCode = new JTextField();
        jtfName = new JTextField();
        jtfType = new JTextField();
        jtfPrice = new JTextField();
        jtfAmount = new JTextField();
        jtaOut = new JTextArea();
        scrollPane = new JScrollPane(jtaOut);
        jbRecord = new JButton();
        jbStatistics = new JButton();
        jrbCosmetics = new JRadioButton("Cosmètica");
        jrbPerfumery = new JRadioButton("Perfumeria");
        jrbMakeup = new JRadioButton("Maquillatge");
        ButtonGroup btngroup = new ButtonGroup();
        final Color errorColor = new Color(195, 0, 0);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm(e);
            }
        });
        getContentPane().setLayout(null);

        getContentPane().add(scrollPane); //afegim el textarea amb scroll al JFrame
        scrollPane.setBounds(320, 88, 380, 320);
        Font font = new Font("Helvetica", Font.BOLD, 14);
        jtaOut.setFont(font);

        //<editor-fold desc=" Definicions dels Labels">
        /** Label definitions for all the labels appearing in the Jframe*/
        jlTittle.setText("GESTOR DE PRODUCTES/STOCKS");
        getContentPane().add(jlTittle);
        jlTittle.setBounds(140, 25, 700, 22);
        jlTittle.setFont(new Font("Helvetica CY", Font.PLAIN, 26));

        jlProductTittle.setText("REGISTRAR PRODUCTE");
        getContentPane().add(jlProductTittle);
        jlProductTittle.setBounds(84, 85, 400, 22);
        jlProductTittle.setFont(new Font("Helvetica CY", Font.BOLD, 17));

        jlStatistics.setText("ESTADISTIQUES");
        getContentPane().add(jlStatistics);
        jlStatistics.setBounds(430, 420, 400, 22);
        jlStatistics.setFont(new Font("Helvetica CY", Font.BOLD, 17));

        jlStatistics1.setText("-Veure productes sense stocks");
        getContentPane().add(jlStatistics1);
        jlStatistics1.setBounds(400, 450, 400, 22);
        jlStatistics1.setFont(new Font("Helvetica CY", Font.ITALIC, 14));

        jlStatistics2.setText("-Veure preu mínim de cada familia");
        getContentPane().add(jlStatistics2);
        jlStatistics2.setBounds(400, 490, 400, 24);
        jlStatistics2.setFont(new Font("Helvetica CY", Font.ITALIC, 14));

        jlStatistics3.setText("-Veure preu mig de cada familia");
        getContentPane().add(jlStatistics3);
        jlStatistics3.setBounds(400, 510, 400, 24);
        jlStatistics3.setFont(new Font("Helvetica CY", Font.ITALIC, 14));

        jlStatistics4.setText("-Veure preu màxim de cada familia");
        getContentPane().add(jlStatistics4);
        jlStatistics4.setBounds(400, 470, 400, 24);
        jlStatistics4.setFont(new Font("Helvetica CY", Font.ITALIC, 14));

        jlName.setText("Nom");
        getContentPane().add(jlName);
        jlName.setBounds(64, 132, 116, 22);

        jlCode.setText("Codi");
        getContentPane().add(jlCode);
        jlCode.setBounds(64, 187, 116, 22);

        jlFamily.setText("Familia");
        getContentPane().add(jlFamily);
        jlFamily.setBounds(55, 242, 136, 22);

        jlType.setText("Tipus");
        getContentPane().add(jlType);
        jlType.setBounds(64, 362, 116, 22);

        jlPrice.setText("Preu");
        getContentPane().add(jlPrice);
        jlPrice.setBounds(66, 427, 116, 22);

        jlAmount.setText("Quantitat");
        getContentPane().add(jlAmount);
        jlAmount.setBounds(55, 492, 176, 28);

        jlNameError.setText("");
        getContentPane().add(jlNameError);
        jlNameError.setBounds(64, 158, 236, 22);
        jlNameError.setFont(new Font("Helvetica CY", Font.ITALIC, 14));
        jlNameError.setForeground(errorColor);

        jlCodeError.setText("");
        getContentPane().add(jlCodeError);
        jlCodeError.setBounds(64, 213, 236, 22);
        jlCodeError.setFont(new Font("Helvetica CY", Font.ITALIC, 14));
        jlCodeError.setForeground(errorColor);

        jlFamilyError.setText("");
        getContentPane().add(jlFamilyError);
        jlFamilyError.setBounds(64, 327, 236, 28);
        jlFamilyError.setFont(new Font("Helvetica CY", Font.ITALIC, 14));
        jlFamilyError.setForeground(errorColor);

        jlTypeError.setText("");
        getContentPane().add(jlTypeError);
        jlTypeError.setBounds(64, 392, 236, 22);
        jlTypeError.setFont(new Font("Helvetica CY", Font.ITALIC, 14));
        jlTypeError.setForeground(errorColor);

        jlPriceError.setText("");
        getContentPane().add(jlPriceError);
        jlPriceError.setBounds(64, 457, 256, 22);
        jlPriceError.setFont(new Font("Helvetica CY", Font.ITALIC, 14));
        jlPriceError.setForeground(errorColor);

        jlAmountError.setText("");
        getContentPane().add(jlAmountError);
        jlAmountError.setBounds(64, 522, 236, 28);
        jlAmountError.setFont(new Font("Helvetica CY", Font.ITALIC, 14));
        jlAmountError.setForeground(errorColor);
        //</editor-fold>

        //<editor-fold desc="Definició dels TextFields">
        /** TextFields definitions for the user's inputs*/
        jtfName.setHorizontalAlignment(JTextField.LEFT);
        jtfName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfFocusGained(e);
            }
        });
        jtfName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Utilities.nameValidation();
            }
        });
        getContentPane().add(jtfName);
        jtfName.setBounds(155, 130, 125, 32);

        jtfCode.setHorizontalAlignment(JTextField.LEFT);
        jtfCode.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfFocusGained(e);
            }
        });
        jtfCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Utilities.codeValidation();
            }
        });
        getContentPane().add(jtfCode);
        jtfCode.setBounds(155, 185, 125, 32);

        jtfType.setHorizontalAlignment(JTextField.LEFT);
        jtfType.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfFocusGained(e);
            }
        });
        jtfType.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Utilities.typeValidation();
            }
        });
        getContentPane().add(jtfType);
        jtfType.setBounds(155, 360, 125, 32);

        jtfPrice.setHorizontalAlignment(JTextField.LEFT);
        jtfPrice.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfFocusGained(e);
            }
        });
        jtfPrice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Utilities.priceValidation();
            }
        });
        getContentPane().add(jtfPrice);
        jtfPrice.setBounds(155, 425, 125, 32);

        jtfAmount.setHorizontalAlignment(JTextField.LEFT);
        jtfAmount.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfFocusGained(e);
            }
        });
        jtfAmount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Utilities.amountValidation();
            }
        });
        getContentPane().add(jtfAmount);
        jtfAmount.setBounds(155, 490, 125, 32);
        //</editor-fold>

        //<editor-fold desc="Definició dels JRadioButtons i el buttonGroup">
        /** JRadioButton's definitions for family options for the user*/
        add(jrbCosmetics);
        jrbCosmetics.setBounds(155, 240, 100, 30);
        jrbCosmetics.setFont(new java.awt.Font("Lucida Grande", 0, 16));

        add(jrbPerfumery);
        jrbPerfumery.setBounds(155, 270, 140, 30);
        jrbPerfumery.setFont(new java.awt.Font("Lucida Grande", 0, 16));

        add(jrbMakeup);
        jrbMakeup.setBounds(155, 300, 120, 30);
        jrbMakeup.setFont(new java.awt.Font("Lucida Grande", 0, 16));

        btngroup.add(jrbCosmetics);
        btngroup.add(jrbPerfumery);
        btngroup.add(jrbMakeup);
        //</editor-fold>

        //<editor-fold desc="Definicio dels buttons">
        /** Button's definitions for registering a Product or getting the statistics from the database*/
        jbRecord.setText("Registrar");
        jbRecord.setMnemonic('R');  //tecla de teclat que al pulsar executara el boto
        getRootPane().setDefaultButton(jbRecord);
        jbRecord.addActionListener(e -> {
            try {
                Utilities.jbRecordProduct();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        getContentPane().add(jbRecord);
        jbRecord.setBounds(160, 560, 120, 34);

        jbStatistics.setText("Estadístiques");
        jbStatistics.setMnemonic('O');  //tecla de teclat que al pulsar executara el boto
        getRootPane().setDefaultButton(jbStatistics);
        jbStatistics.addActionListener(e -> {
            try {
                Utilities.jbStatisticsPerformed();
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
        getContentPane().add(jbStatistics);
        jbStatistics.setBounds(540, 560, 135, 34);
        //</editor-fold>

    }


    private void exitForm(WindowEvent e){
        // per aturar el programa al tancar la finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void jtfFocusGained(FocusEvent evt){
        //conversio de tipus del getsource de evt per donarli a objenfocado
        JTextField objEnfocado = (JTextField) evt.getSource();
        objEnfocado.selectAll();
    }

}
