package core.basesyntax.dataservice;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import core.basesyntax.transactions.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class DataConverterImplTest {
    private DataConverter dataConverter = new DataConverterImpl();
    private List<String> inputData;

    @BeforeEach
    void setUp() {
        inputData = new ArrayList<>();
    }

    @Test
    void conversion_ParameterOperationNotValid_NotOk() {
        inputData.add("b,apple,80");
        inputData.add("23,banana,700");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });
    }

    @Test
    void conversion_ParameterFruitIsEmpty_NotOk() {
        inputData.add("s,apple,78");
        inputData.add("r,,9");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });
    }

    @Test
    void conversion_ParameterNumberIsInvalid_NotOk() {
        inputData.add("s,banana,78");
        inputData.add("r,apple,string");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });
    }

    @Test
    void conversion_ParameterNumberIsEmpty_NotOk() {
        inputData.add("b,apple,178");
        inputData.add("r,apple,");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });
    }

    @Test
    void conversion_AllParametersIsRight_Ok() {
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