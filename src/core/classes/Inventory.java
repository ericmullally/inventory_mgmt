package core.classes;






public class Inventory {
  static Part[] Parts = {};
  static Product[] Products = {};


//  /**
//   * could need params.
//   */
//  public Inventory(){
//
//  }

  /**
   * @param newPart adds a new part to the inventory part list.
   */
  public static void addPart(Part newPart){

  }

  /**
   * @param newProduct adds a new product to the inventory product list.
   */
  public static void addProduct(Product newProduct){

  }

  /**
   * @param partId looks up a part in the inventory by it's ID.
   * @return a Part object if a matching ID is found.
   */
//  public static Part lookUpPart(int partId){
//
//  }

  /**
   * @param partName looks up a part in the inventory by it's Name.
   * @return a list of matching parts. name may be partial.
   */
//  public static Part[] lookUpPart(String partName){
//
//  }

  /**
   * @param productId looks up a product in the inventory by it's ID.
   * @return a Product object if a matching ID is found.
   */
//  public static Product lookUpProduct(int productId){
//
//  }

  /**
   * @param productName looks up a product in the inventory by it's Name.
   * @return a list of matching products. name may be partial.
   */
//  public static Product lookUpProduct(String productName){
//
//  }

  /**
   * @param index parts position in the list.
   * @param part Part Object to replace the old version.
   * finds and replaces an existing part in the part list.
   */
  public static void updatePart(int index, Part part){

  }

  /**
   * @param index Products position in the list.
   * @param product Product Object to replace the old version.
   * finds and replaces an existing Product in the Product list.
   */
  public static void updateProduct(int index, Product product){

  }

  /**
   * @param part Part object to be removed.
   * @return true if part was found and successfully removed, false otherwise.
   */
  public static boolean deletePart(Part part){
    return true;
  }
  /**
   * @param product Product object to be removed.
   * @return true if product was found and successfully removed, false otherwise.
   */
  public static boolean deleteProduct(Product product){
    return true;
  }

  /**
   * @return the Parts list.
   */
  public static Part[] getAllParts(){
    return Parts;
  }

  /**
   * @return the product list.
   */
  public static Product[] getAllProducts(){
    return Products;
  }

}
