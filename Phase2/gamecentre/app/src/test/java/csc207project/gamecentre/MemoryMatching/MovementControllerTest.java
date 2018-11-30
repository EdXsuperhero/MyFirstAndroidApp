package csc207project.gamecentre.MemoryMatching;

import org.junit.Test;

import static org.junit.Assert.*;

public class MovementControllerTest {

    @Test
    public void setBoardManagerTest() {
        MovementController move1 = new MovementController();
        BoardManager manager1 = new BoardManager(6);
        move1.setBoardManager(manager1);
        boolean result1 = move1.getBoardManager().equals(manager1);
        assertTrue(result1);
    }

    @Test
    public void getBoardManagerTest() {
        MovementController move2 = new MovementController();
        BoardManager manager2 = new BoardManager(4);
        move2.setBoardManager(manager2);
        boolean result2 = move2.getBoardManager().equals(manager2);
        assertTrue(result2);
    }
}