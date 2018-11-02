package com.webcheckers.model;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests the methods of the Row class
 * @author John Knecht V (jjk1492@rit.edu)
 */
@Tag("Model-Tier")
public class RowTest {

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


    @Test
    public void FillWhiteRowTest_OddRow(){
        final Row CuT = new Row(1);
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


        assertEquals(piece0, WHITE_PIECE);
        assertNull(piece1);
        assertEquals(piece2, WHITE_PIECE);
        assertNull(piece3);
        assertEquals(piece4, WHITE_PIECE);
        assertNull(piece5);
        assertEquals(piece6, WHITE_PIECE);
        assertNull(piece7);
    }

    @Test
    public void ValidateRowTest_EvenRow(){
            final Row CuT = new Row(4);
            CuT.validateRow();
            List<Space> validatedRow = CuT.getSpaces();

            Space space0 = validatedRow.get(0);
            Space space1 = validatedRow.get(1);
            Space space2 = validatedRow.get(2);
            Space space3 = validatedRow.get(3);
            Space space4 = validatedRow.get(4);
            Space space5 = validatedRow.get(5);
            Space space6 = validatedRow.get(6);
            Space space7 = validatedRow.get(7);

        assertFalse(space0.isValid(), "Every even number space of an even row shouldn't be droppable.");
        assertTrue(space1.isValid(), "Every odd number space of an even row should droppable.");
        assertFalse(space2.isValid(), "Every even number space of an even row shouldn't be droppable.");
        assertTrue(space3.isValid(), "Every odd number space of an even row should droppable.");
        assertFalse(space4.isValid(), "Every even number space of an even row shouldn't be droppable.");
        assertTrue(space5.isValid(), "Every odd number space of an even row should droppable.");
        assertFalse(space6.isValid(), "Every even number space of an even row shouldn't be droppable.");
        assertTrue(space7.isValid(), "Every odd number space of an even row should droppable.");
    }

    @Test
    public void ValidateRowTest_OddRow(){
        final Row CuT = new Row(3);
        CuT.validateRow();
        List<Space> validatedRow = CuT.getSpaces();

        Space space0 = validatedRow.get(0);
        Space space1 = validatedRow.get(1);
        Space space2 = validatedRow.get(2);
        Space space3 = validatedRow.get(3);
        Space space4 = validatedRow.get(4);
        Space space5 = validatedRow.get(5);
        Space space6 = validatedRow.get(6);
        Space space7 = validatedRow.get(7);

        assertTrue(space0.isValid(), "Every even number space of an odd row should droppable.");
        assertFalse(space1.isValid(), "Every odd number space of an odd row shouldn't be droppable.");
        assertTrue(space2.isValid(), "Every even number space of an odd row should droppable.");
        assertFalse(space3.isValid(), "Every odd number space of an odd row shouldn't be droppable.");
        assertTrue(space4.isValid(), "Every even number space of an odd row should droppable.");
        assertFalse(space5.isValid(), "Every odd number space of an odd row shouldn't be droppable.");
        assertTrue(space6.isValid(), "Every even number space of an odd row should droppable.");
        assertFalse(space7.isValid(), "Every odd number space of an odd row shouldn't be droppable.");
    }

    @Test
    public void EqualsTest(){
        final Row CuT = new Row(0);
        final Row CuT1 = new Row(1);
        final Space space = new Space(0);


        assertEquals(CuT, CuT);
        assertNotEquals(CuT, CuT1);
        assertNotEquals(CuT, null);
        assertNotEquals(CuT, space);
    }

    @Test
    public void HashCodeTest(){
            final Row CuT = new Row(0);
            int expectedHash = Objects.hash(CuT.getSpaces(), CuT.getIndex());
            int actualHash = CuT.hashCode();

            assertEquals( expectedHash, actualHash);
    }
}
