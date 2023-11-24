package amazons.board;

import javafx.scene.input.DataFormat;

import java.io.Serializable;

//TODO complete the code this class and its documentation

public class Position implements Serializable {
    public static final DataFormat POSITION_FORMAT = new DataFormat("amazons.position");

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean isOutOfBounds(int numberOfColumns, int numberOfRows) {
        Position position = new Position(numberOfColumns - 1, numberOfRows - 1);
        if (this.getX() != position.getX() && this.getY() != position.getY())
            return true;
        return false;
    }

    public Position next(CardinalDirection direction) {
        int x1 = this.getX() + direction.deltaColumn;
        int y1 = this.getY() + direction.deltaRow;
        return new Position(x1, y1);
    }

    public CardinalDirection getDirection(Position destPosition) {
        return CardinalDirection.getDirection(x, y, destPosition.x, destPosition.y);
    }

    public String toString() {
        return "(" + this.getX() + "," + this.getY() + ")";
    }

    public boolean equals(Object other) {
        if (!(other instanceof Position)) {
            return false;  // L'objet n'est pas une instance de Position
        }

        Position pos = (Position) other;  // Cast seulement après avoir vérifié l'instance

        // Comparaison des attributs
        return this.getX() == pos.getX() && this.getY() == pos.getY();
    }
}

