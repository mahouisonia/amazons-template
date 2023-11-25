package amazons.board;

import amazons.figures.EmptyFigure;
import amazons.figures.Figure;

public class MatrixBoard implements Board{
    private final Figure[][] board;
    public MatrixBoard(int numberOfColumns, int numberOfRows) {
        this.board = new Figure[numberOfColumns][numberOfColumns];
        for (int i = 0; i < numberOfColumns; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                board[i][j] = EmptyFigure.EMTPY_FIGURE;

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
        if(board[position.getX()][position.getY()] == EmptyFigure.EMTPY_FIGURE){
            return true;
        }
         return false;
    }

    @Override
    public boolean isOutOfBoard(Position position) {
        return false;
    }
}
