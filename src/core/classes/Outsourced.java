package core.classes;

/**
 * extends the part class.
 * comes from another company, has a company name instead of machine Id.
 *
 * RUNTIME ERROR: Could not invoke getCompanyName.
 * Solution: I spelled it correctly.
 *
 * FUTURE IMPROVEMENT: Add more details about the part. add a list of products its associated with,
 * so that if it was deleted, it could be removed from them as well.
 */
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

    /**
     *
     * @param name to set.
     */
    public void setCompanyName(String name){
        this.companyName = name;
    }

    /**
     *
     * @return the company name
     */
    public String getCompanyName (){
        return this.companyName;
    }


}
