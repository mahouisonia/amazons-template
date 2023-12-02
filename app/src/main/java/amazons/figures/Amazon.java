package amazons.figures;

import amazons.board.Board;
import amazons.board.CardinalDirection;
import amazons.board.MatrixBoard;
import amazons.board.Position;
import amazons.player.PlayerID;

import java.util.ArrayList;
import java.util.List;

public class Amazon extends MovableFigure implements Figure{
    Position position;
    public Amazon(Position position){ this.position = position;}
    @Override
    public boolean canMoveTo(Position position, Board board) {
        return false;
    }

    @Override
    public void moveTo(Position position, Board board) {

    }

    @Override
    public void setPosition(Position position) {

    }

    @Override
    public PlayerID getPlayerID() {
        return null;
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









        return null;
    }
}
