package core.classes;
// needs to be static


public class InHouse extends Part{
    int machineId = -1;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
     super(id, name, price, stock, min, max);
     this.machineId = machineId;
    }

    public void setMachinedId(int id){
        this.machineId = id;
    }

    public int getMachinedId (){
        return this.machineId;
    }

}
