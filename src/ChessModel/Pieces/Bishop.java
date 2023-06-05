package ChessModel.Pieces;

import ChessModel.Board.Grid;

import java.util.Vector;

public class Bishop extends Piece {
    @Override
    public void move(int x, int y) {
        positions.setCoords(x, y);
    }

    @Override
    public boolean canMove(int x, int y, int player, Grid[][] g) {
        if (positions.getX() == x || positions.getY() == y)
            return false;

        int direction = getDirection(x, y);
        Pair end = new Pair(x, y);
        Pair start = new Pair(positions.getX(), positions.getY());

        //white bishop
        if (player % 2 == 0) {
            return recurseCheck(start, end, "black", direction, g, 0);
        } else if (player % 2 != 0) {
            return recurseCheck(start, end, "white", direction, g, 0);
        }

        return true;
    }

    private int getDirection(int x, int y) {
        if (x > positions.getX()) {
            //down right
            if (y > positions.getY()) {
                return 2;
            }
            //up right
            else {
                return 1;
            }
        } else {
            //down left
            if (y > positions.getY()) {
                return 3;
            }
            //up left
            else {
                return 4;
            }
        }
    }

    private boolean recurseCheck(Pair s, Pair e, String opponent, int direction, Grid[][] g, int count) {
        if (s.equal(e) && !g[e.getX()][e.getY()].occupied)
            return true;
        else if (s.equal(e) && g[e.getX()][e.getY()].occupied && g[e.getX()][e.getY()].color.equals(opponent))
            return true;
        else if (g[s.getX()][s.getY()].occupied && count > 0)
            return false;
        else {
            if (direction == 1) {
                s.setCoords(s.getX() + 1, s.getY() - 1);
                if (s.getX() > e.getX() || s.getY() < e.getY())
                    return false;
                return recurseCheck(s, e, opponent, direction, g, 1);
            } else if (direction == 2) {
                s.setCoords(s.getX() + 1, s.getY() + 1);
                if (s.getX() > e.getX() || s.getY() > e.getY())
                    return false;
                return recurseCheck(s, e, opponent, direction, g, 1);
            } else if (direction == 3) {
                s.setCoords(s.getX() - 1, s.getY() + 1);
                if (s.getX() < e.getX() || s.getY() > e.getY())
                    return false;
                return recurseCheck(s, e, opponent, direction, g, 1);
            } else {
                s.setCoords(s.getX() - 1, s.getY() - 1);
                if (s.getX() < e.getX() || s.getY() < e.getY())
                    return false;
                return recurseCheck(s, e, opponent, direction, g, 1);
            }
        }
    }

    @Override
    public void specialMove(String c) {
    }

    @Override
    public Vector<Pair> addMoves(int player, Grid[][] g) {
        Vector<Pair> values = new Vector<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (canMove(i, j, player, g))
                    values.add(new Pair(i, j));
            }
        }

        return values;
    }
}
