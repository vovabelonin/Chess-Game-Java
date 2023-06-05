package ChessModel.Pieces;

import ChessModel.Board.Grid;

import java.util.Vector;

public class Pawn extends Piece {
    private boolean firstMove;

    public Pawn() {
        firstMove = true;
    }

    public void move(int x, int y) {
        positions.setCoords(x, y);
        firstMove = false;
    }

    public boolean canMove(int x, int y, int player, Grid[][] g) {
        //white pawn
        if (player % 2 == 0) {
            if (firstMove) {
                if (positions.getX() == x && positions.getY() - 2 == y && !g[x][y].occupied)
                    return true;
                else if (positions.getX() == x && positions.getY() - 1 == y && !g[x][y].occupied)
                    return true;
                else if (positions.getX() - 1 == x && positions.getY() - 1 == y && g[x][y].occupied && g[x][y].color.equals("black"))
                    return true;
                else if (positions.getX() + 1 == x && positions.getY() - 1 == y && g[x][y].occupied && g[x][y].color.equals("black"))
                    return true;
                else
                    return false;
            } else {
                if (positions.getX() == x && positions.getY() - 1 == y && !g[x][y].occupied)
                    return true;
                else if (positions.getX() - 1 == x && positions.getY() - 1 == y && g[x][y].occupied && g[x][y].color.equals("black"))
                    return true;
                else if (positions.getX() + 1 == x && positions.getY() - 1 == y && g[x][y].occupied && g[x][y].color.equals("black"))
                    return true;
                else
                    return false;
            }
        }
        //black pawn
        else if (player % 2 != 0) {
            if (firstMove) {
                if (positions.getX() == x && positions.getY() + 2 == y && !g[x][y].occupied)
                    return true;
                else if (positions.getX() == x && positions.getY() + 1 == y && !g[x][y].occupied)
                    return true;
                else if (positions.getX() - 1 == x && positions.getY() + 1 == y && g[x][y].occupied && g[x][y].color.equals("white"))
                    return true;
                else if (positions.getX() + 1 == x && positions.getY() + 1 == y && g[x][y].occupied && g[x][y].color.equals("white"))
                    return true;
                else
                    return false;
            } else {
                if (positions.getX() == x && positions.getY() + 1 == y && !g[x][y].occupied)
                    return true;
                else if (positions.getX() - 1 == x && positions.getY() + 1 == y && g[x][y].occupied && g[x][y].color.equals("white"))
                    return true;
                else if (positions.getX() + 1 == x && positions.getY() + 1 == y && g[x][y].occupied && g[x][y].color.equals("white"))
                    return true;
                else
                    return false;
            }
        }

        return false;
    }

    @Override
    public void specialMove(String c) {
        if (c.equals("white")) {
            if (positions.getY() == 6)
                firstMove = true;
        } else {
            if (positions.getY() == 1)
                firstMove = true;
        }

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
