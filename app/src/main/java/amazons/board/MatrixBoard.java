package amazons.board;

import amazons.figures.EmptyFigure;
import amazons.figures.Figure;

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
                board[i][j] = EmptyFigure.EMPTY_FIGURE;

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
        if(board[position.getX()][position.getY()] == EmptyFigure.EMPTY_FIGURE){
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
}
