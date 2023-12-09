package amazons.player;
import amazons.board.Position;
import java.util.Objects;

public class Move {

    public static final Move DUMMY_MOVE = new Move();

    private final Position amazonStartPosition;
    private final Position amazonDestPosition;
    private final Position arrowDestPosition;

    public Position getAmazonStartPosition() {
        return amazonStartPosition;
    }

    public Position getAmazonDestPosition() {
        return amazonDestPosition;
    }

    public Position getArrowDestPosition() {
        return arrowDestPosition;
    }

    private Move() {
        this.amazonStartPosition = null;
        this.amazonDestPosition = null;
        this.arrowDestPosition = null;
    }

    public Move(Position amazonStartPosition, Position amazonDestPosition, Position arrowDestPosition) {
        this.amazonStartPosition = amazonStartPosition;
        this.amazonDestPosition = amazonDestPosition;
        this.arrowDestPosition = arrowDestPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return Objects.equals(amazonStartPosition, move.amazonStartPosition) &&
                Objects.equals(amazonDestPosition, move.amazonDestPosition) &&
                Objects.equals(arrowDestPosition, move.arrowDestPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amazonStartPosition, amazonDestPosition, arrowDestPosition);
    }

    @Override
    public String toString() {
        return amazonStartPosition.toString() + ':' + amazonDestPosition.toString() + "->" + arrowDestPosition.toString();
    }

    public Position getAmazonDstPosition() {
        return amazonDestPosition;
    }
}
