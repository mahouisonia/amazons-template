package amazons.board;

import javafx.scene.input.DataFormat;

import java.io.Serializable;
import java.util.Objects;

//TODO complete the code this class and its documentation

public class Position implements Serializable {
    public static final DataFormat POSITION_FORMAT = new DataFormat("amazons.position");

    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    //TODO
    public int getX() {return this.x;}
    public int getY() {return this.y;}


    public boolean isOutOfBounds(int numberOfColumns, int numberOfRows){
        return this.x < 0 || this.y < 0 || this.x >= numberOfColumns || this.y >= numberOfRows;
    }
    public Position next(CardinalDirection direction) {
        int newX = this.x + direction.deltaColumn;
        int newY = this.y + direction.deltaRow;
        return new Position(newX, newY);
    }

    public CardinalDirection getDirection(Position destPosition){
        return CardinalDirection.getDirection(x,y, destPosition.x, destPosition.y);
    }
    @Override
    public String toString(){
        return "("+this.x+","+this.y+")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Position position = (Position) other;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
