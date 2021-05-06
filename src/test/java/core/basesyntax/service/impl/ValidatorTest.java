package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.storage.DataBase;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static FruitRecordDto NegativeAmountFruitRecordDto;
    private static FruitRecordDto testFruitRecordDto;
    private static FruitRecordDto fruitRecordDto;
    private static final Validator validator = new Validator();

    @BeforeClass
    public static void beforeClass() throws Exception {
        NegativeAmountFruitRecordDto = new FruitRecordDto(OperationType.BALANCE,
                "banana", -10);
        testFruitRecordDto = new FruitRecordDto(OperationType.PURCHASE,
                "banana", 30);
        fruitRecordDto = new FruitRecordDto(OperationType.BALANCE,
                "banana", 10);
    }

    @Test(expected = RuntimeException.class)
    public void amountValidationTest_NotOk() {
        validator.checkPurchaseValidation(NegativeAmountFruitRecordDto);
        DataBase.getDataBase().remove("banana");
    }

    @Test(expected = RuntimeException.class)
    public void amountInStorageTest_NotOk() {
        DataBase.getDataBase()
                .put(NegativeAmountFruitRecordDto.getName(),
                        NegativeAmountFruitRecordDto.getAmount());
        validator.checkPurchaseValidation(testFruitRecordDto);
        DataBase.getDataBase().remove(NegativeAmountFruitRecordDto.getName());
    }
}
