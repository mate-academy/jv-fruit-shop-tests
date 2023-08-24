package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class AdditionTypeHandlerTest {
    private static final AdditionTypeHandler ADDITION_TYPE_HANDLER = new AdditionTypeHandler();

    @Test
    public void edgeInt_Ok() {
        assertEquals(-1,
                ADDITION_TYPE_HANDLER.getNewQuantity(Integer.MAX_VALUE, Integer.MIN_VALUE),
                "Addition result is incorrect");
    }

    @Test
    public void goodInt_Ok() {
        int before = 5;
        int after = 10;
        int expectedResult = before + after;

        int result = ADDITION_TYPE_HANDLER.getNewQuantity(before, after);

        assertEquals(expectedResult, result, "Addition result is incorrect");
    }
}
