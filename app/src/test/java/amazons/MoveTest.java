package amazons;

import amazons.board.Position;
import amazons.player.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class MoveTest {

    private Move testMove;
    private final Position amazonStartPosition = new Position(0,0);
    private final Position amazonDstPosition = new Position(1,1);
    private final Position arrowDstPosition  = new Position(1,2);
    @BeforeEach
    void setTestMove(){
        testMove = new Move(amazonStartPosition, amazonDstPosition, arrowDstPosition);
    }
    @Test
    void testGetters() {
        assertThat(testMove.getAmazonStartPosition()).isEqualTo(amazonStartPosition);
        assertThat(testMove.getAmazonDstPosition()).isEqualTo(amazonDstPosition);
        assertThat(testMove.getArrowDestPosition()).isEqualTo(arrowDstPosition);
    }


}