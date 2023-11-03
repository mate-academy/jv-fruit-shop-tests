package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Test;

public class CsvReadDataParserImplTest {
    private static final List<String> VALID_INPUT = List.of("type,fruit,quantity",
                                                            "b,banana,20");
    private static final List<String> INVALID_INPUT = List.of("type,fruit,quantity",
                                                            "2 0,ban ana,b //qqq");

    private static final CsvReadDataParserImpl parser = new CsvReadDataParserImpl();

    @Test
    public void convertToFruitTransactionList_allValidConditions() {
        FruitTransaction expected = new FruitTransaction(
                                        FruitTransaction.OperationType.BALANCE, "banana", 20);
        FruitTransaction actual = parser.convertToFruitTransactionList(VALID_INPUT).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void convertToFruitTransactionList_nullInputValue_NotOk() {
        assertThrows(RuntimeException.class,
                () -> parser.convertToFruitTransactionList(null));
    }

    @Test
    public void convertToFruitTransactionList_invalidInputData_NotOk() {
        assertThrows(RuntimeException.class,
                () -> parser.convertToFruitTransactionList(INVALID_INPUT));
    }
}
