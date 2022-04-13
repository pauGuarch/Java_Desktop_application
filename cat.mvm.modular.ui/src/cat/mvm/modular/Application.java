package cat.mvm.modular;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Color;
import java.io.IOException;

/**
 * Class that sets the look and font properties and instantiates the JFrame
 *
 * @version 1.0 20 May 2021
 * @author Pau Guarch
 */

public class Application {
    /**
     *Method that sets a different look to the main JFrame executed.
     * It assigns the font properties and then instantiates the main JFrame object.
     *
     */
    public static void main(String[] args) throws IOException {
        /**
         * @param app variable where we save the new object of type ProductRecorder
         * @throws Exception e
         */

        try {  //try catch on carregarem el look&feel per la finestra de la nostre interficie
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error en la càrrega del paquet Nimbus de Look&feel");
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                System.out.println("Error en la càrrega del paquet Look and Feel");
            }
        }
        //asignacio de la font i color de fons a la User Interface
        setUIFont (new javax.swing.plaf.FontUIResource("Helvetica", Font.BOLD,16));
        UIManager.put("nimbusBase", Color.gray);

        //instancia de la interface ProductRecorder
        var app = new ProductRecorder();
        app.setVisible(true);
    }

    /**
     *Method that sets a different font on the main JFrame executed.
     */
    //definicio del metode al que cridarem després per asignar la font del programa
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        /**
         * @param keys Enumeration variable
         */
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }
}
