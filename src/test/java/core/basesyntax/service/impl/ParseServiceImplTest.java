package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static final String VALID_DATA = "type,fruit,quantity" + System.lineSeparator()
            + "b,apple,20";
    private static final String NOT_VALID_DATA = "type,fruit,quantity" + System.lineSeparator()
            + "n,badword,20";
    private static final String EMPTY_DATA = "";
    private static final String NULL_DATA = null;
    private static final int INDEX_OF_MAP = 0;
    private static final int VALID_QUANTITY = 20;
    private static ParseService parseService;
    private static List<FruitTransaction> fruitTransactionsList;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        parseService = new ParseServiceImpl();
        fruitTransactionsList = new ArrayList<>();
        fruitTransaction = new FruitTransaction(VALID_QUANTITY,
                FruitTransaction.Operation.BALANCE, "apple");
        fruitTransactionsList.add(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void parse_notValidData_notOk() {
        parseService.parse(NOT_VALID_DATA);
        fail("Expected " + RuntimeException.class.getName() + " to be thrown for not valid data: "
                + NOT_VALID_DATA + ", but it's wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void parse_emptyData_notOk() {
        parseService.parse(EMPTY_DATA);
        fail("Expected " + RuntimeException.class.getName() + " to be thrown for empty data: "
                + EMPTY_DATA + ", but it's wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void parse_nullData_notOk() {
        parseService.parse(NULL_DATA);
        fail("Expected " + RuntimeException.class.getName() + " to be thrown for null data: "
                + NULL_DATA + ", but it's wasn't");
    }

    @Test
    public void parse_ok() {
        List<FruitTransaction> parsed = parseService.parse(VALID_DATA);
        assertEquals("Expected: " + true + " but was: "
                + false, parsed.get(INDEX_OF_MAP).getFruit(),
                fruitTransactionsList.get(INDEX_OF_MAP).getFruit());
        assertEquals("Expected: " + true + " but was: "
                + false, parsed.get(INDEX_OF_MAP).getQuantity(),
                fruitTransactionsList.get(INDEX_OF_MAP).getQuantity());
        assertEquals("Expected: " + true + " but was: "
                + false, parsed.get(INDEX_OF_MAP).getOperation(),
                fruitTransactionsList.get(INDEX_OF_MAP).getOperation());
    }
}
