package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.Test;

public class ParseServiceImplTest {
    private static final String VALID_TEST_DATA = "s,apple,100";
    private static final String NOT_VALID_TEST_DATA = "s,apple,-100";

    @Test
    public void parse_Ok() {
        ParseService parseService = new ParseServiceImpl();
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(FruitTransaction.Operation
                .SUPPLY);
        expected.setFruit("apple");
        expected.setQuantity(100);
        FruitTransaction actual = parseService.parse(VALID_TEST_DATA);
        assertEquals(expected,actual);
    }

    @Test
    public void parse_NotValidData_NotOk() {
        ParseService parseService = new ParseServiceImpl();
        assertThrows(RuntimeException.class,() ->
                parseService.parse(NOT_VALID_TEST_DATA));
    }
}
