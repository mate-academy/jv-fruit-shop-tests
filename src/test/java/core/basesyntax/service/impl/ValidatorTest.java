package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.storage.DataBase;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static FruitRecordDto NegativeAmountFruitRecordDto;
    private static FruitRecordDto testFruitRecordDto;
    private static FruitRecordDto fruitRecordDto;
    private static final Validator validator = new Validator();

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
        DataBase.getDataBase().remove(NegativeAmountFruitRecordDto.getName());
    }

    @Test(expected = RuntimeException.class)
    public void amountValidationTest_NotOk() {
        validator.checkPurchaseValidation(NegativeAmountFruitRecordDto);
    }

    @Test(expected = RuntimeException.class)
    public void amountInStorageTest_NotOk() {
        DataBase.getDataBase()
                .put(NegativeAmountFruitRecordDto.getName(),
                        NegativeAmountFruitRecordDto.getAmount());
        validator.checkPurchaseValidation(testFruitRecordDto);
    }
}
