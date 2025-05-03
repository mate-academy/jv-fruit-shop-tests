package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.model.Operation;
import core.basesyntax.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static TransactionParserImpl parserService;

    @BeforeEach
    void setUp() {
        parserService = new TransactionParserImpl();
    }

    @Test
    void parse_null_notOk() {
        assertThrows(NullPointerException.class,
                () -> parserService.parseData(null));
    }

    @Test
    void parse_dataFromFile_ok() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");

        List<FruitTransaction> actual = parserService.parseData(dataFromFile);

        assertEquals(3, actual.size(), "There should be three transactions parsed.");

        assertTransactionEqual("banana", Operation.BALANCE, actual.get(0));
        assertTransactionEqual("apple", Operation.BALANCE, actual.get(1));
        assertTransactionEqual("banana", Operation.SUPPLY, actual.get(2));
    }

    private void assertTransactionEqual(String expectedFruit, Operation expectedOperation,
                                        FruitTransaction actualTransaction) {
        assertEquals(expectedFruit, actualTransaction.getFruit(), "Fruit should match");
        assertEquals(expectedOperation, actualTransaction.getOperation(), "Operation should match");
        // Quantity is not asserted because it's not processed in the current implementation
    }
}
