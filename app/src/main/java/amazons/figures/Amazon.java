package amazons.figures;

import amazons.board.Board;
import amazons.board.CardinalDirection;
import amazons.board.Position;
import amazons.player.PlayerID;

import java.util.ArrayList;
import java.util.List;

public class Amazon extends MovableFigure implements Figure{
    Position position;
    PlayerID playerID;
    public Amazon(Position position, PlayerID playerID ){
        this.position = position;
        this.playerID= playerID;
    }
    @Override
    public boolean canMoveTo(Position position, Board board) {
        if (this.getAccessiblePositions(board).contains(position)){
            return true;
        }
        return false;
    }
    @Override
    public void moveTo(Position position, Board board) {
        this.setPosition(position);
        board.setFigure(position,this);



    }

    @Override
    public void setPosition(Position position) {
        this.position = position;


    }

    @Override
    public PlayerID getPlayerID() {
        return playerID;
    }

    @Override
    public List<Position> getAccessiblePositions(Board board) {
        List<Position> accessiblePositions = new ArrayList<>();

        for (CardinalDirection direction : CardinalDirection.values()) {
            Position currentPosition = position;

            while (true) {
                Position nextPosition = currentPosition.next(direction);

                if (board.isOutOfBoard(nextPosition)
                        || !board.isEmpty(nextPosition)) {
                    break;
                }
                accessiblePositions.add(nextPosition);
                currentPosition = nextPosition;
            }
        }

        return accessiblePositions;
    }

}
