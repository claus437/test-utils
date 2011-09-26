package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.table.tableassertionrunnersupport.ChessBoard;
import dk.fujitsu.utils.test.table.tableassertionrunnersupport.ChessPiece;
import dk.fujitsu.utils.test.table.tableassertionrunnersupport.IllegalPositionException;
import dk.fujitsu.utils.test.table.tableassertionrunnersupport.Position;
import org.junit.Assert;
import org.junit.Test;

public class TableAssertionRunnerTest {
    private DataBase db = new DataBase("dk/fujitsu/utils/test/table/tableassertionrunnersupport/chess.txt");

    @Test
    public void testExceptionAssertion() throws Throwable {
        TableAssertionRunner<Position, IllegalPositionException> runner;

        runner = new TableAssertionRunner<Position, IllegalPositionException>(
            db, Position.class, "positions", IllegalPositionException.class, "positions_exp") {
            @Override
            IllegalPositionException execute(Position object) {
                ChessBoard board;

                board = new ChessBoard();
                board.getChessPiece(object);

                return null;
            }

            @Override
            public void done(int row, IllegalPositionException expected, IllegalPositionException actual) {
                super.done(row, actual, expected);

                Assert.assertEquals(expected.getMessage(), actual.getMessage());
            }
        };

        runner.run();
    }

    @Test
    public void testExceptionValidPositions() throws Throwable {
        TableAssertionRunner<Position, ChessPiece> runner;

        runner = new TableAssertionRunner<Position, ChessPiece>(db, Position.class, "valid-positions", ChessPiece.class, "valid-positions_exp") {

            @Override
            ChessPiece execute(Position object) {
                ChessBoard board;

                board = new ChessBoard();
                return board.getChessPiece(object);
            }

            @Override
            public void done(int row, ChessPiece expected, ChessPiece actual) {
                super.done(row, expected, actual);

                Assert.assertEquals(expected, actual);
            }
        };

        runner.run();
    }
}