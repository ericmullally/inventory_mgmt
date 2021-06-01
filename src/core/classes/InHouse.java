package core.classes;
// needs to be static


public class InHouse extends Part{
    int machinedId = 0;

    public InHouse(int id, String name, double price, int stock, int min, int max) {
     super(id, name, price, stock, min, max);
    }

    public void setMachinedId(int id){
        this.machinedId = id;
    }

    public int getMachinedId (){
        return this.machinedId;
    }

}
