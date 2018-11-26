package csc207project.gamecentre.MainMenu.GameLibFragment;

/**
 * Game information.
 * A POJO.
 */
public class Game {

    /**
     * The name of this game.
     */
    private int gameName;

    /**
     * The description of this game.
     */
    private int gameDescription;

    /**
     * The starting activity of this game.
     */
    private Class<?> startGameClass;

    /**
     * A new game with given name, description and starting activity.
     *
     * @param gameName the name of this game
     * @param gameDescription the description of this game
     * @param startGameClass the starting activity of this game
     */
    public Game(int gameName, int gameDescription, Class<?> startGameClass) {
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.startGameClass = startGameClass;
    }

    /**
     * @return the name of this game
     */
    public int getGameName() {
        return gameName;
    }

    /**
     * @return the description of this game
     */
    public int getGameDescription() {
        return gameDescription;
    }

    /**
     * @return the starting activity of this game
     */
    public Class<?> getStartGameClass() {
        return startGameClass;
    }
}
