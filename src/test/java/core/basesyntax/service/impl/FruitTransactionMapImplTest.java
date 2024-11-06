package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.ShopTransaction;
import core.basesyntax.service.FruitTransactionMap;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionMapImplTest {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int WEIGHT_INDEX = 2;
    private static final String COMA = ",";
    private FruitTransactionMap mapTransaction;
    private ShopTransaction transaction;
    private List<ShopTransaction> listValidReturnType;
    private List<String> validData;
    private List<String> notValidData;
    private List<String> emptyList;

    @BeforeEach
    void setUp() {
        listValidReturnType = new ArrayList<>();
        emptyList = new ArrayList<>();
        validData = List.of("b,apple,70", "s,banana,44");
        notValidData = List.of("n,melon,-152");
    }

    @AfterEach
    void tearDown() {
        Storage.fruitShopStorage.clear();
    }

    @Test
    void mapEmptyData_notOk() {
        assertThrows(RuntimeException.class,
                () -> mapTransaction.map(emptyList));
    }

    @Test
    void mullMapping_NotOk() {
        assertThrows(RuntimeException.class,
                () -> mapTransaction.map(null));
    }

    @Test
    void returnType_Ok() {
        List<ShopTransaction> shopTransactionList = new ArrayList<>();
        assertEquals(listValidReturnType, shopTransactionList,
                "Should return List<ShopTransaction>");
    }

    @Test
    void operationIndex_Ok() {
        boolean expected = false;
        String codeOperation;
        String[] record = validData.get(0).split(COMA);
        codeOperation = record[OPERATION_INDEX];
        for (OperationType type : OperationType.values()) {
            if (type.getCodeOperation().equals(codeOperation)) {
                expected = true;
            }
        }
        assertTrue(expected);
    }

    @Test
    void operationIndex_NotOk() {
        boolean expected = false;
        String codeOperation;
        String[] record = notValidData.get(0).split(COMA);
        codeOperation = record[OPERATION_INDEX];
        for (OperationType type : OperationType.values()) {
            if (type.getCodeOperation().equals(codeOperation)) {
                expected = true;
            }
        }
        assertFalse(expected);
    }

    @Test
    void nullFruit_NotOk() {
        String[] record = validData.get(0).split(COMA);
        String expected = record[FRUIT_INDEX];
        assertNotNull(expected);
    }

    @Test
    void fruitIndex_NotOk() {
        String[] record = validData.get(0).split(COMA);
        String expected = record[FRUIT_INDEX];
        assertEquals(expected,"apple");
    }

    @Test
    void negativeWeight_NotOk() {
        String[] record = notValidData.get(0).split(COMA);
        int weight = Integer.parseInt(record[WEIGHT_INDEX]);
        assertTrue(weight < 0);
    }

    @Test
    void weight_Ok() {
        String[] record = validData.get(0).split(COMA);
        int weight = Integer.parseInt(record[WEIGHT_INDEX]);
        assertTrue(weight >= 0);
    }

    @Test
    void addTransaction_Ok() {
        String[] record = validData.get(0).split(COMA);
        String activity = record[OPERATION_INDEX];
        String fruit = record[FRUIT_INDEX];
        int weight = Integer.parseInt(record[WEIGHT_INDEX]);
        listValidReturnType.add(new ShopTransaction(OperationType.getOperationType(activity),
                fruit, weight));
        final int actualWeight = listValidReturnType.get(0).getWeight();
        final String actualFruit = listValidReturnType.get(0).getFruitName();
        assertEquals(70, actualWeight);
        assertEquals("apple", actualFruit);
    }

    @Test
    void returnEmptyList_NotOk() {
        FruitTransactionMap transactionMap = new FruitTransactionMapImpl();
        final List<ShopTransaction> actual = transactionMap.map(validData);
        assertNotNull(actual);
    }
}
