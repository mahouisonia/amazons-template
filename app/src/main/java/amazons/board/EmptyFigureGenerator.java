package amazons.board;

import amazons.figures.Figure;

import static amazons.figures.EmptyFigure.EMPTY_FIGURE;

public class EmptyFigureGenerator implements FigureGenerator {
    @Override
    public Figure nextFigure(Position position) {
        return EMPTY_FIGURE;
    }
}
