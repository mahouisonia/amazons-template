package amazons.game;

import amazons.IllegalMoveException;
import amazons.board.*;
import amazons.figures.Amazon;
import amazons.figures.MovableFigure;
import amazons.player.Move;
import amazons.player.Player;
import amazons.player.PlayerID;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;


public class Game {
    public static final int NUMBER_OF_PLAYERS = 2;
    public static final int DEFAULT_NUMBER_OF_AMAZONS = 4;
    private static final int DEFAULT_NUMBER_OF_COLUMNS = 10;
    private static  final int DEFAULT_NUMBER_OF_ROWS = 10;

    protected Board board;


    private static final List<Position> DEFAULT_PLAYER0_POSITIONS =
            List.of(new Position(0,6), new Position(9,6), new Position(3,9), new Position(6,9));
    private static final List<Position> DEFAULT_PLAYER1_POSITIONS =
            List.of(new Position(3,0), new Position(6,0), new Position(0,3), new Position(9,3));

    private List<Position>[] initialPositions = new List[2];

    private  List<Position> PLAYER0_POSITIONS = new ArrayList<>(DEFAULT_PLAYER0_POSITIONS);
    private  List<Position> PLAYER1_POSITIONS = new ArrayList<>(DEFAULT_PLAYER1_POSITIONS);


    private final Player[] players = new Player[NUMBER_OF_PLAYERS];

    private PlayerID winner;

    private int turn = 0;
    private Player player0;
    private Player player1;
    private boolean isThisIsTheEnd = false;


    public Game() {
        this.board = new MatrixBoard(DEFAULT_NUMBER_OF_ROWS, DEFAULT_NUMBER_OF_COLUMNS);
        initialPositions[0] = DEFAULT_PLAYER0_POSITIONS;
        initialPositions[1] = DEFAULT_PLAYER1_POSITIONS;

    }

    public List<Position>[] getInitialPositions() {
        return initialPositions;
    }

    public Player[] getPlayers() {
        return players;
    }

    public boolean isThisIsTheEnd() {
        return isThisIsTheEnd;
    }


    public void initializeGame(Player player0, Player player1) {
        player0.initialize(DEFAULT_NUMBER_OF_COLUMNS, DEFAULT_NUMBER_OF_ROWS, PlayerID.PLAYER_ZERO, initialPositions);
        player1.initialize(DEFAULT_NUMBER_OF_COLUMNS, DEFAULT_NUMBER_OF_ROWS, PlayerID.PLAYER_ONE, initialPositions);
        for(Position p: PLAYER0_POSITIONS){
            Amazon a = new Amazon(p, PlayerID.PLAYER_ZERO);
            board.setFigure(p, a);
        }
        for(Position p: PLAYER1_POSITIONS){
            Amazon a = new Amazon(p, PlayerID.PLAYER_ONE);
            board.setFigure(p, a);
        }
        this.player0 = player0;
        this.player1 = player1;
    }

    private List<MovableFigure> createPlayersFiguresWithDefaultPosition(){
        List<MovableFigure> allPlayersFigures = new ArrayList<>();
        for(Position position: DEFAULT_PLAYER0_POSITIONS){
            allPlayersFigures.add(new Amazon(position, PlayerID.PLAYER_ZERO));
        }
        for(Position position: DEFAULT_PLAYER1_POSITIONS){
            allPlayersFigures.add(new Amazon(position, PlayerID.PLAYER_ONE));
        }
        return allPlayersFigures;
    }

    public void updateGame(Move move){
        try{
            updateGameAmazonMove(move.getAmazonStartPosition(),move.getAmazonDstPosition());
            updateGameArrowShot(move.getAmazonDstPosition(),move.getArrowDestPosition());
        }
        catch (IllegalMoveException e ){
            if(getPlayer()==player0){
                winner = PlayerID.PLAYER_ONE;
            }
            else {
                winner = PlayerID.PLAYER_ZERO;
            }
        }
        turn++;
    }


    public void updateGameAmazonMove(Position amazonStartPosition, Position amazonDstPosition){
        board.moveFigure(amazonStartPosition,amazonDstPosition);
        updatePositions(amazonStartPosition, amazonDstPosition);

    }

    private void updatePositions(Position amazonStartPosition, Position amazonDstPosition){
        if (getPlayerID() == PlayerID.PLAYER_ZERO){
            for (int i=0;i<4;i++){
                Position p = PLAYER0_POSITIONS.get(i);
                if (p.equals(amazonStartPosition)){
                    PLAYER0_POSITIONS.remove(p);
                    break;
                }
            }
            PLAYER0_POSITIONS.add(amazonDstPosition);
        }else{
            for (int i=0;i<4;i++){
                Position p = PLAYER1_POSITIONS.get(i);
                if (p.equals(amazonStartPosition)){
                    PLAYER1_POSITIONS.remove(p);
                    break;
                }
            }
            PLAYER1_POSITIONS.add(amazonDstPosition);
        }
    }

    private void decideWinner(){
        boolean foundWinner0 = true;
        boolean foundWinner1 = true;

        for(Position p: PLAYER0_POSITIONS){
            Amazon a = new Amazon(p, PlayerID.PLAYER_ZERO);
            if(!a.getAccessiblePositions(board).isEmpty()){
                foundWinner0 = false;
                System.out.println("player 0 :" + a.getPosition() +  a.getAccessiblePositions(board));
                break;
            }
        }

        for(Position p: PLAYER1_POSITIONS){
            Amazon a = new Amazon(p, PlayerID.PLAYER_ONE);
            if(!a.getAccessiblePositions(board).isEmpty()){
                foundWinner1 = false;
                System.out.println("player 1 :" +a.getPosition() + a.getAccessiblePositions(board));
                break;
            }
        }

        if(foundWinner1){
            winner = PlayerID.PLAYER_ZERO;
        }else if(foundWinner0){
            winner = PlayerID.PLAYER_ONE;
        }
    }

    public void updateGameArrowShot(Position amazonDstPosition, Position arrowDstPosition) {
        board.shootArrow(amazonDstPosition,arrowDstPosition);
        decideWinner();
    }


    private boolean hasLost(PlayerID playerID) {
        return winner != playerID;
    }


    public Board getBoard(){
        return board;
    }


    public PlayerID getWinner(){
        return winner;
    }


    public PlayerID getPlayerID(){
        if(turn % 2 == 0) return PlayerID.PLAYER_ZERO;
        return PlayerID.PLAYER_ONE;
    }


    public Player getPlayer() {
        if(turn % 2 == 0){
            return player0;
        }
        return player1;
    }


    public boolean hasEnded() {
        return winner != null;
    }

    public void incrementTurn(){
        turn++;
    }

    public int getTurn() {return turn; }


    public int getNumberOfColumns(){
        return DEFAULT_NUMBER_OF_COLUMNS;
    }

    public int getNumberOfRows(){
        return DEFAULT_NUMBER_OF_ROWS;
    }
}
