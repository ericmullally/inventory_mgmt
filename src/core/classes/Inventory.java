package core.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Inventory object holds parts and products entered into the system.
 */
public class Inventory {
  private ObservableList<Part> allParts = FXCollections.observableArrayList();
  private ObservableList<Product> allProducts = FXCollections.observableArrayList();


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
  public Part lookUpPart(int partId){
    int index = -1;
    for(int i =0; i < allParts.size(); i++){
      if(allParts.get(i).getId() == partId){
          index = i;
      }

    }
      return allParts.get(index);
  }

  /**
   * @param partName looks up a part in the inventory by it's Name.
   * @return a list of matching parts. name may be partial.
   */
  public ObservableList<Part> lookUpPart(String partName){
    String toMatch = partName.strip().toLowerCase()+".*";
    return allParts.stream().filter(part -> part.getName().toLowerCase().matches(toMatch)).collect(
            Collectors.toCollection(FXCollections::observableArrayList
            ));
  }

  /**
   * @param productId looks up a product in the inventory by it's ID.
   * @return a Product object if a matching ID is found.
   */
  public Product lookUpProduct(int productId){
    int index = -1;
    for(int i =0; i < allProducts.size(); i++){
      if(productId == allProducts.get(i).getId()){
        index = i;
      }
    }
    return allProducts.get(index);
  }

  /**
   * @param productName looks up a product in the inventory by it's Name.
   * @return a list of matching products. name may be partial.
   */
  public ObservableList<Product> lookUpProduct(String productName){
    String toMatch = productName.toLowerCase() + ".*";
    return allProducts.stream().filter(part -> part.getName().toLowerCase().matches(toMatch)).collect(
            Collectors.toCollection(FXCollections::observableArrayList
            ));
  }

  /**
   * @param index parts position in the list.
   * @param selectedPart Part Object to replace the old version.
   * finds and replaces an existing part in the part list.
   */
  public void updatePart(int index, Part selectedPart){
    allParts.remove(index);
    allParts.add(selectedPart);
  }

  /**
   * @param index Products position in the list.
   * @param newProduct Product Object to replace the old version.
   * finds and replaces an existing Product in the Product list.
   */
  public void updateProduct(int index, Product newProduct){
    allProducts.remove(index);
    allProducts.add(newProduct);
  }

  /**
   * @param part Part object to be removed.
   * @return true if part was found and successfully removed, false otherwise.
   */
  public boolean deletePart(Part part){
    return allParts.remove(part);
  }

  /**
   * @param product Product object to be removed.
   * @return true if product was found and successfully removed, false otherwise.
   */
  public boolean deleteProduct(Product product){
    return allProducts.remove(product);
  }

  /**
   * @return the Parts list.
   */
  public  ObservableList<Part> getAllParts(){ return allParts; }

  /**
   * @return the product list.
   */
  public ObservableList<Product> getAllProducts(){
    return allProducts;
  }

}
