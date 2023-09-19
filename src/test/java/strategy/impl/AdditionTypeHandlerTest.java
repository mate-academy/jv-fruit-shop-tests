package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class AdditionTypeHandlerTest {
    private static final AdditionTypeHandler additionTypeHandler = new AdditionTypeHandler();

    @Test
    public void edgeInt_Ok() {
        assertEquals(-1,
                additionTypeHandler.getNewQuantity(Integer.MAX_VALUE, Integer.MIN_VALUE),
                "Addition result is incorrect");
    }

    @Test
    public void goodInt_Ok() {
        int before = 5;
        int after = 10;
        int expectedResult = before + after;

        int result = additionTypeHandler.getNewQuantity(before, after);

        assertEquals(expectedResult, result, "Addition result is incorrect");
    }
}
