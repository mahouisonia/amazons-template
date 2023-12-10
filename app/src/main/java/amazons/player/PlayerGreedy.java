package amazons.player;

import amazons.board.CardinalDirection;
import amazons.board.Position;

import java.util.ArrayList;
import java.util.List;

public class PlayerGreedy implements Player{
    private PlayerID playerID;
    private List<Position> playerPositions = new ArrayList<>();
    private List<Position> occupiedPositions = new ArrayList<>();
    private int boardWidth;
    private int boardHeight;

    @Override
    public Move play(Move opponentMove) {
        if (opponentMove != Move.DUMMY_MOVE) {
            applyMove(opponentMove);
        }
        Move bestMove = playGreedy();
        occupiedPositions.remove(bestMove.getAmazonStartPosition());
        occupiedPositions.add(bestMove.getArrowDestPosition());
        occupiedPositions.add(bestMove.getAmazonDstPosition());
        playerPositions.remove(bestMove.getAmazonStartPosition());
        playerPositions.add(bestMove.getAmazonDstPosition());
        return bestMove;
    }

    @Override
    public void initialize(int boardHeight, int boardWidth, PlayerID playerID, List<Position>[] initialPositions) {
        this.playerID = playerID;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        for (List<Position> position : initialPositions) {
            occupiedPositions.addAll(position);
        }
        playerPositions.addAll(initialPositions[playerID.index]);
    }

    @Override
    public boolean isGUIControlled() {
        return false;
    }

    @Override
    public PlayerID getPlayerID() {
        return this.playerID;
    }

    public List<Position> possiblePositions(Position position) {
        List<Position> possiblePositions = new ArrayList<>();
        for (CardinalDirection direction : CardinalDirection.values()) {
            Position nextPosition = position.next(direction);
            while (!nextPosition.isOutOfBounds(boardWidth, boardHeight) && !occupiedPositions.contains(nextPosition)) {
                possiblePositions.add(nextPosition);
                nextPosition = nextPosition.next(direction);
            }
        }
        return possiblePositions;
    }

    public void applyMove(Move opponentMove) {
        occupiedPositions.remove(opponentMove.getAmazonStartPosition());
        occupiedPositions.add(opponentMove.getAmazonDstPosition());
        occupiedPositions.add(opponentMove.getArrowDestPosition());
    }

    private Move playGreedy() {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;

        for (Position srtPosition : playerPositions) {
            List<Position> possibleDstPositions = possiblePositions(srtPosition);

            for (Position dstPosition : possibleDstPositions) {
                List<Position> possibleArrowPositions = possiblePositions(dstPosition);

                for (Position arrowPosition : possibleArrowPositions) {
                    int playerScore = calculateScore(srtPosition, dstPosition, arrowPosition);
                    int opponentScore = calculateOpponentScore(srtPosition, dstPosition, arrowPosition);

                    int score = playerScore - opponentScore;

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Move(srtPosition, dstPosition, arrowPosition);
                    }
                }
            }
        }
        return bestMove;
    }

    public int calculateScore(Position srtPosition, Position dstPosition, Position arrowPosition) {
        int playerScore = 0;
        List<Position> updatedPlayerPositions = new ArrayList<>(playerPositions);
        updatedPlayerPositions.remove(srtPosition);
        updatedPlayerPositions.add(dstPosition);

        for (Position newPosition : possiblePositions(dstPosition)) {
            if (!updatedPlayerPositions.contains(newPosition)) {
                playerScore++;
            }
        }
        return playerScore;
    }

    public int calculateOpponentScore(Position srtPosition, Position dstPosition, Position arrowPosition) {
        int opponentScore = 0;
        List<Position> updatedOccupiedPositions = new ArrayList<>(occupiedPositions);
        updatedOccupiedPositions.remove(srtPosition);
        updatedOccupiedPositions.add(dstPosition);
        updatedOccupiedPositions.add(arrowPosition);

        for (Position opponentPosition : updatedOccupiedPositions) {
            for (Position newPosition : possiblePositions(opponentPosition)) {
                if (!updatedOccupiedPositions.contains(newPosition)) {
                    opponentScore++;
                }
            }
        }
        return opponentScore;
    }
}

