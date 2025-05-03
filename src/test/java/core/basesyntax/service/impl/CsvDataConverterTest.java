package core.basesyntax.service.impl;

import static core.basesyntax.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import core.basesyntax.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvDataConverterTest {
    private static final String HEADER_CSV = "type,fruit,quantity";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int QUANTITY_20 = 20;
    private static final int QUANTITY_100 = 100;
    private static final String B_BANANA_20 = "b,banana,20";
    private static final String B_APPLE_100 = "b,apple,100";
    private static final String S_BANANA_100 = "s,banana,100";
    private static final String FRUIT_INVALID_TYPE = "x,banana,100";
    private static final String FRUIT_INVALID_QUANTITY = "s,banana,qwe";
    private static final String FRUIT_NO_TYPE = "apple,20";
    private static final String FRUIT_NO_FRUIT = "b,55";
    private static final String FRUIT_NO_QUANTITY = "b,apple";
    private DataConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new CsvDataConverter();
    }

    @Test
    public void convertToTransaction_emptyInputList_notOk() {
        List<String> actual = new ArrayList<>();
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_nullInputList_notOk() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(null));
    }

    @Test
    public void convertToTransaction_onlyHeaderInputList_notOk() {
        List<String> actual = new ArrayList<>(List.of(HEADER_CSV));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_dataLineWithTwoFields_notOk() {
        List<String> actual = new ArrayList<>(List.of(HEADER_CSV, FRUIT_NO_QUANTITY));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_validData_ok() {
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(BALANCE);
        transaction1.setFruit(BANANA);
        transaction1.setQuantity(QUANTITY_20);
        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(BALANCE);
        transaction2.setFruit(APPLE);
        transaction2.setQuantity(QUANTITY_100);
        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(SUPPLY);
        transaction3.setFruit(BANANA);
        transaction3.setQuantity(QUANTITY_100);
        List<String> actual = new ArrayList<>(List.of(
                HEADER_CSV,
                B_BANANA_20,
                B_APPLE_100,
                S_BANANA_100
        ));
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                transaction1,
                transaction2,
                transaction3
        ));
        assertIterableEquals(expected, converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_typeDataAbsent_notOk() {
        List<String> actual = new ArrayList<>(List.of(
                HEADER_CSV,
                FRUIT_NO_TYPE
        ));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_fruitDataAbsent_notOk() {
        List<String> actual = new ArrayList<>(List.of(
                HEADER_CSV,
                FRUIT_NO_FRUIT
        ));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_quantityDataAbsent_notOk() {
        List<String> actual = new ArrayList<>(List.of(
                HEADER_CSV,
                FRUIT_NO_QUANTITY
        ));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_typeDataInvalid_notOk() {
        List<String> actual = new ArrayList<>(List.of(
                HEADER_CSV,
                FRUIT_INVALID_TYPE
        ));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_quantityIsNotANumber_notOk() {
        List<String> actual = new ArrayList<>(List.of(
                HEADER_CSV,
                FRUIT_INVALID_QUANTITY
        ));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }
}
