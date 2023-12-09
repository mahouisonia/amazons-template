package amazons.board;

import java.util.Iterator;

public class MatrixIterator<T> implements Iterator<T> {
    private int numberOfRows;
    private int numberOfColumns;
    private T[][] matrix;
    private int currentRow;
    private int currentColumn;

    public MatrixIterator(int numberOfColumns, int numberOfRows, T[][] matrix) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.matrix = matrix;
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
            throw new IllegalStateException("No more elements in the matrix");
        }

        T element = matrix[currentRow][currentColumn];
        currentColumn++;

        if (currentColumn >= numberOfColumns) {
            currentRow++;
            currentColumn = 0;
        }

        return element;
    }
}
