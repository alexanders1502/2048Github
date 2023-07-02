package spw4.game2048;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameImplTests {
    GameImpl game;
    @BeforeEach
    void createGame(){
        game = new GameImpl();
    }
    @Test
    void initliaise(){
        assertDoesNotThrow(() -> {game.initialize();});
    }

    @Test
    void toStringOnlyDots(){
        game.initialize();
        String s = game.toString();
        GameImpl game = new GameImpl();
        game.initialize();

        assertTrue(game.toString().contains("2") || game.toString().contains("4"));
    }

    @Test
    void valueAt0Equal14(){
        game.initialize();
        int count0 = 0;
        for(int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int at = game.getValueAt(row,col);
                if(at == 0) count0++;
            }
        }
        assertEquals(14, count0);
    }

    @Test
    void moveTest(){
        game.initialize();
        int moves = game.getMoves();
        assertEquals(0, moves);
        game.move(Direction.up);

        //If random generates impossible move
        if(game.getMoves() == 0)
            game.move(Direction.down);
        moves = game.getMoves();
        assertEquals(1, moves);
    }

    @Test
    void scoreIs0(){
        game.initialize();
        assertEquals(0, game.getScore());
    }

    @Test
    void movesIs0(){
        game.initialize();
        assertEquals(0, game.getMoves());
    }

    @Test
    void moveUpDoesNotThrow(){
        game.initialize();
        assertDoesNotThrow(() -> {game.move(Direction.up);});
    }

    @Test
    void moveDownDoesNotThrow(){
        game.initialize();
        assertDoesNotThrow(() -> {game.move(Direction.down);});
    }

    @Test
    void moveLeftDoesNotThrow(){
        game.initialize();
        assertDoesNotThrow(() -> {game.move(Direction.left);});
    }

    @Test
    void moveRightDoesNotThrow(){
        game.initialize();
        assertDoesNotThrow(() -> {game.move(Direction.right);});
    }

    @Test
    void gameWonIsFalseOnInit(){
        game.initialize();
        assertFalse(game.isWon());
    }

    @Test
    void gameOverIsFalseOnInit(){
        game.initialize();
        assertFalse(game.isOver());
    }

    @Test
    void gameOverIsTrueWithGameOverInit(){
      game.initGameOver();
      game.move(Direction.left);
      assertTrue(game.isOver());
    }

    @Test
    void gameWonIsTrueWithGameWonInit(){
        game.initGameWon();
        game.move(Direction.left);
        assertTrue(game.isWon());
    }

    @Test
    void scoreIncreaseOnCombineLeft(){
        game.initCombineHorizontal();
        game.move(Direction.left);
        assertEquals(8, game.getScore());
    }

    @Test
    void scoreIncreaseOnCombineRight(){
        game.initCombineHorizontal();
        game.move(Direction.right);
        assertEquals(8, game.getScore());
    }

    @Test
    void scoreIncreaseOnCombineUp(){
        game.initCombineVertical();
        game.move(Direction.up);
        assertEquals(8, game.getScore());
    }

    @Test
    void scoreIncreaseOnCombineDown(){
        game.initCombineVertical();
        game.move(Direction.down);
        assertEquals(8, game.getScore());
    }

}
