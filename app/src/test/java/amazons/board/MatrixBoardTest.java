package amazons.board;


import amazons.IllegalMoveException;
import amazons.board.*;
import amazons.figures.Amazon;
import amazons.figures.Figure;
import amazons.figures.MovableFigure;

import amazons.player.PlayerID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static amazons.figures.ArrowFigure.ARROW_FIGURE;
import static amazons.figures.EmptyFigure.EMPTY_FIGURE;
import static org.assertj.core.api.Assertions.*;


class MatrixBoardTest {

    private MatrixBoard testBoard;
    private final Random random = new Random();
    private final int NUMBER_OF_COLUMNS = 4;
    private final int NUMBER_OF_ROWS = 3;
    private Amazon amazon00Player0;
    private final Position position00 = new Position(0,0);
    private Amazon amazon01Player0;
    private final Position position01 = new Position(0,1);

    private Amazon amazon32Player0;
    private final Position position32 = new Position(3,2);

    private Amazon amazon11Player1;
    private final Position position11 = new Position(1,1);

    private Amazon amazon30Player1;
    private final Position position30 = new Position(3,0);

    private Amazon amazon12Player1;
    private final Position position12 = new Position(1,2);



    @BeforeEach
    void setAmazons(){
        amazon00Player0 = new Amazon(position00, PlayerID.PLAYER_ZERO);
        amazon01Player0 = new Amazon(position01, PlayerID.PLAYER_ZERO);
        amazon32Player0 = new Amazon(position32, PlayerID.PLAYER_ZERO);
        amazon11Player1 = new Amazon(position11, PlayerID.PLAYER_ONE);
        amazon30Player1 = new Amazon(position30, PlayerID.PLAYER_ONE);
        amazon12Player1 = new Amazon(position12, PlayerID.PLAYER_ONE);
    }

    @BeforeEach
    void setTestBoard(){
        testBoard = new MatrixBoard(NUMBER_OF_COLUMNS, NUMBER_OF_ROWS);
    }

    @Test
    void testGetSetFigure() {
        testBoard.setFigure(position00,amazon00Player0);
        assertThat(testBoard.getFigure(position00)).isSameAs(amazon00Player0);
        testBoard.setFigure(position00,EMPTY_FIGURE);
        assertThat(testBoard.getFigure(position00)).isSameAs(EMPTY_FIGURE);
        testBoard.setFigure(position12,ARROW_FIGURE);
        assertThat(testBoard.getFigure(position12)).isSameAs(ARROW_FIGURE);
    }

    @Test
    void testGetNumberOfRows() {
        MatrixBoard board  = new MatrixBoard(2,3);
        assertThat(board.getNumberOfRows()).isEqualTo(3);
    }

    @Test
    void testGetNumberOfColumns() {
        MatrixBoard board  = new MatrixBoard(2,3);
        assertThat(board.getNumberOfColumns()).isEqualTo(2);
    }

    @Test
    void testInitializeEmpty() {
        testBoard.fill(new EmptyFigureGenerator());
        assertThat(testBoard.getFigure(new Position(0,0))).isSameAs(EMPTY_FIGURE);
        assertThat(testBoard.getFigure(new Position(1,1))).isSameAs(EMPTY_FIGURE);
        assertThat(testBoard.getFigure(new Position(3,2))).isSameAs(EMPTY_FIGURE);
    }

    @Test
    void testMoveEmptyFigure() {
        testBoard.setFigure(position12,EMPTY_FIGURE);
        assertThatThrownBy(() -> testBoard.moveFigure(position12,position00))
                .isInstanceOf(IllegalMoveException.class);
    }

    @Test
    void testMoveArrowFigure() {
        testBoard.setFigure(position12,ARROW_FIGURE);
        assertThatThrownBy(() -> testBoard.moveFigure(position12,position00)).isInstanceOf(IllegalMoveException.class);
    }

    @Test
    void testMoveAmazonFigure() {
        testBoard.setFigure(position12,amazon12Player1);
        assertThatThrownBy(() -> testBoard.moveFigure(position12,position00)).isInstanceOf(IllegalMoveException.class);
        testBoard.setFigure(position00,amazon12Player1);
        try {
            testBoard.moveFigure(position00, position11);
        } catch(IllegalMoveException e) {
            assertThat(false).withFailMessage("move("+position00+","+position11+") should not throw exception").isTrue();
        }
        assertThat(testBoard.getFigure(position00)).isSameAs(EMPTY_FIGURE);
        assertThat(testBoard.getFigure(position11)).isSameAs(amazon12Player1);
    }

    @Test
    void testToString() {
        assertThat(testBoard.toString()).isEqualTo(
                """
                +----+----+----+----+
                |    |    |    |    | 0
                +----+----+----+----+
                |    |    |    |    | 1
                +----+----+----+----+
                |    |    |    |    | 2
                +----+----+----+----+
                  0    1    2    3\040\040"""
        );
        testBoard.setFigure(position30,amazon30Player1);
        testBoard.setFigure(position00,amazon00Player0);
        testBoard.setFigure(position11,ARROW_FIGURE);
        assertThat(testBoard.toString()).isEqualTo(
                """
                +----+----+----+----+
                | A0 |    |    | A1 | 0
                +----+----+----+----+
                |    | XX |    |    | 1
                +----+----+----+----+
                |    |    |    |    | 2
                +----+----+----+----+
                  0    1    2    3\040\040"""
        );
    }

    @Test
    void testPositionIterator() {
        int rowIndex = 0;
        int columnIndex = 0;
        Iterator<Position> positionIterator = testBoard.positionIterator();
        while(positionIterator.hasNext()){
            assertThat(columnIndex).withFailMessage("iterator should not have a next element")
                    .isLessThan(NUMBER_OF_COLUMNS);
            assertThat(rowIndex).withFailMessage("iterator should not have a next element")
                    .isLessThan(NUMBER_OF_ROWS);
            Position position = positionIterator.next();
            Position expectedPosition = new Position(columnIndex, rowIndex);
            assertThat(position).isEqualTo(expectedPosition);
            if(columnIndex < NUMBER_OF_COLUMNS - 1)
                columnIndex ++;
            else{
                columnIndex = 0;
                rowIndex++;
            }
        }
    }

    @Test
    void testIterator() {
        Iterator<Figure> iterator = testBoard.iterator();
        testBoard.setFigure(position00, amazon00Player0);
        testBoard.setFigure(position01, amazon01Player0);
        testBoard.setFigure(position32, amazon32Player0);
        testBoard.setFigure(position11,ARROW_FIGURE);
        testBoard.setFigure(position30, ARROW_FIGURE);
        List<Figure> figures = new ArrayList<>();
        int emptyFigureCount = 0;
        int arrowFigureCount = 0;
        int amazonFigureCount = 0;

        while(iterator.hasNext()){
            assertThat(emptyFigureCount + arrowFigureCount + amazonFigureCount)
                    .isLessThanOrEqualTo(NUMBER_OF_COLUMNS * NUMBER_OF_ROWS);
            Figure figure = iterator.next();
            if(figure == EMPTY_FIGURE) emptyFigureCount++;
            else if (figure == ARROW_FIGURE) arrowFigureCount++;
            else {
                amazonFigureCount++;
                figures.add(figure);
            }
        }
        assertThat(emptyFigureCount).isEqualTo(NUMBER_OF_COLUMNS*NUMBER_OF_ROWS - 5);
        assertThat(arrowFigureCount).isEqualTo(2);
        assertThat(amazonFigureCount).isEqualTo(3);
        assertThat(figures).hasSize(3)
                .doesNotHaveDuplicates()
                .containsExactlyInAnyOrder(amazon00Player0, amazon01Player0, amazon32Player0);
    }
    @Test
    void testIsEmpty() {
        testBoard.setFigure(position32,EMPTY_FIGURE);
        assertThat(testBoard.isEmpty(position32)).isTrue();
    }

}