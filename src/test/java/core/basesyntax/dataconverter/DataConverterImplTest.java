package core.basesyntax.dataconverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static final List<String> INPUT_CONTENT = Arrays.asList(
            "type,fruit,quantity",
            "b,banana,20",
            "s,apple,100"
    );
    private static final List<String> INVALID_QUANTITY = Arrays.asList(
            "type,fruit,quantity",
            "b,banana,invalid"
    );
    private static final FruitTransaction TRANSACTION_1 =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
    private static final FruitTransaction TRANSACTION_2 =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100);
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convert_validInput_returnsTransactions() {
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(INPUT_CONTENT);

        List<FruitTransaction> expectedTransactions = Arrays.asList(
                TRANSACTION_1,
                TRANSACTION_2
        );

        assertEquals(expectedTransactions.size(), transactions.size(),
                "Transaction list size mismatch");

        for (int i = 0; i < transactions.size(); i++) {
            assertEquals(expectedTransactions.get(i), transactions.get(i),
                    "Mismatch at transaction index " + i);
        }
    }

    @Test
    void convert_invalidQuantity_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(INVALID_QUANTITY),
                "Expected IllegalArgumentException when quantity is invalid");
    }
}
