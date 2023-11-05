package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransactionOperation;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserServiceImplTest {
    private ParserServiceImpl parserService;

    @BeforeEach
    public void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void testValidStringsToFruitTransactions() {
        List<String> inputStrings = Arrays.asList(
                "b,apple,10",
                "s,banana,20",
                "p,orange,5",
                "r,kiwi,3"
        );

        List<FruitTransaction> expectedTransactions = Arrays.asList(
                createFruitTransaction(FruitTransactionOperation.BALANCE, "apple", 10),
                createFruitTransaction(FruitTransactionOperation.SUPPLY, "banana", 20),
                createFruitTransaction(FruitTransactionOperation.PURCHASE, "orange", 5),
                createFruitTransaction(FruitTransactionOperation.RETURN, "kiwi", 3)
        );

        List<FruitTransaction> result = parserService.stringsToFruitTransactions(inputStrings);

        assertEquals(expectedTransactions, result);
    }

    @Test
    public void testInvalidStringsToFruitTransactions() {
        List<String> inputStrings = Arrays.asList(
                "b,apple,10",
                "invalid_string",
                "p,orange,5",
                "r,kiwi,3"
        );

        assertThrows(RuntimeException.class, () ->
                parserService.stringsToFruitTransactions(inputStrings));
    }

    private FruitTransaction createFruitTransaction(FruitTransactionOperation operation,
                                                    String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
