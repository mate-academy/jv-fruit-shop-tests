package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.ShopTransaction;
import core.basesyntax.service.FruitTransactionMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionMapImplTest {
    private FruitTransactionMap mapTransaction;
    private ShopTransaction transaction;
    private List<ShopTransaction> listValidReturnType;
    private List<String> validData;
    private List<String> notValidData;
    private List<String> emptyList;

    @BeforeEach
    void setUp() {
        listValidReturnType = new ArrayList<>();
        emptyList = Collections.emptyList();
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
                () -> mapTransaction.map(emptyList),
                "There are not any input data");
    }

    @Test
    void mullMapping_NotOk() {
        assertThrows(RuntimeException.class,
                () -> mapTransaction.map(null),
                "Input data can`t be null");
    }

    @Test
    void returnEmptyList_NotOk() {
        FruitTransactionMap transactionMap = new FruitTransactionMapImpl();
        final List<ShopTransaction> actual = transactionMap.map(validData);
        assertNotNull(actual);
    }
}
