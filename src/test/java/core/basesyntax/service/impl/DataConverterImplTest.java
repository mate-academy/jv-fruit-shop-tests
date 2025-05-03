package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.validation.DataValidator;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataValidator dataValidator;
    private DataConverter dataConverter;

    @BeforeEach
    public void setUp() {
        dataValidator = new DataValidator();
        dataConverter = new DataConverterImpl(dataValidator);
    }

    @Test
    public void returnsFruitTransactions_convertToTransaction_Ok() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,10",
                "p,orange,5"
        );
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputData);

        Assertions.assertNotNull(transactions);
        Assertions.assertEquals(3, transactions.size());

        Assertions.assertEquals(Operation.BALANCE, transactions.get(0).getOperation());
        Assertions.assertEquals("banana", transactions.get(0).getFruit());
        Assertions.assertEquals(20, transactions.get(0).getQuantity());

        Assertions.assertEquals(Operation.SUPPLY, transactions.get(1).getOperation());
        Assertions.assertEquals("apple", transactions.get(1).getFruit());
        Assertions.assertEquals(10, transactions.get(1).getQuantity());

        Assertions.assertEquals(Operation.PURCHASE, transactions.get(2).getOperation());
        Assertions.assertEquals("orange", transactions.get(2).getFruit());
        Assertions.assertEquals(5, transactions.get(2).getQuantity());
    }

    @Test
    public void returnsEmptyList_convertToTransaction_Ok() {
        List<String> inputData = Arrays.asList("type,fruit,quantity");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputData);

        Assertions.assertNotNull(transactions);
        Assertions.assertTrue(transactions.isEmpty());
    }

    @Test
    public void throwsException_invalidFormat_convertToTransaction_Ok() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana",
                "s,apple,10"
        );

        Assertions.assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputData));
    }

    @Test
    public void handlesInvalidOperationCode_shouldThrowException() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "x,banana,20"
        );

        Assertions.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(inputData));
    }

    @Test
    public void handlesNegativeQuantity_shouldThrowException() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "s,apple,-5"
        );

        Assertions.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(inputData));
    }

    @Test
    public void handlesNullFruitName_shouldThrowException() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,,10"
        );

        Assertions.assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputData));
    }
}
