package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionConverterImplTest {
    private TransactionConverterImpl transactionConverter;

    @BeforeEach
    void setUp() {
        transactionConverter = new TransactionConverterImpl();
    }

    @Test
    void converter_ValidInput_Ok() {
        final List<String> inputRecords = List.of(
                "type,fruit,quantity",
                "b,banana,50",
                "p,apple,30",
                "s,banana,20"

        );

        FruitTransaction expectedFirstTransaction = new FruitTransaction();
        expectedFirstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        expectedFirstTransaction.setFruit("banana");
        expectedFirstTransaction.setQuantity(50);

        FruitTransaction expectedSecondTransaction = new FruitTransaction();
        expectedSecondTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        expectedSecondTransaction.setFruit("apple");
        expectedSecondTransaction.setQuantity(30);

        FruitTransaction expectedThirdTransaction = new FruitTransaction();
        expectedThirdTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        expectedThirdTransaction.setFruit("banana");
        expectedThirdTransaction.setQuantity(20);

        List<FruitTransaction> expectedTransactions = List.of(
                expectedFirstTransaction,
                expectedSecondTransaction,
                expectedThirdTransaction
        );

        List<FruitTransaction> actualTransactions = transactionConverter
                .convertToTransaction(inputRecords);

        Assertions.assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void converter_EmptyInput_Ok() {
        List<String> emptyInputRecords = Collections.emptyList();
        List<FruitTransaction> emptyFruitTransactions = transactionConverter
                .convertToTransaction(emptyInputRecords);
        Assertions.assertTrue(emptyFruitTransactions.isEmpty());
    }

    @Test
    void converter_UnknownOperation_NotOk() {
        List<String> unknownOperation = List.of(
                "type,fruit,quantity",
                "jj,banana,50"
        );
        Assertions.assertThrows(RuntimeException.class, () ->
                transactionConverter.convertToTransaction(unknownOperation));
    }

    @Test
    void converter_InvalidQuantity_NotOk() {
        List<String> invalidQuantity = List.of(
                "type,fruit,quantity",
                "b,banana,+?>"
        );
        Assertions.assertThrows(RuntimeException.class, () ->
                transactionConverter.convertToTransaction(invalidQuantity));
    }
}
