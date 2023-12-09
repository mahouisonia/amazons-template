package amazons.board;

import amazons.figures.Amazon;
import amazons.figures.EmptyFigure;
import amazons.figures.Figure;
import amazons.figures.MovableFigure;
import amazons.player.PlayerID;

import java.util.*;

public class RandomFigureGenerator implements FigureGenerator {
    private Random random;
    private List<MovableFigure> figures;
    private Iterator<Position> positionIterator;

    public RandomFigureGenerator(Random random, List<MovableFigure> figures, Iterator<Position> positionIterator) {
        this.random = random;
        this.figures = figures;
        this.positionIterator = positionIterator;
    }

    private List<Amazon> queenList() {
        List<Amazon> myList = new ArrayList<>();
        PositionIterator z = (PositionIterator) positionIterator;

        while (myList.size() < 8) {
            int x = random.nextInt(z.getNumberOfColumns());
            int y = random.nextInt(z.getNumberOfRows());
            Position newPosition = new Position(x, y);

            boolean positionExists = false;
            for (Amazon existingQueen : myList) {
                if (existingQueen.getPosition().equals(newPosition)) {
                    positionExists = true;
                    break;
                }
            }

            if (!positionExists) {
                PlayerID player = (myList.size() <= 3) ? PlayerID.PLAYER_ZERO : PlayerID.PLAYER_ONE;
                Amazon queen = new Amazon(newPosition, player);
                myList.add(queen);
            }
        }
        return myList;
    }


//    public Figure nextFigure(Position p) {
//
//
//            Amazon tempQueen = new Amazon(p, PlayerID.PLAYER_ZERO);
//            Amazon tempQueen2 = new Amazon(p, PlayerID.PLAYER_ONE);
//
//            boolean foundInList = false;
//            List<Amazon> myList = queenList();
//            for (Amazon queen : myList) {
//                if (queen.getPosition().equals(tempQueen.getPosition()) || queen.getPosition().equals(tempQueen2.getPosition())) {
//                    return queen;
//                }
//            }
//
//            if (!foundInList) {
//                return EmptyFigure.EMPTY_FIGURE;
//            }
//
//        return EmptyFigure.EMPTY_FIGURE;
//    }


    @Override
    public Figure nextFigure(Position p) {
        for (MovableFigure f : queenList()) {
            if (f.getPosition().equals(p)) {
                return (Figure) f;
            }
        }
        return EmptyFigure.EMPTY_FIGURE;
    }

}
