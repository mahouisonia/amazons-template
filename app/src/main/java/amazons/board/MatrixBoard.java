package amazons.board;

import amazons.IllegalMoveException;
import amazons.figures.EmptyFigure;
import amazons.figures.Figure;

import static amazons.figures.ArrowFigure.ARROW_FIGURE;
import static amazons.figures.EmptyFigure.EMPTY_FIGURE;

public class MatrixBoard implements Board{
    private final Figure[][] board;

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    private final int numberOfColumns;

    public int getNumberOfRows() {
        return numberOfRows;
    }

    private final int numberOfRows;
    public MatrixBoard( int numberOfColumns, int numberOfRows) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.board = new Figure[numberOfColumns][numberOfRows];
        for (int i = 0; i < numberOfColumns; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                board[i][j] = EMPTY_FIGURE;

            }
        }
    }

    @Override
    public void setFigure(Position position, Figure figure) {
        board[position.getX()][position.getY()] = figure;
    }

    @Override
    public Figure getFigure(Position position) {
        return board[position.getX()][position.getY()];
    }

    @Override
    public boolean isEmpty(Position position) {
        if(board[position.getX()][position.getY()] == EMPTY_FIGURE){
            return true;
        }
         return false;
    }

    @Override
    public boolean isOutOfBoard(Position position) {
        int numberOfColumns = board.length;
        int numberOfRows = board[0].length;
       return position.isOutOfBounds(numberOfColumns, numberOfRows);

    }

    @Override
    public void moveFigure(Position startPosition, Position dstPosition) throws IllegalMoveException {
        Figure figureToMove = this.getFigure(startPosition);

        if (figureToMove == ARROW_FIGURE || figureToMove == EMPTY_FIGURE) {
            throw new IllegalMoveException("Illegal move from " + startPosition + " to " + dstPosition);
        } else if (figureToMove.canMoveTo(dstPosition, this)) {
            figureToMove.moveTo(dstPosition, this);
            this.setFigure(startPosition, EMPTY_FIGURE);
        } else {
            throw new IllegalMoveException("Illegal move from " + startPosition + " to " + dstPosition);
        }
    }

    @Override
    public void shootArrow(Position startPosition, Position arrowDstPosition) throws IllegalMoveException {
        Figure figureShootArrow = this.getFigure(startPosition);

        if (figureShootArrow == EMPTY_FIGURE || figureShootArrow == ARROW_FIGURE) {
            throw new IllegalMoveException("You cannot shoot an arrow from " + startPosition);
        } else {
            if (figureShootArrow.canMoveTo(arrowDstPosition, this)) {
                this.setFigure(arrowDstPosition, ARROW_FIGURE);
            } else {
                throw new IllegalMoveException("Illegal arrow shot from " + startPosition + " to " + arrowDstPosition);
            }
        }
    }

}
