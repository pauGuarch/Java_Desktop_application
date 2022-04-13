package cat.mvm.modular;

/**
 * Definition for the object Product and it's attributes
 */
public class Product {
    /**
     * @param code it contains the code of the product
     * @param name it contains the name of the product
     * @param family it contains the family code value
     * @param type it contains a short description of the product
     * @param price it contains the price of the product
     * @param amount it contains the total amount of that product to be stored
     */

    private String code;
    private String name;
    private int family;
    private String type;
    private float price;
    private int amount;

    public Product(String code, String name, int family, String type, float price, int amount) {
        setCode(code);
        setName(name);
        setFamily(family);
        setType(type);
        setPrice(price);
        setAmount(amount);
    }

    //<editor-fold desc="Accessor methods">
    /**Getters for Product*/
    public String getCode() { return code; }
    public String getName() { return name; }
    public int getFamily() { return family; }
    public String getType() { return type; }
    public float getPrice() { return price; }
    public int getAmount() { return amount; }

    /**Setters for Product*/
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setFamily(int family) { this.family = family; }
    public void setType(String type) { this.type = type; }
    public void setPrice(float price) { this.price = price; }
    public void setAmount(int amount) { this.amount = amount; }
    //</editor-fold>
}




