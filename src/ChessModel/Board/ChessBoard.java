package ChessModel.Board;

import ChessModel.Player;
import ChessModel.Pieces.Piece;

public interface ChessBoard {
    public Piece getPieces(Player player, char name, int index);

    public void moveSelectedPiece(int newX, int newY);

    public void undo();

    public void forfeit();

    public void printGrid();
}
