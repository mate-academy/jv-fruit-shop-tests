package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static TransactionParserService transactionParserService;

    @BeforeClass
    public static void setUp() {
        transactionParserService = new TransactionParserServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void parse_inputListNull_notOk() {
        transactionParserService.parse(null);
    }

    @Test (expected = RuntimeException.class)
    public void parse_notDefaultTitle_notOk() {
        List<String> input = List.of("type,,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");
        transactionParserService.parse(input);
    }

    @Test
    public void parse_allRowsValid_ok() {
        List<String> input = List.of("type,fruit,quantity",
                "b,orange,44",
                "b,banana,20",
                "s,orange,75");
        List<FruitTransaction> fruitTransactionsExpected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 44),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana",20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 75)
        );
        List<FruitTransaction> parsedFruitTransactionsActual =
                transactionParserService.parse(input);
        for (int i = 0; i < parsedFruitTransactionsActual.size(); i++) {
            assertEquals(fruitTransactionsExpected.get(i).getOperation(),
                    parsedFruitTransactionsActual.get(i).getOperation());
            assertEquals(fruitTransactionsExpected.get(i).getFruit(),
                    parsedFruitTransactionsActual.get(i).getFruit());
            assertEquals(fruitTransactionsExpected.get(i).getQuantity(),
                    parsedFruitTransactionsActual.get(i).getQuantity());
        }
    }

    @Test (expected = RuntimeException.class)
    public void parse_invalidRow_notOk() {
        List<String> input = List.of("type,fruit,quantity",
                "b,123,44",
                "b,banana,20",
                "s,orange,75");
        transactionParserService.parse(input);
    }

    @Test (expected = RuntimeException.class)
    public void parse_nullQuantity_notOk() {
        List<String> input = List.of("type,fruit,quantity",
                "p,null,null");
        transactionParserService.parse(input);
    }

    @Test (expected = RuntimeException.class)
    public void parse_emptyValue_notOk() {
        List<String> input = List.of("type,fruit,quantity",
                "p,null,");
        transactionParserService.parse(input);
    }

    @Test (expected = RuntimeException.class)
    public void parse_invalidQuantity_notOk() {
        List<String> input = List.of("type,fruit,quantity",
                "p,null,123l3");
        transactionParserService.parse(input);
    }
}
