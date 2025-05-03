package core.basesyntax.dataservice;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.transactions.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter = new DataConverterImpl();

    @Test
    void conversion_ParameterOperationNotValid_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("b,apple,80");
        inputData.add("23,banana,700");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });
    }

    @Test
    void conversion_ParameterFruitIsEmpty_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("s,apple,78");
        inputData.add("r,,9");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });
    }

    @Test
    void conversion_ParameterNumberIsInvalid_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("s,banana,78");
        inputData.add("r,apple,string");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });
    }

    @Test
    void conversion_ParameterNumberIsEmpty_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("b,apple,178");
        inputData.add("r,apple,");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });
    }

    @Test
    void conversion_AllParametersIsRight_Ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add("b,banana,20");
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        List<FruitTransaction> expected = List.of(fruitTransaction);
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputData);
        assertIterableEquals(actual, expected);
    }
}
