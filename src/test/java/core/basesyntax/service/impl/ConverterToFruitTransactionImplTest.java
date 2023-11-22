package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ConvertToFruitTransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConverterToFruitTransactionImplTest {
    private static ConvertToFruitTransactionService convertService;
    private static List<FruitTransaction> expectedList;
    private static List<String> inputList;

    @BeforeAll
    static void beforeAll() {
        convertService = new ConverterToFruitTransactionImpl();
        expectedList = new ArrayList<>();
        inputList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        expectedList.clear();
        inputList.clear();
    }

    @Test
    void convert_negativeQuantityList_notOk() {
        generateValidInputList();
        inputList.add("b,apple,-100");
        assertThrows(RuntimeException.class, () -> convertService.convert(inputList));
    }

    @Test
    void convert_validInputList_ok() {
        generateValidInputList();
        expectedList.add(new FruitTransaction(Operation.BALANCE, "banana", 0));
        expectedList.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        expectedList.add(new FruitTransaction(Operation.SUPPLY, "banana", 10));
        expectedList.add(new FruitTransaction(Operation.RETURN, "apple", 30));

        List<FruitTransaction> actual = convertService.convert(inputList);
        assertEquals(expectedList, actual);
    }

    private void generateValidInputList() {
        inputList.add("type,fruit,quantity");
        inputList.add("b,banana,0");
        inputList.add("b,apple,100");
        inputList.add("s,banana,10");
        inputList.add("r,apple,30");
    }
}
