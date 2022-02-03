package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.storage.DataBase;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseFruitHandlerImplTest {
    private static final Map<String, Integer> db = new HashMap<>();
    private static final AddHandlerImpl addHandler = new AddHandlerImpl();
    private static final PurchaseFruitHandlerImpl purchaseHandler
            = new PurchaseFruitHandlerImpl();
    private FruitRecordDto fruitRecordDtoBalance;
    private FruitRecordDto purchaseFruitRecordDto;

    @BeforeEach
    void setUp() {
        OperationType operationTypeBalance = OperationType.BALANCE;
        OperationType operationTypePurchase = OperationType.PURCHASE;
        int purchaseAmount = 25;
        int balanceAmount = 50;
        purchaseFruitRecordDto
                = new FruitRecordDto(operationTypePurchase, "banana", purchaseAmount);
        fruitRecordDtoBalance =
                new FruitRecordDto(operationTypeBalance, "banana", balanceAmount);
    }

    @AfterEach
    void tearDown() {
        DataBase.getDataBase().clear();
    }

    @Test
    void getAmountAfterPurchaseTest_Ok() {
        addHandler.applyFruitToStorage(fruitRecordDtoBalance);
        int actual = purchaseHandler.applyFruitToStorage(purchaseFruitRecordDto);
        db.put(purchaseFruitRecordDto.getName(), purchaseFruitRecordDto.getAmount());
        int expected = 25;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void noFruitInStorageTest_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                purchaseHandler.applyFruitToStorage(purchaseFruitRecordDto));
    }
}
