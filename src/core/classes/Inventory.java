package core.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
  private ObservableList<Part> allParts = FXCollections.observableArrayList();
  private ObservableList<Product> allProducts = FXCollections.observableArrayList();


  /**
   * could need params.
   */
  public Inventory(){

  }

  /**
   * @param newPart adds a new part to the inventory part list.
   */
  public void addPart(Part newPart){
    this.allParts.add(newPart);
  }

  /**
   * @param newProduct adds a new product to the inventory product list.
   */
  public void addProduct(Product newProduct){
    this.allProducts.add(newProduct);
  }

  /**
   * @param partId looks up a part in the inventory by it's ID.
   * @return a Part object if a matching ID is found.
   */
//  public Part lookUpPart(int partId){
//
//  }

  /**
   * @param partName looks up a part in the inventory by it's Name.
   * @return a list of matching parts. name may be partial.
   */
//  public Part[] lookUpPart(String partName){
//
//  }

  /**
   * @param productId looks up a product in the inventory by it's ID.
   * @return a Product object if a matching ID is found.
   */
//  public Product lookUpProduct(int productId){
//
//  }

  /**
   * @param productName looks up a product in the inventory by it's Name.
   * @return a list of matching products. name may be partial.
   */
//  public Product lookUpProduct(String productName){
//
//  }

  /**
   * @param index parts position in the list.
   * @param part Part Object to replace the old version.
   * finds and replaces an existing part in the part list.
   */
  public void updatePart(int index, Part part){

  }

  /**
   * @param index Products position in the list.
   * @param product Product Object to replace the old version.
   * finds and replaces an existing Product in the Product list.
   */
  public void updateProduct(int index, Product product){

  }

  /**
   * @param part Part object to be removed.
   * @return true if part was found and successfully removed, false otherwise.
   */
  public boolean deletePart(Part part){
    return true;
  }

  /**
   * @param product Product object to be removed.
   * @return true if product was found and successfully removed, false otherwise.
   */
  public boolean deleteProduct(Product product){
    return true;
  }

  /**
   * @return the Parts list.
   */
  public  ObservableList<Part> getAllParts(){
    return allParts;
  }

  /**
   * @return the product list.
   */
  public ObservableList<Product> getAllProducts(){
    return allProducts;
  }

}
