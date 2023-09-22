package Model;

/**
 * This class is used for a report that counts how many of each type of report is made.
 *
 * @author Ryan Blumenhorst
 * */
public class TypeSort {
    String name;
    int count;

    /**
     * TypeSort Constructor Method
     * @param type Type of appointment
     * @param amount Keeps track of the total of each type
     * */
    public TypeSort(String type, int amount){
        this.name = type;
        this.count = amount;
    }

    public void setType(String type){this.name = type;}
    public String getType(){return name;}

    public void setAmount(int amount){this.count = amount;}
    public int getAmount(){return count;}

}
