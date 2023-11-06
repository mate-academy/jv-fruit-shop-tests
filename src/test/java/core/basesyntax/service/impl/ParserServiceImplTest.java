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
    private static final String BALANCE_OPERATION_CODE = "b";
    private static final String SUPPLY_OPERATION_CODE = "s";
    private static final String PURCHASE_OPERATION_CODE = "p";
    private static final String RETURN_OPERATION_CODE = "r";
    private ParserServiceImpl parserService;

    @BeforeEach
    public void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void testValidStringsToFruitTransactions() {
        List<String> inputStrings = Arrays.asList(
                BALANCE_OPERATION_CODE + ",apple,10",
                SUPPLY_OPERATION_CODE + ",banana,20",
                PURCHASE_OPERATION_CODE + ",orange,5",
                RETURN_OPERATION_CODE + ",kiwi,3"
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
    public void testInvalidStringToFruitTransactions() {
        List<String> inputStrings = Arrays.asList(
                BALANCE_OPERATION_CODE + ",apple,10",
                "invalid_string",
                PURCHASE_OPERATION_CODE + ",orange,5",
                RETURN_OPERATION_CODE + ",kiwi,3"
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
