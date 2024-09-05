package core.basesyntax.service.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final List<String> VALID_INPUT_REPORT = Arrays.asList(
            "type,fruit,quantity",
            "b,banana,20",
            "s,apple,10"
    );
    private static final List<String> INVALID_LINE_FORMAT_REPORT = Arrays.asList(
            "type,fruit,quantity",
            "b,banana",
            "s,apple,10"
    );
    private static final List<String> INVALID_QUANTITY_FORMAT = Arrays.asList(
            "type,fruit,quantity",
            "b,banana,abc",
            "s,apple,10"
    );
    private static final List<String> INVALID_OPERATION_CODE = Arrays.asList(
            "type,fruit,quantity",
            "x,banana,20",
            "s,apple,10"
    );
    private static final List<FruitTransaction> EXPECTED_RESULTS = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10));
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convertToTransaction_validInput_ok() {
        List<FruitTransaction> transactions = dataConverter
                .convertToTransaction(VALID_INPUT_REPORT);

        Assertions.assertEquals(EXPECTED_RESULTS.size(), transactions.size(),
                "Expected the size of the transaction list to be "
                        + EXPECTED_RESULTS.size()
                        + " but got " + transactions.size()
                        + ". Check the input data for discrepancies.");

        for (int i = 0; i < EXPECTED_RESULTS.size(); i++) {
            Assertions.assertEquals(EXPECTED_RESULTS.get(i), transactions.get(i),
                    "At index " + i + ", expected transaction "
                            + EXPECTED_RESULTS.get(i)
                            + " but got " + transactions.get(i)
                            + ". Verify that the conversion logic is correct.");
        }
    }

    @Test
    public void convertToTransaction_emptyInput_notOk() {
        List<String> emptyInputReport = Collections.emptyList();

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(emptyInputReport));
    }

    @Test
    public void convertToTransaction_nullInput_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(null));
    }

    @Test
    public void convertToTransaction_invalidLineFormat_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(INVALID_LINE_FORMAT_REPORT));
    }

    @Test
    public void convertToTransaction_invalidQuantityFormat_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(INVALID_QUANTITY_FORMAT));
    }

    @Test
    public void convertToTransaction_invalidOperationCode_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(INVALID_OPERATION_CODE));
    }
}
