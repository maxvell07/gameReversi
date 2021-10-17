package com.example.gamereversi;

import com.example.gamereversi.model.ReversiGameModel;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

public class ReversiControllerTest {
    private final ReversiGameModel model = ReversiGameModel.getInstance();

    @Test
    public void testIsPossibleToFlipTrue() {
        Boolean obtainedResult = model.isPossibleToFlip(4, 5, 0,-1);
        Assertions.assertEquals(true, obtainedResult);
    }
    @Test
    public void testIsPossibleToFlipFalse() {
        Boolean obtainedResult = model.isPossibleToFlip(4, 5, 1,-1);
        Assertions.assertEquals(false, obtainedResult);
    }
    @Test
    public void testIsLegalMoveTrue() {
        Boolean obtainedResult = model.isLegalMove(2, 3);
        Assertions.assertEquals(true, obtainedResult);
    }
    @Test
    public void testIsLegalMoveFalse() {
        Boolean obtainedResult = model.isLegalMove(0, 0);
        Assertions.assertEquals(false, obtainedResult);
    }
}