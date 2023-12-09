package amazons.board;

import java.util.Iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIterator<T> implements Iterator<T> {
    private final int numberOfRows;
    private final int numberOfColumns;
    private final T[][] board;
    private int currentRow;
    private int currentColumn;

    public MatrixIterator(int numberOfRows, int numberOfColumns, T[][] board) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.board = board;
        this.currentRow = 0;
        this.currentColumn = 0;
    }

    @Override
    public boolean hasNext() {
        return currentRow < numberOfRows && currentColumn < numberOfColumns;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the matrix");
        }

        T element = board[currentRow][currentColumn];
        currentColumn++;
        if (currentColumn >= numberOfColumns) {
            currentColumn = 0;
            currentRow++;
        }

        return element;
    }
}


