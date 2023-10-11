package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.Operation;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.storage.FruitStorage;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class TransactionParserImplTest {
    private TransactionParser transactionParser = new TransactionParserImpl();

    @BeforeEach
    public void cleanStorage() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void parseTest_OK() {
        String data = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13";

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(Operation.PURCHASE, "banana", 13));

        List<FruitTransaction> actual = transactionParser.parse(data);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTest_NullData_notOK() {
        assertThrows(RuntimeException.class, () -> {
            transactionParser.parse(null);
        });
    }

    @Test
    public void parseTest_EmptyData_notOK() {
        assertThrows(RuntimeException.class, () -> {
            transactionParser.parse("");
        });
    }

    @Test
    public void parseTest_InvalidData_OK() {
        String data = "type,fruit,quantity" + System.lineSeparator();

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(Operation.PURCHASE, "banana", 13));

        List<FruitTransaction> actual = transactionParser.parse(data);
        assertNotEquals(expected, actual);
    }
}
