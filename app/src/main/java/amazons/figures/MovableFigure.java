package amazons.figures;

import amazons.board.Board;
import amazons.board.Position;
import amazons.player.PlayerID;

import java.util.List;

public abstract class MovableFigure {
    private Position position; // Adding a Position field

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    // The existing method to get accessible positions remains unchanged
    abstract public List<Position> getAccessiblePositions(Board board);
}
