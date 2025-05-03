package core.basesyntax.service;

import static core.basesyntax.service.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.service.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.service.FruitTransaction.Operation.RETURN;
import static core.basesyntax.service.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static final List<String> correctStringList = List.of("type,fruit,quantity",
            "b,banana,20", "b,apple,1000", "s,banana,100", "p,banana,13", "r,apple,10");
    private static final List<String> emptyList = List.of("");
    private static final List<String> noFruitsList = List.of("type,fruit,quantity");
    private static DataConverter dataConverter;

    @BeforeAll
    public static void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransactions_Ok() {
        final List<FruitTransaction> convertedTransactionsList =
                dataConverter.convertToTransactions(correctStringList);
        final FruitTransaction.Operation balanceBanana =
                convertedTransactionsList.get(0).getOperation();
        final FruitTransaction.Operation balanceApple =
                convertedTransactionsList.get(1).getOperation();
        final FruitTransaction.Operation supplyBanana =
                convertedTransactionsList.get(2).getOperation();
        final FruitTransaction.Operation purchaseBanana =
                convertedTransactionsList.get(3).getOperation();
        final FruitTransaction.Operation returnApple =
                convertedTransactionsList.get(4).getOperation();
        final String fruitOne = convertedTransactionsList.get(0).getFruit();
        final String fruitTwo = convertedTransactionsList.get(1).getFruit();
        final String fruitThree = convertedTransactionsList.get(2).getFruit();
        final String fruitFour = convertedTransactionsList.get(3).getFruit();
        final String fruitFive = convertedTransactionsList.get(4).getFruit();
        final int quantityOne = convertedTransactionsList.get(0).getQuantity();
        final int quantityTwo = convertedTransactionsList.get(1).getQuantity();
        final int quantityThree = convertedTransactionsList.get(2).getQuantity();
        final int quantityFour = convertedTransactionsList.get(3).getQuantity();
        final int quantityFive = convertedTransactionsList.get(4).getQuantity();
        assertEquals(BALANCE, balanceBanana);
        assertEquals(BALANCE, balanceApple);
        assertEquals(SUPPLY, supplyBanana);
        assertEquals(PURCHASE, purchaseBanana);
        assertEquals(RETURN, returnApple);
        assertEquals("banana", fruitOne);
        assertEquals("apple", fruitTwo);
        assertEquals("banana", fruitThree);
        assertEquals("banana", fruitFour);
        assertEquals("apple", fruitFive);
        assertEquals(20, quantityOne);
        assertEquals(1000, quantityTwo);
        assertEquals(100, quantityThree);
        assertEquals(13, quantityFour);
        assertEquals(10, quantityFive);
    }

    @Test
    void convertToTransactionsWithEmptyList_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactions(emptyList));
        String expectedMessage = "No Transactions in file";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void convertToTransactionsWithoutTransactions_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactions(noFruitsList));
        String expectedMessage = "No Transactions in file";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
