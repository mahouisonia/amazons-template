package amazons.board;

import amazons.board.FigureGenerator;
import amazons.board.Position;
import amazons.figures.EmptyFigure;
import amazons.figures.Figure;
import amazons.figures.MovableFigure;

import java.util.List;
import java.util.Random;
import java.util.Iterator;

public class RandomFigureGenerator implements FigureGenerator {
    private Random random;
    private List<MovableFigure> figures;
    private Iterator<Position> positionIterator;

    public RandomFigureGenerator(Random random, List<MovableFigure> figures, Iterator<Position> positionIterator) {
        this.random = random;
        this.figures = figures;
        this.positionIterator = positionIterator;
    }

    @Override
    public Figure nextFigure(Position position) {
        if (positionIterator.hasNext()) {
            MovableFigure randomFigure = getRandomFigure();
            randomFigure.setPosition(positionIterator.next());
            return (Figure) randomFigure;
        }
        return EmptyFigure.EMPTY_FIGURE;
    }

    private MovableFigure getRandomFigure() {
        int randomIndex = random.nextInt(figures.size());
        return figures.remove(randomIndex);
    }

}
