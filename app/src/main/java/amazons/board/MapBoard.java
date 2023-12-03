package amazons.board;

import amazons.IllegalMoveException;
import amazons.figures.EmptyFigure;
import amazons.figures.Figure;

import java.util.HashMap;
import java.util.Map;

import static amazons.figures.ArrowFigure.ARROW_FIGURE;
import static amazons.figures.EmptyFigure.EMPTY_FIGURE;

public class MapBoard implements Board{
    private Map <Position, Figure> positionFigureMap;

    public MapBoard(Map<Position, Figure> positionFigureMap) {
        this.positionFigureMap = new HashMap<>();
        this.positionFigureMap = positionFigureMap;
    }

    @Override
    public void setFigure(Position position, Figure figure) {
        positionFigureMap.put(position,figure);
    }

    @Override
    public Figure getFigure(Position position) {
        return positionFigureMap.get(position);
    }

    @Override
    public boolean isEmpty(Position position) {
        return this.getFigure(position) == EmptyFigure.EMPTY_FIGURE;
    }

    @Override
    public boolean isOutOfBoard(Position position) {
        return ! positionFigureMap.containsKey(position);
    }

    @Override
    public void moveFigure(Position startPosition, Position dstPosition) throws IllegalMoveException {
        if(this.getFigure(startPosition)== ARROW_FIGURE ||this.getFigure(startPosition)== EMPTY_FIGURE){throw new IllegalMoveException("Illegal move from " + startPosition + " to " + dstPosition);}
        else{
            if (this.getFigure(startPosition).canMoveTo(dstPosition, this)){this.getFigure(startPosition).moveTo(dstPosition, this);
                this.setFigure(startPosition, EMPTY_FIGURE);}
            else { throw new IllegalMoveException("Illegal move from " + startPosition + " to " + dstPosition);}
        }

    }

    @Override
    public void shootArrow(Position startPosition, Position arrowDstPosition) throws IllegalMoveException {
            Figure figureShootArrow = this.getFigure(startPosition);
            if (figureShootArrow == EMPTY_FIGURE || figureShootArrow == ARROW_FIGURE) {
                throw new IllegalMoveException("You cannot shoot an arrow from " + startPosition);
            } else {
                if (figureShootArrow.canMoveTo(arrowDstPosition, this)) {
                    this.setFigure(arrowDstPosition, ARROW_FIGURE);
                } else {
                    throw new IllegalMoveException("Illegal arrow shot from " + startPosition + " to " + arrowDstPosition);
                }
            }
        }


    @Override
    public void fill(FigureGenerator generator) {

    }
}
