package spw4.game2048;

import java.util.Random;

public class GameImpl implements Game {
    private int[][] board;
    private int score;
    private int moves;
    private boolean isOver;
    private boolean isWon;
    public GameImpl() {
        board = new int[4][4];
        score = 0;
        moves = 0;
        isOver = false;
        isWon = false;
        initialize();
    }

    public int getMoves() {
        return moves;
    }

    public int getScore() {
        return score;
    }
    private boolean isValidCell(int row, int col){
        return row >=0 && col >= 0 && row < 4 && col < 4;
    }
    public int getValueAt(int row, int col) {
        if (!isValidCell(row, col)) return -1;
        return board[row][col];
    }


    public boolean isOver() {
        return isOver;
    }

    public boolean isWon() {
        return isWon;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int x = 0; x < 4; x ++) {
            for (int y = 0; y < 4; y++) {
                if(board[x][y] != 0)sb.append(board[x][y]);
                else sb.append(".");
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void initialize() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                board[row][col] = 0;
            }
        }

        score = 0;
        moves = 0;
        isOver = false;
        isWon = false;

        addRandomField();
        addRandomField();
    }

    private void addRandomField(){
        Random random = new Random();
        int rndVal = random.nextInt(10) + 1;  // Generates either 1 or 2
        int value = rndVal < 10 ? 2 : 4;
        int row, col;

        do {
            row = random.nextInt(4);
            col = random.nextInt(4);
        } while (board[row][col] != 0);

        board[row][col] = value;
    }

    public void move(Direction direction) {
        boolean moved = false;

        switch (direction) {
            case up:
                moved = moveUp();
                break;
            case down:
                moved = moveDown();
                break;
            case left:
                moved = moveLeft();
                break;
            case right:
                moved = moveRight();
                break;
        }

        if (moved) {
            moves++;
            addRandomField();
            checkGameOver();
            checkWinningCondition();
        }
    }

    private void checkGameOver(){
        boolean hasEmptyCell = false;
        boolean canMove = false;

        // Check for empty cells
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 0) {
                    hasEmptyCell = true;
                    break;
                }
            }
            if (hasEmptyCell) {
                break;
            }
        }

        // Check for possible moves
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int currentTile = board[row][col];
                if (row < 3 && board[row + 1][col] == currentTile) {
                    canMove = true;
                    break;
                }
                if (col < 3 && board[row][col + 1] == currentTile) {
                    canMove = true;
                    break;
                }
            }
            if (canMove) {
                break;
            }
        }

        isOver = !hasEmptyCell && !canMove;
    }

    private void checkWinningCondition(){
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 2048) {
                    isWon = true;
                    return;
                }
            }
        }
        isWon = false;
    }

    private boolean moveUp() {
        boolean moved = false;
        for (int col = 0; col < 4; col++) {
            for (int row = 1; row < 4; row++) {
                if (board[row][col] != 0) {
                    int currentRow = row;
                    while (currentRow > 0 && board[currentRow - 1][col] == 0) {
                        board[currentRow - 1][col] = board[currentRow][col];
                        board[currentRow][col] = 0;
                        currentRow--;
                        moved = true;
                    }
                    if (currentRow > 0 && board[currentRow - 1][col] == board[currentRow][col]) {
                        board[currentRow - 1][col] *= 2;
                        score += board[currentRow - 1][col];
                        board[currentRow][col] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {
                if (board[row][col] != 0) {
                    int currentRow = row;
                    while (currentRow < 3 && board[currentRow + 1][col] == 0) {
                        board[currentRow + 1][col] = board[currentRow][col];
                        board[currentRow][col] = 0;
                        currentRow++;
                        moved = true;
                    }
                    if (currentRow < 3 && board[currentRow + 1][col] == board[currentRow][col]) {
                        board[currentRow + 1][col] *= 2;
                        score += board[currentRow + 1][col];
                        board[currentRow][col] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveLeft() {
        boolean moved = false;
        for (int col = 1; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                if (board[row][col] != 0) {
                    int currentCol = col;
                    while (currentCol > 0 && board[row][currentCol - 1] == 0) {
                        board[row][currentCol -1] = board[row][currentCol];
                        board[row][currentCol] = 0;
                        currentCol--;
                        moved = true;
                    }
                    if (currentCol > 0 && board[row][currentCol -1] == board[row][currentCol]) {
                        board[row][currentCol - 1] *= 2;
                        score += board[row][currentCol -1];
                        board[row][currentCol] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 4; row++) {
                if (board[row][col] != 0) {
                    int currentCol = col;
                    while (currentCol < 3 && board[row][currentCol + 1] == 0) {
                        board[row][currentCol + 1] = board[row][currentCol];
                        board[row][currentCol] = 0;
                        currentCol++;
                        moved = true;
                    }
                    if (currentCol < 3 && board[row][currentCol + 1] == board[row][currentCol]) {
                        board[row][currentCol + 1] *= 2;
                        score += board[row][currentCol + 1];
                        board[row][currentCol] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    //these initializers are for testing only
    public void initGameOver(){
        for(int row = 0; row < 4; row ++) {
            for (int col = 0; col < 4; col++) {
                if(row % 2 == 0){
                    if(col % 2 == 0) board[row][col] = 16;
                    else board[row][col]=32;
                }
                else{
                    if(col % 2 != 0) board[row][col] = 128;
                    else board[row][col]=256;
                }
            }
        }
        board[0][0] = 0;
    }
    public void initGameWon(){
        board[0][0] = 1024;
        board[0][1] = 1024;
    }

    public void initCombineHorizontal(){
        initializeTester();
        board[0][0] = 2;
        board[0][1] = 2;
        board[1][0] = 2;
        board[1][1] = 2;
    }

    public void initCombineVertical(){
        initializeTester();
        board[1][1] = 2;
        board[2][1] = 2;
        board[1][2] = 2;
        board[2][2] = 2;
    }

    public void initializeTester() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                board[row][col] = 0;
            }
        }

        score = 0;
        moves = 0;
        isOver = false;
        isWon = false;
    }
}
