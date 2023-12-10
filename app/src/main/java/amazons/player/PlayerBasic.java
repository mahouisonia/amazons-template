package amazons.player;

import amazons.board.CardinalDirection;
import amazons.board.Position;
import amazons.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    public class PlayerBasic implements Player{
        private final Random random = new Random();
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
            Position srtPosition = RandomUtil.getRandomElement(random, playerPositions);
            Position dstPosition = RandomUtil.getRandomElement(random, adjacentPositions(srtPosition));
            Position arrowPosition = RandomUtil.getRandomElement(random, adjacentPositions(dstPosition));
            while (adjacentPositions(srtPosition).isEmpty() || adjacentPositions(dstPosition).isEmpty()) {
                srtPosition = RandomUtil.getRandomElement(random, playerPositions);
                dstPosition = RandomUtil.getRandomElement(random, adjacentPositions(srtPosition));
                arrowPosition = RandomUtil.getRandomElement(random, adjacentPositions(dstPosition));
            }
            occupiedPositions.remove(srtPosition);
            occupiedPositions.add(dstPosition);
            occupiedPositions.add(arrowPosition);
            playerPositions.remove(srtPosition);
            playerPositions.add(dstPosition);
            return new Move(srtPosition, dstPosition, arrowPosition);
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

        public List<Position> adjacentPositions(Position position) {
            List<Position> adjacentPositions = new ArrayList<>();
            for (CardinalDirection direction : CardinalDirection.values()) {
                Position nextPosition = position.next(direction);
                if (!nextPosition.isOutOfBounds(boardWidth, boardHeight) && !occupiedPositions.contains(nextPosition)) {
                    adjacentPositions.add(nextPosition);
                }
            }
            return adjacentPositions;
        }

        public void applyMove(Move opponentMove) {
            occupiedPositions.remove(opponentMove.getAmazonStartPosition());
            occupiedPositions.add(opponentMove.getAmazonDstPosition());
            occupiedPositions.add(opponentMove.getArrowDestPosition());
        }
    }


