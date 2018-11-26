package com.webcheckers.model;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

//import java.util.List;
//import java.util.Objects;
//
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//
//
/**
 * Tests the methods of the Row class
 * @author John Knecht V (jjk1492@rit.edu)
 */
@Tag("Model-Tier")
public class RowTest {

    @Test
    void testValid(){
        int index = 5;
        List<Space> spaces = new LinkedList<>();
        for(int i = 0; i < 8; i ++) {
            spaces.add(new Space(i));
        }
        Row row = new Row(index, spaces);

        int actualIndex = row.getIndex();

        assertEquals(index, actualIndex);

        assertNotNull(row.iterator());
        }

}
