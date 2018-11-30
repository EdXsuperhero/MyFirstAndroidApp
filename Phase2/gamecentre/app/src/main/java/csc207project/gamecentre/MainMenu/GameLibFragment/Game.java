package csc207project.gamecentre.MainMenu.GameLibFragment;

/**
 * Game information.
 * A POJO.
 * # Exclude from code coverage because it's a model class.
 */
public class Game {

    /**
     * The image view of this game.
     */
    private int gameImage;

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
     * The scoreboard activity of this game.
     */
    private Class<?> scoreboardClass;

    /**
     * A new game with given name, description and starting activity.
     *
     * @param gameImage the image view of this game
     * @param gameName the name of this game
     * @param gameDescription the description of this game
     * @param startGameClass the starting activity of this game
     */
    public Game(int gameImage, int gameName, int gameDescription, Class<?> startGameClass, Class<?> scoreboardClass) {
        this.gameImage = gameImage;
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.startGameClass = startGameClass;
        this.scoreboardClass = scoreboardClass;
    }

    /**
     * @return the image view of this game
     */
    int getGameImage() {
        return gameImage;
    }

    /**
     * @return the name of this game
     */
    int getGameName() {
        return gameName;
    }

    /**
     * @return the description of this game
     */
    int getGameDescription() {
        return gameDescription;
    }

    /**
     * @return the starting activity of this game
     */
    Class<?> getStartGameClass() {
        return startGameClass;
    }

    /**
     * @return the scoreboard activity of this game
     */
    Class<?> getScoreboardClass() {
        return scoreboardClass;
    }
}
