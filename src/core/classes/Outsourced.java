package core.classes;
// needs to be static

public class Outsourced extends Part{

    String companyName = "";

    /**
     *
     * @param id Auto-generated ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Amount on hand
     * @param min Minimum amount to have in stock at all times
     * @param max Maximum amount to have on hand
     * @param companyName Name of the manufacturing company
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id,name,price,stock,min,max);
        this.companyName = companyName;

    }

    public void setCompanyName(String name){
        this.companyName = name;
    }

    public String getCompanyName (){
        return this.companyName;
    }


}
