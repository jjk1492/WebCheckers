package model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Color;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Row;
import com.webcheckers.model.Space;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests the methods of the Row class
 */
@Tag("Model-Tier")
public class RowTester {

    @Test
    public void ConstructorTest_Index(){
        final int ROW_INDEX = 0;
        final Row CuT = new Row(ROW_INDEX);

        int rowIndex = CuT.getIndex();
        assertEquals(ROW_INDEX, rowIndex);
    }



    @Test
    public void ConstructorTest_Spaces(){
        final Row CuT = new Row(0);
        final int NUM_SPACES = 8;

        int numberOfSpaces = CuT.getSpaces().size();
        assertEquals(NUM_SPACES, numberOfSpaces);

    }

    @Test
    public void FillRedRowTest_EvenRow(){
        final Row CuT = new Row(0);
        Piece RED_PIECE = new Piece(Color.RED, Piece.Type.SINGLE);
        CuT.fillRedRow();
        List<Space> filledRow = CuT.getSpaces();

        Piece piece0 = filledRow.get(0).getPiece();
        Piece piece1 = filledRow.get(1).getPiece();
        Piece piece2 = filledRow.get(2).getPiece();
        Piece piece3 = filledRow.get(3).getPiece();
        Piece piece4 = filledRow.get(4).getPiece();
        Piece piece5 = filledRow.get(5).getPiece();
        Piece piece6 = filledRow.get(6).getPiece();
        Piece piece7 = filledRow.get(7).getPiece();


        assertNull(piece0);
        assertEquals(piece1, RED_PIECE);
        assertNull(piece2);
        assertEquals(piece3, RED_PIECE);
        assertNull(piece4);
        assertEquals(piece5, RED_PIECE);
        assertNull(piece6);
        assertEquals(piece7, RED_PIECE);
    }

    @Test
    public void FillRedRowTest_OddRow(){
        final Row CuT = new Row(1);
        Piece RED_PIECE = new Piece(Color.RED, Piece.Type.SINGLE);
        CuT.fillRedRow();
        List<Space> filledRow = CuT.getSpaces();

        Piece piece0 = filledRow.get(0).getPiece();
        Piece piece1 = filledRow.get(1).getPiece();
        Piece piece2 = filledRow.get(2).getPiece();
        Piece piece3 = filledRow.get(3).getPiece();
        Piece piece4 = filledRow.get(4).getPiece();
        Piece piece5 = filledRow.get(5).getPiece();
        Piece piece6 = filledRow.get(6).getPiece();
        Piece piece7 = filledRow.get(7).getPiece();


        assertEquals(piece0, RED_PIECE);
        assertNull(piece1);
        assertEquals(piece2, RED_PIECE);
        assertNull(piece3);
        assertEquals(piece4, RED_PIECE);
        assertNull(piece5);
        assertEquals(piece6, RED_PIECE);
        assertNull(piece7);
    }


    @Test
    public void FillWhiteRowTest_EvenRow(){
        final Row CuT = new Row(0);
        Piece WHITE_PIECE = new Piece(Color.WHITE, Piece.Type.SINGLE);
        CuT.fillWhiteRow();
        List<Space> filledRow = CuT.getSpaces();

        Piece piece0 = filledRow.get(0).getPiece();
        Piece piece1 = filledRow.get(1).getPiece();
        Piece piece2 = filledRow.get(2).getPiece();
        Piece piece3 = filledRow.get(3).getPiece();
        Piece piece4 = filledRow.get(4).getPiece();
        Piece piece5 = filledRow.get(5).getPiece();
        Piece piece6 = filledRow.get(6).getPiece();
        Piece piece7 = filledRow.get(7).getPiece();


        assertNull(piece0);
        assertEquals(piece1, WHITE_PIECE);
        assertNull(piece2);
        assertEquals(piece3, WHITE_PIECE);
        assertNull(piece4);
        assertEquals(piece5, WHITE_PIECE);
        assertNull(piece6);
        assertEquals(piece7, WHITE_PIECE);
    }
}
