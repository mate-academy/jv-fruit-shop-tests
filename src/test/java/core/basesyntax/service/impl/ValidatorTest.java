package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.ApplierFruitsToStorage;
import core.basesyntax.storage.DataBase;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static final ApplierFruitsToStorage supplyFruits
            = new AddHandlerImpl();
    private static final ApplierFruitsToStorage purchaseFruits
                    = new PurchaseFruitHandlerImpl();
    private static final Validator validator = new Validator();
    private static FruitRecordDto NegativeAmountFruitRecordDto;
    private static FruitRecordDto testFruitRecordDto;
    private static FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void beforeClass() {
        NegativeAmountFruitRecordDto = new FruitRecordDto(OperationType.BALANCE,
                "banana", -10);
        testFruitRecordDto = new FruitRecordDto(OperationType.PURCHASE,
                "banana", 30);
        fruitRecordDto = new FruitRecordDto(OperationType.BALANCE,
                "banana", 10);
    }

    @After
    public void tearDown() {
        DataBase.getDataBase().clear();
    }

    @Test(expected = RuntimeException.class)
    public void amountValidationTest_NotOk() {
        validator.checkPurchaseValidation(NegativeAmountFruitRecordDto);
    }

    @Test(expected = RuntimeException.class)
    public void amountInStorageNotEnoughTest_NotOk() {
        DataBase.getDataBase()
                .put(NegativeAmountFruitRecordDto.getName(),
                        NegativeAmountFruitRecordDto.getAmount());
        validator.checkPurchaseValidation(testFruitRecordDto);
    }

    @Test
    public void checkPurchaseValidationTest_Ok() {
        supplyFruits.applyFruitToStorage(testFruitRecordDto);
        validator.checkPurchaseValidation(fruitRecordDto);
        int expected = 20;
        int actual = purchaseFruits.applyFruitToStorage(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }
}
