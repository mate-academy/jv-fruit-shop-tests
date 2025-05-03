package core.basesyntax.dataconverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static final String INVALID_DATA_FILE = "src/test/resources/invalid_quantity.csv";
    private static final FruitTransaction TRANSACTION_1 =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
    private static final FruitTransaction TRANSACTION_2 =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100);
    private static final String VALID_DATA_FILE = "src/test/resources/valid_transactions.csv";
    private DataConverter dataConverter;
    private List<String> validContent;
    private List<String> invalidContent;

    @BeforeEach
    void setUp() throws IOException {
        dataConverter = new DataConverterImpl();
        validContent = Files.readAllLines(Path.of(VALID_DATA_FILE));
        invalidContent = Files.readAllLines(Path.of(INVALID_DATA_FILE));
    }

    @Test
    void convert_validInput_returnsTransactions() {
        List<FruitTransaction> actualTransactions =
                dataConverter.convertToTransaction(validContent);

        List<FruitTransaction> expectedTransactions = Arrays.asList(
                TRANSACTION_1,
                TRANSACTION_2
        );

        assertEquals(validContent.size() - 1, actualTransactions.size(),
                "Transaction list size mismatch");

        for (int i = 0; i < actualTransactions.size(); i++) {
            assertEquals(expectedTransactions.get(i), actualTransactions.get(i),
                    "Mismatch at transaction index " + i);
        }
    }

    @Test
    void convert_invalidQuantity_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(invalidContent),
                "Expected IllegalArgumentException when quantity is invalid");
    }
}
