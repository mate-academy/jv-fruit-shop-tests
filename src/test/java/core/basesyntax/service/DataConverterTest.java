package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.converter.DataConverter;
import core.basesyntax.service.converter.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String FRUIT_BANANA = "banana";
    private static final String INCORRECT_OPERATION = "t, banana, -1";
    private static final String INCORRECT_LENGTH = "b,bana,10,5";
    private static final String NEGATIVE_QUANTITY = "b,bana,-1";
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convertTransaction_listNull_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> dataConverter.convertFruitTransactions(null),
                "List transaction is null");
    }

    @Test
    public void convertOperation_incorrectOperation_notOk() {
        List<String> incorrectOperation = List.of(HEADER, INCORRECT_OPERATION);

        Assertions.assertThrows(IllegalStateException.class,
                () -> dataConverter.convertFruitTransactions(incorrectOperation),
                "Unexpected operation value");
    }

    @Test
    public void checkValidationSlitString_incorrectLength_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> dataConverter.convertFruitTransactions(List.of(HEADER, INCORRECT_LENGTH)),
                "Split string != 3");
    }

    @Test
    public void checkValidationSlitString_negativeQuantity_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> dataConverter.convertFruitTransactions(List.of(HEADER, NEGATIVE_QUANTITY)),
                "Quantity negative value");
    }

    @Test
    public void convertFruitTransactions_correct_ok() {
        List<String> strings = List.of(HEADER, "b,banana,10");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT_BANANA, 10));
        List<FruitTransaction> fruitTransactions = dataConverter.convertFruitTransactions(strings);
        Assertions.assertEquals(expected, fruitTransactions);

    }

    @Test
    public void convertFruitTransactions_correctList_ok() {
        List<FruitTransaction> expectedFruitTransactionList = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, FRUIT_BANANA, 0),
                new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY, FRUIT_BANANA, 20),
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, FRUIT_BANANA, 13),
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN, FRUIT_BANANA, 10));

        List<String> listTransactions = List.of("type,fruit,quantity",
                "b,banana,0", "s,banana,20", "p,banana,13",
                "r,banana,10");

        List<FruitTransaction> fruitTransactions
                = dataConverter.convertFruitTransactions(listTransactions);
        Assertions.assertEquals(expectedFruitTransactionList, fruitTransactions);
    }
}
