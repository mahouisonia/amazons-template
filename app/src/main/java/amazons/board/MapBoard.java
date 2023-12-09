package amazons.board;


import amazons.IllegalMoveException;
import amazons.figures.Amazon;
import amazons.figures.ArrowFigure;
import amazons.figures.EmptyFigure;
import amazons.figures.Figure;
import amazons.player.PlayerID;
import amazons.figures.MovableFigure;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class MapBoard implements Board  {
    private int numberOfRows;
    private int numberOfColumns;
    private Map<Position,Figure> board;

    public MapBoard(){
        this.board = new HashMap<>();
    }

    public void setFigure(Position position, Figure figure){
        board.put(position, figure);
    }

    public Figure getFigure(Position position){
        return board.get(position);
    }

    public boolean isEmpty(Position position){
        return this.getFigure(position) == EmptyFigure.EMPTY_FIGURE;
    }

    public  boolean isOutOfBoard(Position position) {
        return !board.containsKey(position);
    }

    public void moveFigure(Position startPosition, Position dstPosition) throws IllegalMoveException{
        if (this.getFigure(startPosition) == EmptyFigure.EMPTY_FIGURE){
            throw new IllegalMoveException("pas de figure a deplacer");
        } else if (!getFigure(startPosition).canMoveTo(dstPosition,this)) {
            throw new IllegalMoveException("case destinataire non vide");
        }
        board.put(dstPosition, getFigure(startPosition));
        board.put(startPosition, EmptyFigure.EMPTY_FIGURE);
    }

    public void shootArrow(Position startPosition, Position arrowDstPosition) throws IllegalMoveException{
        Figure arrow = new Amazon(startPosition, PlayerID.NONE);
        if (!arrow.canMoveTo(arrowDstPosition, this)){
            throw new IllegalMoveException("deplacment non autorisé");
        }
        board.put(arrowDstPosition, ArrowFigure.ARROW_FIGURE);
    }

    public void fill(FigureGenerator generator){

    }

    @Override
    public Iterator<Position> positionIterator() {
        return null;
    }

    @Override
    public Iterator<Figure> iterator() {
        return null;
    }
}
