package amazons.figures;

import amazons.board.Board;
import amazons.board.Position;
import amazons.player.PlayerID;

import java.util.List;

public class Amazon extends MovableFigure implements Figure{
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
        return null;
    }
}
