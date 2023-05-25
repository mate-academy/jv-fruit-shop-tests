package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.testservice.RandomDataGenerator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FruitTransactionParserImplTest {
    private static FruitTransactionParser fruitTransactionParser;
    private static RandomDataGenerator randomDataGenerator;
    private static final int NULL_LIST_SIZE = 0;

    @BeforeAll
    public static void init() {
        fruitTransactionParser = new FruitTransactionParserImpl();
        randomDataGenerator = new RandomDataGenerator();
    }

    @Test
    public void parseTransactions_returnValidData_Ok() {
        List<String> fileLines = new ArrayList<>();
        fileLines.add("b,apple,40");
        fileLines.add("s,apple,10");
        fileLines.add("b,banana,100");
        fileLines.add("r,apple,30");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "apple", 40));
        expected.add(new FruitTransaction(Operation.SUPPLY, "apple", 10));
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 100));
        expected.add(new FruitTransaction(Operation.RETURN, "apple", 30));
        List<FruitTransaction> actual = fruitTransactionParser.parseTransactions(fileLines);
        Assertions.assertEquals(expected.size(), actual.size(),
                "The actual list size doesn't match the expected list size");
        for (int i = 0; i < expected.size(); i++) {
            FruitTransaction expectedTransaction = expected.get(i);
            FruitTransaction actualTransaction = actual.get(i);
            Assertions.assertEquals(expectedTransaction.getOperation(),
                    actualTransaction.getOperation(),
                    "The operation doesn't match for transaction at index " + i);
            Assertions.assertEquals(expectedTransaction.getFruit(), actualTransaction.getFruit(),
                    "The fruit doesn't match for transaction at index " + i);
            Assertions.assertEquals(expectedTransaction.getQuantity(),
                    actualTransaction.getQuantity(),
                    "The quantity doesn't match for transaction at index " + i);
        }
    }

    @Test
    public void parseTransactions_InvalidData_NotOk() {
        List<String> fileLines = new ArrayList<>();
        fileLines.add(randomDataGenerator.generateRandomData());
        fileLines.add(randomDataGenerator.generateRandomData());
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitTransactionParser.parseTransactions(fileLines),
                "RuntimeException expected");
    }

    @Test
    public void parseTransactions_parseEmptyList_Ok() {
        List<String> fileLines = new ArrayList<>();
        List<FruitTransaction> fruitTransactions
                = fruitTransactionParser.parseTransactions(fileLines);
        Assertions.assertEquals(NULL_LIST_SIZE, fruitTransactions.size(),
                "The empty parsed fruitTransactions list expected");
    }

}
