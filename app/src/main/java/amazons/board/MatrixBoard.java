package amazons.board;

import amazons.IllegalMoveException;
import amazons.figures.Amazon;
import amazons.figures.ArrowFigure;
import amazons.figures.EmptyFigure;
import amazons.figures.Figure;
import amazons.player.PlayerID;
import amazons.figures.MovableFigure;
import java.util.Iterator;
public class MatrixBoard implements Board {
    private Figure[][] board;
    private final int numberOfColumns;
    private final int numberofRows;

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberofRows;
    }

    public MatrixBoard(int numberOfColumns, int numberOfRows) {
        this.numberofRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.board = new Figure[numberOfRows][numberOfColumns];
        fill(new EmptyFigureGenerator());
    }

    public void setFigure(Position position, Figure figure) {
        this.board[position.getY()][position.getX()] = figure;
    }

    public Figure getFigure(Position position) {
        return board[position.getY()][position.getX()];
    }

    public boolean isEmpty(Position position) {
        return this.board[position.getY()][position.getX()] == EmptyFigure.EMPTY_FIGURE;
    }

    public boolean isOutOfBoard(Position position) {
        return position.getX() < 0 || position.getY() < 0 || position.getY() >= this.board.length || position.getX() >= this.board[0].length;
    }

    @Override
    public void moveFigure(Position startPosition, Position dstPosition) throws IllegalMoveException {
        if (getFigure(startPosition) == EmptyFigure.EMPTY_FIGURE || getFigure(startPosition) == ArrowFigure.ARROW_FIGURE || !getFigure(startPosition).canMoveTo(dstPosition, this)) {
            throw new IllegalMoveException("deplacement non autorisé");
        }
        getFigure(startPosition).moveTo(dstPosition, this);
        this.setFigure(startPosition, EmptyFigure.EMPTY_FIGURE);
    }

    public void shootArrow(Position startPosition, Position arrowDstPosition) throws IllegalMoveException {
        Figure arrow = new Amazon(startPosition, PlayerID.NONE);
        if (!arrow.canMoveTo(arrowDstPosition, this)) {
            throw new IllegalMoveException("deplacment non autorisé");
        }
        this.setFigure(arrowDstPosition, ArrowFigure.ARROW_FIGURE);
    }

    public void fill(FigureGenerator generator) {
        for (int column = 0; column < this.numberOfColumns; column++) {
            for (int row = 0; row < numberofRows; row++) {
                Position p = new Position(column, row);
                setFigure(p, generator.nextFigure(p));
            }
        }
    }

    public Iterator<Figure> iterator() {
        return new MatrixIterator<>(numberOfColumns, numberofRows, board);
    }

    public Iterator<Position> positionIterator() {
        return new PositionIterator(numberOfColumns, numberofRows);
    }


    public String toString() {
        String result = "";
        int numJ = -1;

        result += "+";
        for (int col = 0; col < numberOfColumns; col++) {
            result += "----+";
        }
        result += "\n";

        for (int row = 0; row < numberofRows; row++) {
            result += "| ";
            for (int col = 0; col < numberOfColumns; col++) {
                Figure figure = getFigure(new Position(col, row));
                if (figure instanceof Amazon) {
                    Amazon amazon = (Amazon) figure;
                    if (amazon.getPlayerID() == PlayerID.PLAYER_ZERO) {
                        numJ = 0;
                    }
                    if (amazon.getPlayerID() == PlayerID.PLAYER_ONE) {
                        numJ = 1;
                    }
                    result += "A" + String.valueOf(numJ) + " | ";
                } else if (figure instanceof ArrowFigure) {
                    result += "XX | ";
                } else {
                    result += "   | ";
                }
            }
            result += row + "\n";

            result += "+";
            for (int col = 0; col < numberOfColumns; col++) {
                result += "----+";
            }
            result += "\n";
        }
        result += "  ";
        for (int col = 0; col < numberOfColumns; col++) {
            result += col + "    ";
        }
        result += "     ";
        result = result.trim();
        result += "  ";
        return result;
    }
}