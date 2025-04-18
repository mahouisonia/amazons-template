package amazons.figures;

import amazons.IllegalMoveException;
import amazons.board.Board;
import amazons.board.Position;
import amazons.player.PlayerID;

public class ArrowFigure implements Figure{
    private static Position startPosition;
    public static ArrowFigure ARROW_FIGURE = new ArrowFigure(startPosition);
    public ArrowFigure(Position startPosition){}

    @Override
    public boolean canMoveTo(Position position, Board board) {
        return false;
    }

    @Override
    public void moveTo(Position position, Board board) throws IllegalMoveException {
        throw new IllegalMoveException(" Attention ! une flèche ne peut pas être déplacée");

    }

    @Override
    public void setPosition(Position position) {

    }

    @Override
    public PlayerID getPlayerID() {
        return PlayerID.NONE;
    }
}
