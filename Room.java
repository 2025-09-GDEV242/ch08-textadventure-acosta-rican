import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;     //imported for 8.22
import java.util.Iterator;      //imported for 8.30

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * @edited by Franco Acosta
 */

public class Room 
{
    private String description;                 //stores room's description
    private HashMap<String, Room> exits;        //stores exits of this room.
    private Item item;                          //lets room have item
    private ArrayList<Item> items;              //lets room have multiple items (8.22)
    
    /**
     * modified for 8.22
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }
    
    
    /**
     * Method to return current item.
     * @return Item.
     */
    public Item getItem()
    {
        return item;
    }
    
    /**
     * EXERCISE 8.20
     * Setter method to give the room an item.
     * @set room's item.
     */
    public void setItem(Item item)
    {
        this.item = item;
    }    
    
    /**
     * EXCERCISE 8.22
     * Method to add item to room.
     * @param item.
     */
    public void addItem(Item item) {
        items.add(item);
    }
    
    /**
     * Get an item by name
     * @returns Item
     */
    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getDescription().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * / Remove an item by name
     * @params Item name
     */
    public void removeItem(String name) {
        Iterator<Item> it = items.iterator();

        while (it.hasNext()) {
            Item item = it.next();
            if (item.getDescription().equalsIgnoreCase(name)) {
            it.remove();
            return;
            }
        }
    }
    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        String description = "You are " + this.description + ".\n" + getExitString();

        if (!items.isEmpty()) { //lists all the items in the room
            description += "\nItems here:";
            for (Item item : items) {
                description += "\n  " + item.toString();
            }
        }
    
        return description;
    }
    
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

