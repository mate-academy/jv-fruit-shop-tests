package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserServiceImplTest {
    private static final List<String> DEFAULT_INPUT = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static final List<FruitTransaction> DEFAULT_RESULT = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));

    private static TransactionParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new FruitTransactionParserServiceImpl();
    }

    @Test
    public void parseDataFromList_validData_Ok() {
        List<FruitTransaction> actual = parserService.parseDataFromList(DEFAULT_INPUT);
        assertEquals(DEFAULT_RESULT, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataFromList_nullData_NotOk() {
        parserService.parseDataFromList(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataFromList_nonExistentOperation_NotOk() {
        List<String> nonExistentOperation = List.of("type,fruit,quantity",
                "k,banana,20");
        parserService.parseDataFromList(nonExistentOperation);
    }
}
