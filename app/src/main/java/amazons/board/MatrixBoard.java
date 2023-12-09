package amazons.board;
import amazons.figures.Amazon;
import amazons.figures.ArrowFigure;
import amazons.figures.EmptyFigure;
import amazons.figures.Figure;
import amazons.IllegalMoveException;
import amazons.player.PlayerID;
import amazons.figures.MovableFigure;
import java.util.Random;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class MatrixBoard implements Board {
    private Figure[][] board;
    private final int numberOfColumns;
    private final int numberofRows;

    public MatrixBoard(int numberOfColumns, int numberOfRows){
        this.numberofRows=numberOfRows;
        this.numberOfColumns=numberOfColumns;

        this.board = new Figure[numberOfRows][numberOfColumns];

        fill(new EmptyFigureGenerator());

    }

    @Override
    public void setFigure(Position position, Figure figure) {
        this.board[position.getY()][position.getX()]= figure;
    }

    @Override
    public Figure getFigure(Position position) {
        int y = position.getY();
        int x = position.getX();

        return board[y][x];


    }

    @Override
    public boolean isEmpty(Position position) {
        return this.board[position.getY()][position.getX()] == EmptyFigure.EMPTY_FIGURE;
    }

    @Override
    public boolean isOutOfBoard(Position position) {
        return position.getX()<0 || position.getY()<0 || position.getY()>=this.board.length || position.getX()>=this.board[0].length;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberofRows() {
        return numberofRows;
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

        figureToMove.setPosition(dstPosition);
    }

    @Override
    public void shootArrow(Position startPosition, Position arrowDstPosition) throws IllegalMoveException {
        Figure arrow = new Amazon(startPosition, PlayerID.NONE);
        if (!arrow.canMoveTo(arrowDstPosition, this)) {
            throw new IllegalMoveException("Illegal arrow shooting");
        }
        this.setFigure(arrowDstPosition, ArrowFigure.ARROW_FIGURE);


    }

    public void fill (FigureGenerator x){
        for(int i=0; i<this.numberOfColumns;i++){
            for(int j=0; j<numberofRows;j++){
                Position p = new Position(i, j);
                this.setFigure(p, x.nextFigure(p));
            }
        }
    }

    @Override
    public MatrixIterator<Figure> iterator() {
        return new MatrixIterator<>(numberOfColumns, numberofRows, board);
    }

    @Override
    public Iterator<Position> positionIterator() {
        return new PositionIterator(numberOfColumns, numberofRows);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        SeparateLine(numberOfColumns,stringBuilder);
        stringBuilder.append("\n");
        for (int row = 0; row < numberofRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                Figure currentFigure = board[row][col];

                if (currentFigure instanceof Amazon) {
                    Amazon amazon = (Amazon) currentFigure;
                    stringBuilder.append("| A").append(amazon.getPlayerID().ordinal()).append(" ");

                } else if (currentFigure == ArrowFigure.ARROW_FIGURE) {
                    stringBuilder.append("| XX ");

                } else {
                    stringBuilder.append("|    "); // Assuming EmptyFigure.EMPTY_FIGURE is represented by "--"
                }
            }
            stringBuilder.append("|  " + row+"\n");
            SeparateLine(numberOfColumns,stringBuilder);
            stringBuilder.append("\n");
        }
        for(int i=0;i<numberOfColumns;i++){
            stringBuilder.append("  " + i + "  ");
        }

        return stringBuilder.toString();
    }

    private void SeparateLine(int numcolom,StringBuilder s){
        s.append("+");
        for(int i=0;i<numcolom;i++){
            s.append("----+");
        }
    }

    public static void main(String[] args) {
        MatrixBoard board = new MatrixBoard(5, 5);

        // For testing purposes, assuming you have Amazon and ArrowFigure classes
        Amazon amazon1 = new Amazon(new Position(1, 1), PlayerID.PLAYER_ZERO);
        Amazon amazon2 = new Amazon(new Position(3, 2), PlayerID.PLAYER_ONE);
        Position pbase = new Position(2,2);
        Position pdest = new Position(3,3);




        EmptyFigureGenerator generator = new EmptyFigureGenerator();

        /*Random random = new Random();
        RandomFigureGenerator randomFigureGenerator = new RandomFigureGenerator(
                random, Arrays.asList(new ArrowFigure(), new ArrowFigure()),
                new PositionIterator(5, 5)
        );*/


        board.fill(generator);

        board.setFigure(new Position(1, 1), amazon1);
        board.setFigure(new Position(3, 2), amazon2);
        board.shootArrow(pbase,pdest);

        String boardGame = board.toString();
        System.out.println(boardGame);
        System.out.println("fqddqh");
    }


}
