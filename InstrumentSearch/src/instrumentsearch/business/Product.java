package instrumentsearch.business;

import java.text.NumberFormat;

/*
    Christopher Torres
    12/12/2018
    
    Product class holds all information about an intrument.
*/
public class Product {
    
    private int productID;
    private int categoryID;
    private String productCode;
    private String productName;
    private double listPrice;
    private String formattedListPrice;
    
    //Constructor
    public Product(int productID, int categoryID, String productCode, 
                    String productName, double listPrice){
        this.productID = productID;
        this.categoryID = categoryID;
        this.productCode = productCode;
        this.productName = productName;
        this.listPrice = listPrice;
        
        //Used for currecy formatting
         NumberFormat nf = NumberFormat.getCurrencyInstance();
         this.formattedListPrice = nf.format(listPrice);
    }

    /**
     * @return the productID
     */
    public int getProductID() {
        return productID;
    }

    /**
     * @param productID the productID to set
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * @return the categoryID
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * @return the productCode
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * @param productCode the productCode to set
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the listPrice
     */
    public double getListPrice() {
        return listPrice;
    }

    /**
     * @param listPrice the listPrice to set
     */
    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    /**
     * @return the formattedListPrice
     */
    public String getFormattedListPrice() {
        return formattedListPrice;
    }

    /**
     * @param formattedListPrice the formattedListPrice to set
     */
    public void setFormattedListPrice(String formattedListPrice) {
        this.formattedListPrice = formattedListPrice;
    }
}
