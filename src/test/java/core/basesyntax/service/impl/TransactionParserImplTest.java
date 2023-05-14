package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static final int INDEX_FIRST_LINE = 0;
    private static final String VALID_DATA_PATH = "src/test/java/resources/inputCorrectData.csv";
    private static final List<String> INVALID_OPERATION_IN_DATA = List.of("type,fruit,quantity\n"
            + "b,banana,20");
    private static final List<String> INVALID_DATA = List.of("type,fruit", "s,apple");
    private static TransactionParser parser;

    @BeforeClass
    public static void init() {
        parser = new TransactionParserImpl();
    }

    @Test
    public void parseList_Ok() {
        List<String> data = new FilerReadServiceImpl().readFromFile(VALID_DATA_PATH);
        String fruit = "banana";
        int quantity = 20;
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        List<FruitTransaction> actual = parser.parseList(data);
        assertEquals(fruit, actual.get(INDEX_FIRST_LINE).getFruit());
        assertEquals(quantity, actual.get(INDEX_FIRST_LINE).getQuantity());
        assertEquals(operation, actual.get(INDEX_FIRST_LINE).getOperation());
    }

    @Test
    public void parseList_WrongOperation_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> parser.parseList(INVALID_OPERATION_IN_DATA));
        String expectedMessage = "Invalid input data";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test(expected = RuntimeException.class)
    public void parseList_invalidData_notOk() {
        parser.parseList(INVALID_DATA);
    }

    @Test(expected = NullPointerException.class)
    public void parseList_nullData_notOk() {
        parser.parseList(null);
    }
}
