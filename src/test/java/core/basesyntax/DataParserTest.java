package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParser;
import core.basesyntax.service.impl.DataParserImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataParserTest {
    private DataParser dataParser;

    @BeforeEach
    public void setUp() {
        dataParser = new DataParserImpl();
    }

    @Test
    void parseData_validData_ok() {
        List<String> validData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,50",
                "p,orange,30"
        );
        List<FruitTransaction> expectedTransactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 30)
        );

        List<FruitTransaction> actualTransactions = dataParser.parseData(validData);
        assertFruitTransactionsEqual(expectedTransactions, actualTransactions);
    }

    @Test
    void parseData_invalidRowFormat_notOk() {
        List<String> invalidData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dataParser.parseData(invalidData);
        });
    }

    @Test
    void parseData_invalidOperationCode_notOk() {
        List<String> invalidData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "x,apple,30"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dataParser.parseData(invalidData);
        });
    }

    private void assertFruitTransactionsEqual(List<FruitTransaction> expected,
                                              List<FruitTransaction> actual) {
        Assertions.assertEquals(expected.size(),
                actual.size(), "Lists have different sizes.");
        for (int i = 0; i < expected.size(); i++) {
            FruitTransaction expectedTransaction = expected.get(i);
            FruitTransaction actualTransaction = actual.get(i);
            Assertions.assertEquals(expectedTransaction.getOperation(),
                    actualTransaction.getOperation(),
                    "Operation mismatch at index " + i);
            Assertions.assertEquals(expectedTransaction.getFruit(),
                    actualTransaction.getFruit(),
                    "Fruit mismatch at index " + i);
            Assertions.assertEquals(expectedTransaction.getQuantity(),
                    actualTransaction.getQuantity(),
                    "Quantity mismatch at index " + i);
        }
    }
}
