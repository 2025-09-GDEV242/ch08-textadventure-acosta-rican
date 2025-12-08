import java.util.Stack;
import java.util.HashMap; //added for 8.30

/**
 * Write a description of class Player here.
 *
 * @author Franco Acosta
 * @version 12/08/25
 */

public class Player
{
    private Room currentRoom;
    private Stack<Room> roomHistory = new Stack<>();    //added for 8.26 
    private HashMap<String, Item> inventory;
    
    /**
     * Constructor for Player class
     * @params starting room.
     */
    public Player(Room startingRoom) {
        this.currentRoom = startingRoom;
        this.roomHistory = new Stack<>();
        inventory = new HashMap<>();    //added for 8.30
    }

    /**
     * Getter method for current room.
     * @return current room.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Getter method for room description.
     * @return current room description.
     */
    public String getCurrentRoomDescription() {
        return currentRoom.getLongDescription();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    public void goRoom(String direction) {
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            roomHistory.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * EXERCISE 8.23:
     * Add 'back' command to return to previous room.
     * Null at start of game.
     */
    public void goBack() {
        if(roomHistory.empty()) {
            System.out.println("You can't go back any further!");
            return;
        }

        currentRoom = roomHistory.pop();
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * EXERCISE 8.29
     * Method to take item from Room and add it to Player.
     * @params item name.
     */
    public void takeItem(String itemName) {
        Item roomItem = currentRoom.getItem(itemName);

        if (roomItem == null) {
            System.out.println("There is no such item here.");
            return;
        }

        // Add to inventory
        inventory.put(roomItem.getDescription(), roomItem);

        // Remove from room
        currentRoom.removeItem(itemName);

        System.out.println("You picked up: " + roomItem.getDescription());
    }

    
    /**
     * EXERCISE 8.29
     * Method to drop item from Player and add it to Room.
     * @null
     */
    public void dropItem(String itemName) {
        Item item = inventory.get(itemName);

        if (item == null) {
            System.out.println("You are not carrying that item.");
            return;
        }

        currentRoom.addItem(item);
        inventory.remove(itemName);

        System.out.println("You dropped: " + item.getDescription());
    }

}