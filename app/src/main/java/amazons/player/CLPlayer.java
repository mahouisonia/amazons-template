package amazons.player;

import amazons.board.Position;
import amazons.board.Position;
import java.util.List;
import java.util.Scanner;

public class CLPlayer implements Player {
    private PlayerID playerID;
    private final static Scanner inputScanner = new Scanner(System.in);

    private int boardWidth;
    private int boardHeight;

    private List<Position>[] initialPositions;

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public List<Position>[] getInitialPositions() {
        return initialPositions;
    }

    @Override
    public boolean isGUIControlled() {
        return false;
    }

    @Override
    public Move play(Move opponentMove) {
        System.out.println(playerID + " select amazon? (enter X Y coordinate)");
        int xQueen = inputScanner.nextInt();
        int yQueen = inputScanner.nextInt();
        Position amazonStartPosition = new Position(xQueen, yQueen);

        System.out.println(playerID + " select destination? (enter X Y coordinate)");
        int xDest = inputScanner.nextInt();
        int yDest = inputScanner.nextInt();
        Position amazonDestPosition = new Position(xDest, yDest);

        System.out.println(playerID + " where to shoot arrow? (enter X Y coordinate)");
        int xArrow = inputScanner.nextInt();
        int yArrow = inputScanner.nextInt();
        Position arrowDestPosition = new Position(xArrow, yArrow);

        return new Move(amazonStartPosition, amazonDestPosition, arrowDestPosition);
    }

    @Override
    public void initialize(int boardWidth, int boardHeight, PlayerID playerID, List<Position>[] initialPositions) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.playerID = playerID;
        this.initialPositions = initialPositions;
    }

    @Override
    public PlayerID getPlayerID() {
        return playerID;
    }
}
