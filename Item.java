
/**
 * EXERCISE 8.20: ADDING ITEM CLASS
 * "This class has fields for a description and weight, and a room to hold a reference to an item object."
 *
 * @author Franco Acosta
 * @version 12/8/25
 */
public class Item
{
    private String description; //item description for each instance.
    private int weight; //item weight for each instance.
    
    /**
     * Constructor for Item class with two fields: description and weight.
     * @param Str description, int weight.
     */
    public Item(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
    }
    
    /**
     * Getter method for instance's description.
     * @return description.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Getter method for instance's weight.
     * @return weight.
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Returns Str containing instance's description AND weight.
     * @return weight and description.
     */
    public String toString()
    {
        return description + " (weight: " + weight + ")";
    }
}