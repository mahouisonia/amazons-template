package amazons.board;

import java.util.Iterator;

public class PositionIterator implements Iterator<Position> {
    private int numberOfRows;
    private int numberOfColumns;
    private int currentRow;
    private int currentColumn;



    public PositionIterator(int numberOfColumns, int numberOfRows) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.currentRow = 0;
        this.currentColumn = 0;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    @Override
    public boolean hasNext() {
        return currentRow < numberOfRows && currentColumn < numberOfColumns;
    }

    @Override
    public Position next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more positions in the matrix");
        }

        Position position = new Position(currentColumn, currentRow);
        currentColumn++;

        if (currentColumn >= numberOfColumns) {
            currentRow++;
            currentColumn = 0;
        }

        return position;
    }
}

