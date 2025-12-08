import java.util.Stack;     //imported for 8.26

/** CHAPTER 8: TEXT ADVENTURE
 * edited by Franco Acosta
 * 
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Player player;                              //added for 8.28
    private Room previousRoom;                          //added for 8.23
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * EXERCISE 8.14:
     * Add the 'look command to Game class.
     * @author  Michael Kölling and David J. Barnes
     * @version 2016.02.29
        */
    private void look()
    {
        System.out.println(player.getCurrentRoomDescription());
    }
    
    /**
     * EXERCISE 8.23:
     * Add 'back' command to return to previous room.
     * Null at start of game.
     */
    private void goBack() {
        player.goBack();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, cafeteria, gym, lounge; //added cafeteria, gym, and lounge
      
        // create the rooms, 8 minimum.
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cafeteria = new Room("in the campus cafeteria");
        gym = new Room("in the university's gym");
        lounge = new Room("in the student lounge");
        
        // EXERCISE 8.20: Create and assign items:
        outside.addItem(new Item("sword", 4));
        outside.addItem(new Item("old shield", 2));
        theater.addItem(new Item("book", 3));
        theater.addItem(new Item("pencil", 1));
        pub.addItem(new Item("key", 1));
        lab.addItem(new Item("heater", 2));
        lab.addItem(new Item("calculator", 3));
        office.addItem(new Item("coin", 1));
        cafeteria.addItem(new Item("scissors", 2));
        cafeteria.addItem(new Item("food",1));
        gym.addItem(new Item("shield", 5));
        lounge.addItem(new Item("potion", 2));
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", cafeteria);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        cafeteria.setExit("east", gym);
        cafeteria.setExit("west", lounge);
        cafeteria.setExit("south", outside);
        
        gym.setExit("west", cafeteria);
        gym.setExit("south", theater);
        
        lounge.setExit("east", cafeteria);
        lounge.setExit("south", pub);

        player = new Player(outside);
        previousRoom = null;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoomDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
            
            case LOOK:
                look();
                break;
                
            case TAKE:
                takeItem(command);
                break;
            
            case DROP:
                dropItem(command);
                break;
                
            case BACK:
                goBack();
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:
    
    /**
     * Command method to take item from room.
     * @params command word.
     */
    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();
        player.takeItem(itemName);
    }
    
    /**
     * Command method to drop item.
     * @null
     */
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        player.dropItem(itemName);
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        player.goRoom(direction);

    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal to quit
        }
    }
    
    /**
     * Main method for the Game class.
     * @null
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

}
