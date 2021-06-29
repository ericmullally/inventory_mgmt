package core.classes;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> parts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     *
     * @param id int value
     * @param name string value
     * @param price double value
     * @param stock int value
     * @param min int value
     * @param max int value
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return The part instance Id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name of the product instance
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price of the product instance
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock of the product instance
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min of the product instance
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max of the product instance
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param newPart New Part object to add.
     */
    public void addAssociatedPart(Part newPart){
        this.parts.add(newPart);
    }

    /**
     * @param Id Id of Part to remove.
     * @return true if Part found and removed, false otherwise.
     */
    public boolean deleteAssociatedPart(int Id){
        for(int i =0; i < parts.size(); i ++){
            if(Id == parts.get(i).getId() ){
                parts.remove(i);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     *
     * @return the associated part list.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return this.parts;
    }
}
