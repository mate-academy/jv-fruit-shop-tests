package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static TransactionParserService transactionParserService;
    private static List<String> input;

    @BeforeClass
    public static void setUp() {
        transactionParserService = new TransactionParserServiceImpl();
        input = new ArrayList<>();
    }

    @After
    public void afterEach() {
        input.clear();
    }

    @Test (expected = RuntimeException.class)
    public void parse_inputListNull_notOk() {
        transactionParserService.parse(null);
    }

    @Test (expected = RuntimeException.class)
    public void parse_notDefaultTitle_notOk() {
        input.add("type,,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        input.add("s,banana,100");
        input.add("p,banana,13");
        transactionParserService.parse(input);
    }

    @Test
    public void parse_allRowsValid_ok() {
        input.add("type,fruit,quantity");
        input.add("b,orange,44");
        input.add("b,banana,20");
        input.add("s,orange,75");
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
    public void parse_invalidFruitValue_notOk() {
        input.add("type,fruit,quantity");
        input.add("b,123,44");
        input.add("b,banana,20");
        input.add("s,orange,75");
        input.add("p,banana,13");
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
        input.add("type,fruit,quantity");
        input.add("p,null,");
        transactionParserService.parse(input);
    }

    @Test (expected = RuntimeException.class)
    public void parse_invalidQuantity_notOk() {
        input.add("type,fruit,quantity");
        input.add("p,null,123l3");
        transactionParserService.parse(input);
    }

    @Test (expected = RuntimeException.class)
    public void parse_invalidOperationType_notOk() {
        input.add("type,fruit,quantity");
        input.add("y,apple,123l3");
        transactionParserService.parse(input);
    }
}
