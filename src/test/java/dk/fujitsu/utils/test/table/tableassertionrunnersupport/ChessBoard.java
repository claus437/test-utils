package dk.fujitsu.utils.test.table.tableassertionrunnersupport;

import dk.fujitsu.utils.test.table.tableassertionrunnersupport.chessmen.Bishop;
import dk.fujitsu.utils.test.table.tableassertionrunnersupport.chessmen.King;
import dk.fujitsu.utils.test.table.tableassertionrunnersupport.chessmen.Knight;
import dk.fujitsu.utils.test.table.tableassertionrunnersupport.chessmen.Pawn;
import dk.fujitsu.utils.test.table.tableassertionrunnersupport.chessmen.Queen;
import dk.fujitsu.utils.test.table.tableassertionrunnersupport.chessmen.Rook;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-09-11
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class ChessBoard {
    private ChessPiece[][] board = new ChessPiece[8][8];

    public ChessPiece getChessPiece(Position position) {
        if (position.getX() < 'A' || position.getX() > 'H') {
            throw new IllegalPositionException(position.getX() + " is not a valid horizontal position");
        }

        if (position.getY() < 1 || position.getY() > 8) {
            throw new IllegalPositionException(position.getY() + " is not a valid vertical position");
        }

        return null;
    }

    public void lineUp() {
        clear();

        lineUp(7, 6, Color.BLACK);
        lineUp(0, 1, Color.WHITE);
    }

    private void lineUp(int baseLine, int frontLine, Color color) {
        board[0][baseLine] = new Rook(color);
        board[1][baseLine] = new Knight(color);
        board[2][baseLine] = new Bishop(color);
        board[3][baseLine] = new Queen(color);
        board[4][baseLine] = new King(color);
        board[5][baseLine] = new Bishop(color);
        board[6][baseLine] = new Knight(color);
        board[7][baseLine] = new Rook(color);

        for (int i = 0; i < 8; i++) {
            board[i][frontLine] = new Pawn(color);
        }
    }

    private void clear() {
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = null;
            }
        }
    }
}
