package cat.mvm.modular;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Class that defines the methods used in ProductRecorder to record, get statistics and validate inputs
 * @see Utilities#jbRecordProduct
 * @see Utilities#jbStatisticsPerformed()
 * @see Utilities#priceValidation()
 * @see Utilities#amountValidation()
 * @see Utilities#codeValidation()
 * @see Utilities#nameValidation()
 * @see Utilities#typeValidation()
 * @link #jbRecordProduct()
 * @link #jbStatisticsPerformed()
 * @version 1.0 20 May 2021
 * @author Pau Guarch
 */

public final class Utilities {

    /**
     *
     * Method that records the inputs from the user in a database row after validating the input values
     * @see Utilities#jbRecordProduct()
     *{@link #jbRecordProduct() throws SQLException}
     *
     */
    public static void jbRecordProduct() throws SQLException {

        /**
         * @see Utilities#jbRecordProduct()
         * @param name String that contains the input name of the product
         * @param code String that contains the input code of the product
         * @param type String that contains the input type of the product
         * @param price String that contains the input price of the product
         * @param amount String that contains the input amount of the product
         * @param server String that contains the route to the database server
         * @param dnName String that contains the name of the database
         * @param user String that contains the user to acces the database
         * @param password String that contains the password for the database user
         *
         */

        String server = "jdbc:mysql://localhost:3306/";
        String dbName = "sephora_db";
        String user ="userDAW";
        String password = "12345678Daw";
        String name = ProductRecorder.jtfName.getText();
        String code = ProductRecorder.jtfCode.getText();
        String type = ProductRecorder.jtfType.getText();
        String price = ProductRecorder.jtfPrice.getText();
        price = price.replace(',', '.'); //canviem les possibles comas del input per punts
        String amount = ProductRecorder.jtfAmount.getText();
        String family_name = "";
        String txtError = "";
        String sqlInsert = "INSERT INTO products VALUES (?,?,?,?,?,?)";
        int family_id = 0;
        boolean familySelected=false; //variable que utilitzarem per determinar si un radiobutton s'ha seleccionat
        PreparedStatement pstatement = null;
        Connection connection = null;

        ProductRecorder.jtaOut.setText(null);

        //<editor-fold desc="Assignacio de valors per la familia segons el radiobutton seleccionat">


        if (ProductRecorder.jrbMakeup.isSelected()){
            family_name = "Maquillatge";
            family_id = 1; //emmagatzemem la variable que asignarem posteriorment a la query
            familySelected=true; //en cas de que el radiobutton sigui seleccionat canviem el valor que valida la selecció
            ProductRecorder.jlFamilyError.setText(""); //esborrem el possible missatge d'error de validacions anteriors
        }else if(ProductRecorder.jrbPerfumery.isSelected()){
            family_name = "Perfumeria";
            family_id = 2;
            familySelected=true;
            ProductRecorder.jlFamilyError.setText("");
        }else if (ProductRecorder.jrbCosmetics.isSelected()){
            family_name = "Cosmetica";
            family_id = 3;
            familySelected=true;
            ProductRecorder.jlFamilyError.setText("");
        }else {
            txtError+="Siusplau, selecciona una familia.";
            ProductRecorder.jlFamilyError.setText(txtError);
        }
        //</editor-fold>
        //condició que es complira cridant a les funcions de validació que retornen booleans i comprovant familySelected
        if (Utilities.nameValidation() && Utilities.codeValidation() && Utilities.typeValidation() && Utilities.priceValidation() &&
                Utilities.amountValidation() && familySelected) {
            //instanciem l'objecte product després d'haver validat les dades
            var Product1 = new Product(code, name, family_id, type, Float.parseFloat(price), Integer.parseInt(amount));
            ProductRecorder.jtaOut.setForeground(Color.BLUE); //canviem a blau el color de les lletres pel missatge a l'usuari

            try {
                connection = DriverManager.getConnection(server + dbName, user, password);
            }catch(SQLException throwables){
                ProductRecorder.jtaOut.setForeground(Color.RED);
                ProductRecorder.jtaOut.setText("Ho sentim, hi ha hagut un error de connexió \namb la base de dades.");
            }
            try{
                pstatement = connection.prepareStatement(sqlInsert); //passem el string del insert a la sentencia
                //assignem els valors de l'objecte instanciat als ? de l'insert de la sentencia preparada
                pstatement.setInt(1, Integer.parseInt(Product1.getCode()));
                pstatement.setString(2, Product1.getName());
                pstatement.setInt(3, Product1.getFamily());
                pstatement.setString(4, Product1.getType());
                pstatement.setFloat(5, Product1.getPrice());
                pstatement.setInt(6, Product1.getAmount());
                pstatement.executeUpdate();
                String textConfirmation = String.format("PRODUCTE INTRODUIT CORRECTAMENT. \n\nNom: %s \nCodi : %s \n" +
                        "Familia : %s\nTipus: %s\nPreu: %s \nQuantitat: %s", name, code, family_name, type, price, amount);
                ProductRecorder.jtaOut.setText(textConfirmation);
                ProductRecorder.jtfName.setText("");
                ProductRecorder.jtfCode.setText("");
                ProductRecorder.jtfType.setText("");
                ProductRecorder.jtfPrice.setText("");
                ProductRecorder.jtfAmount.setText("");
            }catch (SQLException throwables) {
                throwables.printStackTrace();
                ProductRecorder.jtaOut.setForeground(Color.RED);
                ProductRecorder.jtaOut.setText("AQUEST CODI DE PRODUCTE JA EXISTEIX");

            }finally{
                if (pstatement != null)
                    try {
                        pstatement.close();
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                    }
            }
        }else{
            ProductRecorder.jtaOut.setText("REVISA LES DADES INTRODUIDES\nSIUSPLAU");
        }
    }

    /**
     *Method that gets values from different sql querys to display statistics to the user and export a statistics file
     *
     * @see Utilities#jbStatisticsPerformed()
     * {@link #jbStatisticsPerformed() throws SQLException, IOException}
     *
     */
    static void jbStatisticsPerformed() throws SQLException, IOException {

        /**
         * @see Utilities#jbStatisticsPerformed()
         * @param fileName value that will be incremented to reference different families
         * @param fileName String that contains the name of the exported file
         * @param server String that contains the route to the database server
         * @param dnName String that contains the name of the database
         * @param user String that contains the user to acces the database
         * @param password String that contains the password for the database user
         * @param productName String used to save the name of the product printed in the statistics
         * @param productCode Strung used to save the code of the product printed in the statistics
         */
        int familyNum=1; //variable que utilitzem per assignar a la sentencia preparada quan fem un select segons familia
        //aqui seguirem els passos per convertit un localdatetime en String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm_yyyy-MM-dd"); //
        LocalDateTime dateTime = LocalDateTime.now();
        String currentDate = dateTime.format(formatter);
        //assignem l'string creat amb la data com a part del nom del fitxer que s'exporta amb les estadístiques
        String fileName = "statistics_"+currentDate+".txt";
        String server = "jdbc:mysql://localhost:3306/";
        String dbName = "sephora_db";
        String user ="userDAW";
        String password = "12345678Daw";
        String productName, productCode;;
        Writer output = new BufferedWriter(new FileWriter(fileName));
        ResultSet rs = null;
        PreparedStatement pstatement = null;
        //definicio de conexio i selects que executarem a traves d'aquesta conexio
        Connection connection = null;
        String sqlSelect1 = "Select product_id, name from products where stock =0;";
        String sqlSelect2= "select price from products where family=?;";
        //esborrem el contingut del Jtextarea i configurem la font de color negre
        ProductRecorder.jtaOut.setText(null);
        ProductRecorder.jtaOut.setForeground(Color.BLACK);
        try {
            connection = DriverManager.getConnection(server + dbName, user, password);
        }catch(SQLException throwables){
            ProductRecorder.jtaOut.setForeground(Color.RED);
            ProductRecorder.jtaOut.setText("Ho sentim, hi ha hagut un error de connexió \namb la base de dades.");
        }

        try { //a continuació assignem a la conexio els Strings que la defineixen
            connection = DriverManager.getConnection(server + dbName, user, password);
            try  {
                //li donem a la sentencia preparada el valor de l'string definit
                pstatement = connection.prepareStatement(sqlSelect2);
                //assignem al ? del select definit el valor de familyNum i executem la query
                pstatement.setString(1, String.valueOf(familyNum));
                rs = pstatement.executeQuery();
                //creem un array list per emmagatzemar els preus de la query
                List<Float> pricesArray = new ArrayList<Float>();

                while(rs.next()) { //creem la condició per anar recorrent i emmagatzemant els valors de la query
                    pricesArray.add(rs.getFloat(1));
                }
                //creem un objecte statistics aplicant el metode stream al nostre arrayList
                DoubleSummaryStatistics stats = pricesArray.stream().mapToDouble((x) -> x).summaryStatistics();
                //mostrem a l'arxiu creat el valor maxim, mig, i minim de l'object els metodes getmin, max i avg
                output.append(String.format("                      ESTADISTIQUES\n\nCOSMETICA:   " +
                                "preu mínim- %05.2f   mig- %05.2f   màxim- %.2f",
                        stats.getMin(),stats.getAverage(), stats.getMax()));
                //fem el mateix al text area del JFrame
                ProductRecorder.jtaOut.setText(ProductRecorder.jtaOut.getText()+String.format("                  " +
                                "          ESTADISTIQUES\n\n" +
                                "COSMETICA:   \npreu mínim- %05.2f   mig- %05.2f   màxim- %.2f\n",
                        stats.getMin(),stats.getAverage(), stats.getMax()));
                System.out.println(String.format("                            ESTADISTIQUES\n\n" +
                                "COSMETICA:   \npreu mínim- %05.2f   mig- %05.2f   màxim- %.2f",
                        stats.getMin(),stats.getAverage(), stats.getMax()));
                familyNum++; //incrementem per la següent sentencia preparada i fer referencia a la familia 2




                //repetim el mateix query canviant el valor del nombre de familia al pstatement i l'executem de nou
                pricesArray.clear();
                pstatement.setString(1, String.valueOf(familyNum));
                rs = pstatement.executeQuery();
                while(rs.next()) { pricesArray.add(rs.getFloat(1)); }
                stats = pricesArray.stream().mapToDouble((x) -> x).summaryStatistics();

                output.append(String.format("\nPERFUMERIA:  preu mínim-%05.2f   mig-%05.2f   màxim-%.2f",
                        stats.getMin(),stats.getAverage(), stats.getMax()));
                ProductRecorder.jtaOut.setText(ProductRecorder.jtaOut.getText()+(String.format("\nPERFUMERIA:  \npreu mínim-%05.2f   " +
                        "mig-%05.2f   màxim-%.2f\n", stats.getMin(),stats.getAverage(), stats.getMax())));
                System.out.println(String.format("\nPERFUMERIA:  \npreu mínim-%05.2f   " +
                        "mig-%05.2f   màxim-%.2f", stats.getMin(),stats.getAverage(), stats.getMax()));
                familyNum++;

                //per últim tornem a fer el select de la última familia
                pricesArray.clear();
                pstatement.setString(1, String.valueOf(familyNum));
                rs = pstatement.executeQuery();
                while(rs.next()) { pricesArray.add(rs.getFloat(1)); }
                stats = pricesArray.stream().mapToDouble((x) -> x).summaryStatistics();
                output.append(String.format("\nMAQUILLATGE: preu mínim-%05.2f   mig-%05.2f   màxim-%.2f",
                        stats.getMin(),stats.getAverage(), stats.getMax()));
                ProductRecorder.jtaOut.setText(ProductRecorder.jtaOut.getText()+(String.format("\nMAQUILLATGE: \npreu mínim-%05.2f   " +
                        "mig-%05.2f   màxim-%.2f\n", stats.getMin(),stats.getAverage(), stats.getMax())));
                System.out.println(String.format("\nMAQUILLATGE: \npreu mínim-%05.2f   " +
                        "mig-%05.2f   màxim-%.2f", stats.getMin(),stats.getAverage(), stats.getMax()));

                //assignem el segon String del select per obtenir el llistat de productes sense stock
                pstatement = connection.prepareStatement(sqlSelect1);
                rs = pstatement.executeQuery(); //assignem al resultSet la nova query modificada
                output.append(String.format("\n\nPRODUCTES SENSE STOCK")); //escribim a l'arxiu .txt
                System.out.println(String.format("\nPRODUCTES SENSE STOCK"));
                ProductRecorder.jtaOut.setText(ProductRecorder.jtaOut.getText()+String.format("\nPRODUCTES SENSE STOCK\nCodi    Nom\n"));

                while(rs.next()){ //recorrem tots els registres retornats per la query
                    productCode = rs.getString(1); //de cada registre del select agafem codi i nom
                    productName = rs.getString(2);
                    //System.out.println(String.format("%s", productName));
                    ProductRecorder.jtaOut.setText(ProductRecorder.jtaOut.getText()+String.format(" %03d     %s\n",
                            Integer.parseInt(productCode), productName));
                    output.append(String.format("\nCodi: %03d    Nom: %s",Integer.parseInt(productCode), productName));
                    System.out.println(String.format("Codi: %03d    Nom: %s",
                            Integer.parseInt(productCode), productName));
                }

                output.close(); //tanquem l'stream per a la modificacio del fitxer
                ProductRecorder.jtaOut.setSelectionStart(0); //deixar scroll a dalt de tot despres de printar el text
                ProductRecorder.jtaOut.setSelectionEnd(0);

            }
            catch (IOException ex) {
                System.out.println("Error al executar les sentències preparades.");
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();

        }finally { //després dels diferents try per executar les accions, tanquem els Streams
            rs.close();
            pstatement.close();
            connection.close();
        }
    }

    /**
     *Validates the length of the user input from a textfield to set the name for the object Product
     *@return true if the validation is successful .
     */
    static boolean nameValidation(){ //validacio de la llargada del nom
        String nameStr = ProductRecorder.jtfName.getText();
        if(nameStr.length()>30 ){
            ProductRecorder.jtfName.setForeground(Color.RED);
            ProductRecorder.jlNameError.setText("Error. Maxim 30 caracters");
            return false;
        }else if(nameStr.length()==0) {
            ProductRecorder.jlNameError.setText("Error. Has d'introduir un nom");
            return false;
        }else{
            ProductRecorder.jtfName.setForeground(Color.BLACK);
            ProductRecorder.jlNameError.setText("");
            return true;
        }
    }

    /**
     *Validates the length of the user input from a textfield to set the type for the object Product
     *@return true if the validation is successful .
     */
    static boolean typeValidation(){ //validacio de la llargada de lstring tipus
        String typeStr = ProductRecorder.jtfType.getText();
        if(typeStr.length()>50){
            ProductRecorder.jlTypeError.setForeground(Color.RED);
            ProductRecorder.jlTypeError.setText("Error. Màxim 50 caracters");
            return false;
        }else if(typeStr.length()==0) {
            ProductRecorder.jlTypeError.setText("Error. Has d'introduir un tipus");
            return false;
        }else{
            ProductRecorder.jlTypeError.setForeground(Color.BLACK);
            ProductRecorder.jlTypeError.setText("");
            return true;
        }
    }

    /**
     *Validates the value of the user input from a textfield to set the code for the object Product
     *@return true if the validation is successful
     */
    static boolean codeValidation(){ //validacio del codi entre 0 i 999
        String code = ProductRecorder.jtfCode.getText();
        int codeInt;
        try{
            codeInt = Integer.parseInt(code);
            ProductRecorder.jtfCode.setForeground(Color.BLACK);
            ProductRecorder.jlCodeError.setText("");
            if (codeInt<0 || codeInt>999){
                ProductRecorder.jtfCode.setForeground(Color.RED);
                ProductRecorder.jlCodeError.setText("Error. Nombre entre 0 i 999 inclosos");
                return false;
            }else{
                return true;
            }
        }catch(NumberFormatException err){
            ProductRecorder.jlCodeError.setText("Error. Nombre entre 0 i 999 inclosos");
            ProductRecorder.jtfCode.setForeground(Color.RED);
            ProductRecorder.jtaOut.setForeground(Color.RED);
            return false;
        }
    }

    /**
     *Validates the value of the user input from a textfield to set the amount for the object Product
     *@return true if the validation is successful .
     *
     */
    static boolean amountValidation(){ //validació de la quantitat a registrar
        /*aqui he permés registrar productes amb quantitat 0 per si es vol registrar un producte per consultes futures
        encara que actualment no hi hagi stock del producte introduit*/
        var amount = ProductRecorder.jtfAmount.getText();
        int amountInt;
        try{  //convertim a integer el valor obtingut del textfield
            amountInt = Integer.parseInt(amount);
            ProductRecorder.jtfAmount.setForeground(Color.BLACK);
            ProductRecorder.jlAmountError.setText(""); //si es realitza correctament esborrem el label on hi ha el missatge d'error
            //creem condicions despres del try per definir un missatge d'error en cas de que el nombre sigui negatiu
            if (amountInt<0 ){
                ProductRecorder.jtfAmount.setForeground(Color.RED);
                ProductRecorder.jlAmountError.setText("Error. Introdueix un nombre positiu");
                return false;
            }else{
                return true;
            }
        }
        catch(NumberFormatException err){ //definim el label d'error i el color de les lletres del textfield
            ProductRecorder.jlAmountError.setText("Error. Introdueix un nombre");
            ProductRecorder.jtfAmount.setForeground(Color.RED);
            ProductRecorder.jtaOut.setForeground(Color.RED);
            return false;
        }
    }
    /**
     *Validates the value of the user input from a textfield to set the price for the object Product
     * @see Utilities#priceValidation()
     *@return true if the validation is successful .
     */
    static boolean priceValidation() {
        final String regExp = "[0-9]+[,.]?+([0-9]{1,2})?";

        String priceTxt = ProductRecorder.jtfPrice.getText();
        boolean isPriceFormat = priceTxt.matches(regExp);
        priceTxt = priceTxt.replace(',', '.');

        if (isPriceFormat && Float.parseFloat(priceTxt)<=99999999) {
            ProductRecorder.jtfPrice.setForeground(Color.BLACK);
            ProductRecorder.jlPriceError.setText("");
            return true;
        } else {
            ProductRecorder.jtfPrice.setForeground(Color.RED);
            ProductRecorder.jlPriceError.setText("Error. Preu no vàlid");
            return false;
        }

    }
}
