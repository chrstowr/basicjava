package instrumentsearch.db;

import instrumentsearch.business.Product;
import instrumentsearch.business.Category;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
    Christopher Torres 
    12/12/2018

    ProductDB manages all database queries and supporting methods.
 */
public class ProductDB {
    
    private Connection connection;

    //Constructor - Calls sqlite driver manager and initializes it
    public ProductDB() {
        try {
            String dbUrl = "jdbc:sqlite:guitar_shop.sqlite";
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    //Close connection to DB
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    //Gets Product information from the passed resultset
    private Product getProductFromRow(ResultSet rs) throws SQLException {
        Product p;
        int productID;
        int categoryID;
        String productCode;
        String productName;
        double listPrice;
        
        productID = rs.getInt("productID");
        categoryID = rs.getInt("categoryID");
        productCode = rs.getString("productCode");
        productName = rs.getString("productName");
        listPrice = rs.getDouble("listPrice");
        
        p = new Product(productID, categoryID, productCode, productName,
                        listPrice);
        return p;
    }
    
    //Gets Product information from the passed resultset
    private Category getCategoryFromRow(ResultSet rs) throws SQLException {
        Category c;
        
        int categoryID;
        String categoryName;
        
        categoryID = rs.getInt("categoryID");
        categoryName = rs.getString("categoryName");
        
        c = new Category(categoryID, categoryName);
        
        return c;
    }
    
    //Get all products from DB
    public List<Product> getProducts(int categoryID) {
        String sql = "SELECT * FROM Product WHERE categoryID=?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
                ps.setInt(1, categoryID);
                ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(getProductFromRow(rs));
            }
            rs.close();
            ps.close();
            return products;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }
    
    //Get all categories from DB that match with passed category ID
    public List<Category> getAllCategories() {
        
        String sql = "SELECT * FROM Category ORDER BY categoryID ASC";
        List<Category> categories = new ArrayList<>();
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();){ 
            while (rs.next()) {
                categories.add(getCategoryFromRow(rs));
            }
            rs.close();
            ps.close();
            return categories;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }
    
    //Convert List of category objects to list of strings for use in combobox
    public ArrayList<String> toStringList(List<Category> list){
        
        ArrayList<String> stringList = new ArrayList<>();
        
        for(Category item : list ){
            stringList.add(item.getCategoryID() + " - " + item.getCategoryName());
        }
        
        return stringList;
    }
}
