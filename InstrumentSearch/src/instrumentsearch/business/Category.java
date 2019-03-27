package instrumentsearch.business;

/*
    Christopher Torres
    12/12/2018
    
    Category class holds all information about a intrument category
*/
public class Category {
    
    private int categoryID;
    private String categoryName;
    
    //Constructor
    public Category(int id, String name){
        this.categoryID = id;
        this.categoryName = name;
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
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    
    
}
