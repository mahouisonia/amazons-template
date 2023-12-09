package amazons.board;

import amazons.figures.EmptyFigure;
import amazons.figures.Figure;
import amazons.figures.MovableFigure;

import java.util.List;

public class PresetFigureGenerator implements FigureGenerator {
    private final List<MovableFigure> figures;

    public PresetFigureGenerator(List<MovableFigure> figures) {
        this.figures = figures;
    }

    @Override
    public Figure nextFigure(Position p) {
        for (MovableFigure f : figures) {
            if (f.getPosition().equals(p)) {
                return (Figure) f;
            }
        }
        return EmptyFigure.EMPTY_FIGURE;
    }
}
