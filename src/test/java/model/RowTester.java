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
}
