package core.basesyntax.service.impl;

import static core.basesyntax.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import core.basesyntax.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CsvDataConverterTest {
    @Test
    public void convertToTransaction_emptyInputList_notOk() {
        DataConverter converter = new CsvDataConverter();
        List<String> actual = new ArrayList<>();
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_nullInputList_notOk() {
        DataConverter converter = new CsvDataConverter();
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(null));
    }

    @Test
    public void convertToTransaction_onlyHeaderInputList_notOk() {
        DataConverter converter = new CsvDataConverter();
        List<String> actual = new ArrayList<>(List.of("type,fruit,quantity"));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_dataLineWithTwoFields_notOk() {
        DataConverter converter = new CsvDataConverter();
        List<String> actual = new ArrayList<>(List.of("type,fruit,quantity", "b,apple"));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_validData_ok() {
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(BALANCE);
        transaction1.setFruit("banana");
        transaction1.setQuantity(20);
        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(BALANCE);
        transaction2.setFruit("apple");
        transaction2.setQuantity(100);
        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(SUPPLY);
        transaction3.setFruit("banana");
        transaction3.setQuantity(100);
        List<String> actual = new ArrayList<>(List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        ));
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                transaction1,
                transaction2,
                transaction3
        ));
        DataConverter converter = new CsvDataConverter();
        assertIterableEquals(expected, converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_typeDataAbsent_notOk() {
        DataConverter converter = new CsvDataConverter();
        List<String> actual = new ArrayList<>(List.of(
                "fruit,quantity",
                "banana,20",
                "apple,100"
        ));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_fruitDataAbsent_notOk() {
        DataConverter converter = new CsvDataConverter();
        List<String> actual = new ArrayList<>(List.of(
                "type,fruit,quantity",
                "b,20",
                "s,100"
        ));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_quantityDataAbsent_notOk() {
        DataConverter converter = new CsvDataConverter();
        List<String> actual = new ArrayList<>(List.of(
                "type,fruit,quantity",
                "b,banana",
                "s,apple"
        ));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_typeDataInvalid_notOk() {
        DataConverter converter = new CsvDataConverter();
        List<String> actual = new ArrayList<>(List.of(
                "type,fruit,quantity",
                "x,banana,20"
        ));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }

    @Test
    public void convertToTransaction_quantityIsNotANumber_notOk() {
        DataConverter converter = new CsvDataConverter();
        List<String> actual = new ArrayList<>(List.of(
                "type,fruit,quantity",
                "b,banana,qwe"
        ));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> converter.convertToTransaction(actual));
    }
}
