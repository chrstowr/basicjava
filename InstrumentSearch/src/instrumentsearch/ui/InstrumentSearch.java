package instrumentsearch.ui;

import instrumentsearch.business.Product;
import instrumentsearch.business.Category;
import instrumentsearch.db.ProductDB;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
    Christopher Torres
    12/12/2018
    
    InstrumentSearch is a tool that lets you search for intruments
    using their categories. You can change between a GUI or console
    interface by changing the LOAD_GUI variable. 
  
 */
public class InstrumentSearch extends Application {

    /*******************************************************
     true = load GUI OR if false = load console application
    ********************************************************/
    private final static boolean LOAD_GUI = true;
    
    private TableView table; 
    private ComboBox categoryComboBox;
    private Label categoryLabel;
    
    private ProductDB db;
    private List<Category> categoryList;
    

    @Override
    public void start(Stage stage) {
        
        //Initialize data objects
        db = new ProductDB();
        categoryList = db.getAllCategories();
        ObservableList<String> menuList = 
                FXCollections.observableArrayList(db.toStringList(categoryList));
        
        //Initialize elements - Label and combobox held in HBox
        table = new TableView();
        categoryLabel = new Label("Category: ");
        categoryComboBox = new ComboBox(menuList);
        HBox comboBoxGroup = new HBox(8);
        comboBoxGroup.getChildren().addAll(
                categoryLabel,categoryComboBox);
        
        //Set on actionlistener for combo box - onAction reload products
        // based on chosen categoryID
        categoryComboBox.setOnAction(event -> {
            String choice = categoryComboBox.getValue().toString();
            //Get index 0 which contains the category number and cast
            // to a int
            int categoryID = Integer.parseInt(choice.substring(0, 1));
            
            List<Product> productList = db.getProducts(categoryID);
            
            ObservableList<Product> data = 
                    FXCollections.observableArrayList(productList);
            
             table.setItems(data);
        
        });
        
        //Setup tableview for resulting product data
        
        // setCellValueFactory() creates a link between product instance variables
        // and row values
        TableColumn codeColumn = new TableColumn("Code");
        codeColumn.setCellValueFactory(
                new PropertyValueFactory<>("productCode")); 
        
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("productName"));
        
        TableColumn priceColumn = new TableColumn("Price");
        
        // I used a formatted version of listPrice  because it is not possible
        // to process values before inserting into table:
        // (Product::formattedListPrice)
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("formattedListPrice"));
        
        //Add column headers to table
        table.getColumns().addAll(codeColumn, nameColumn, priceColumn);
        
        //Setup column widths in 25%/50%/25% distribution.
        codeColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.50));
        priceColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.25));

        
        //Setup VBox for combobox and table
        VBox root = new VBox();
        root.setSpacing(5);
        root.setPadding(new Insets(10,10,10,10));
        root.getChildren().addAll(comboBoxGroup, table);
        
        //Setup scene
        Scene scene = new Scene(root);
        
        //Set stage
        stage.setTitle("Intrument Search");
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop(){
        db.closeConnection();
        System.out.println("Closing Db connection...");
    }
    
    /*******************************
     * MAIN CLASS
     ******************************/
    
     public static void main(String[] args) {
        
         if(LOAD_GUI == true){ //Load GUI
             launch(args);
         }
         else if(LOAD_GUI == false){ //Load Console Interface
             
            //Controls loop
            boolean continueMenu = true;
            
            ProductDB db = new ProductDB();
            List<Category> categoryList = db.getAllCategories();
            
            int input = 0; 
            
             while(continueMenu){
                 
                 //Print category list and wait for user input
                 printCategory(categoryList); 
                 input = Console.getInt("Enter a category id (999 to exit):");
                 System.out.println("\n");
                 
                 //Process user input
                 if(isAChoice(input,categoryList)){
                     //If choice is valid, print product list based off of
                     // categoryID
                     printProducts(input, db);
                 }else if(input == 999){
                     //Close program if input is 999, close DB connection.
                     db.closeConnection();
                     System.out.println("Closing DB connection...");
                     System.out.println("Exiting...");
                     continueMenu = false;
                 }else{
                     //Tell user that input was invalid.
                     System.out.println("\""+input+"\""+" not a valid choice.\n");
                 }
                 
             }
         }
    }
     
     /***********************************
      Helper methods for console program
     ***********************************/
     
     //Print the category choices passed from category list loaded
     // from DB
     public static void printCategory(List<Category> list){
         System.out.println("CATEGORIES");
         System.out.println("-----------");
         
         for(Category c : list){
             System.out.println(c.getCategoryID()+"-"+c.getCategoryName());
         }
         
         System.out.println();
     }
     
     //Check if user input a valid choice, return a boolean value
     public static boolean isAChoice(int input, List<Category> list){
         
         for(Category c : list){
             if(input == c.getCategoryID())
                 return true;
         }
         
         return false;
     }
     
     //Print products to console from given category
     public static void printProducts(int categoryID, ProductDB db){
         
         // Get product list where categoryID = this.categoryID
         List<Product> products = db.getProducts(categoryID);
         
        //Print table header 
        System.out.format("%-16s%-38s%-16s\n","CODE", "NAME", "PRICE");
        //Print divider line
        for(int i = 0; i < 72; i++){System.out.print("-");}
        System.out.println();
        
        //Print product information
         for(Product p : products){
             String code =  p.getProductCode();
             String name = p.getProductName();
             String price = p.getFormattedListPrice();
             
            System.out.format("%-16s%-38s%-16s\n",code, name, price);
         }
         
         System.out.println("\n");
     }
     
}
