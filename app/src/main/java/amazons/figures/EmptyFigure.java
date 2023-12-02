package amazons.figures;

import amazons.board.Board;
import amazons.board.Position;
import amazons.player.PlayerID;

public class EmptyFigure implements Figure{
    public static final EmptyFigure EMTPY_FIGURE = new EmptyFigure();  // Attribut statique pour représenter une instance unique d'EmptyFigure
    private EmptyFigure(){};// Constructeur privé, car on ne veut qu'une seule instance d'EmptyFigure
    @Override
    public boolean canMoveTo(Position position, Board board) {
        // Une case vide ne peut pas être déplacée
        return false;
    }

    @Override
    public void moveTo(Position position, Board board) throws IllegalAccessException {
        throw new IllegalAccessException(" Attention ! une case vide ne peut pas être déplacée");

    }

    @Override
    public void setPosition(Position position) {
        // Ne rien faire, car une case vide n'a pas de position
    }

    @Override
    public PlayerID getPlayerID() {
        return PlayerID.NONE;
    }
}
