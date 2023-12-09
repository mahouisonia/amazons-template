package amazons.board;

import amazons.figures.Amazon;
import amazons.figures.ArrowFigure;
import amazons.figures.EmptyFigure;
import amazons.figures.Figure;
import amazons.IllegalMoveException;
import amazons.player.PlayerID;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapBoard implements Board {
    private Map<Position, Figure> board;
    private final int numberOfColumns;
    private final int numberofRows;

    public MapBoard(int numberOfColumns, int numberOfRows) {
        this.numberofRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;

        this.board = new HashMap<>();

        fill(new EmptyFigureGenerator());
    }

    @Override
    public void setFigure(Position position, Figure figure) {
        board.put(position, figure);
    }

    @Override
    public Figure getFigure(Position position) {
        return board.getOrDefault(position, EmptyFigure.EMPTY_FIGURE);
    }

    @Override
    public boolean isEmpty(Position position) {
        return getFigure(position) == EmptyFigure.EMPTY_FIGURE;
    }

    @Override
    public boolean isOutOfBoard(Position position) {
        return position.getX() < 0 || position.getY() < 0 || position.getY() >= this.numberofRows || position.getX() >= this.numberOfColumns;
    }

    @Override
    public Iterator<Figure> iterator() {
        return null;
    }

    @Override
    public Iterator<Position> positionIterator() {
        return null;
    }

    @Override
    public void moveFigure(Position startPosition, Position dstPosition) throws IllegalMoveException {
        Figure figureToMove = getFigure(startPosition);
        if (figureToMove == EmptyFigure.EMPTY_FIGURE) {
            throw new IllegalMoveException("No figure to move at the starting position");
        }

        if (!figureToMove.canMoveTo(dstPosition, this)) {
            throw new IllegalMoveException("Illegal move for the figure");
        }

        setFigure(dstPosition, figureToMove);
        setFigure(startPosition, EmptyFigure.EMPTY_FIGURE);

        figureToMove.setPosition(dstPosition);    }

    @Override
    public void shootArrow(Position startPosition, Position arrowDstPosition) throws IllegalMoveException {
        Figure arrow = new Amazon(startPosition, PlayerID.NONE);
        if (!arrow.canMoveTo(arrowDstPosition, this)) {
            throw new IllegalMoveException("Illegal arrow shooting");
        }
        this.setFigure(arrowDstPosition, ArrowFigure.ARROW_FIGURE);
    }

    public void fill(FigureGenerator x) {
        for (int i = 0; i < this.numberOfColumns; i++) {
            for (int j = 0; j < this.numberofRows; j++) {
                Position p = new Position(i, j);
                setFigure(p, x.nextFigure(p));
            }
        }
    }
}
