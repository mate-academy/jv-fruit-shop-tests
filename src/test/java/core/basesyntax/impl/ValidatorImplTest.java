package core.basesyntax.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.operation.OperationType;
import core.basesyntax.service.Validator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void initializer() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validateRecord_validRecord_Ok() {
        FruitRecordDto fruitRecordDto = FruitRecordDto.builder()
                .fruit(Fruit.builder().name("peach").build())
                .type(OperationType.BALANCE).amount(10).build();
        boolean actual = validator.validateRecord(fruitRecordDto);
        Assert.assertTrue("Expected true with fruitRecord: "
                + fruitRecordDto + ", but was: " + actual, actual);
    }

    @Test(expected = RuntimeException.class)
    public void validateRecord_negativeAmount_NotOk() {
        FruitRecordDto fruitRecordDto = FruitRecordDto.builder()
                .fruit(Fruit.builder().name("peach").build())
                .type(OperationType.BALANCE).amount(-8).build();
        boolean actual = validator.validateRecord(fruitRecordDto);
    }

    @Test(expected = RuntimeException.class)
    public void validateRecord_nullFruit_NotOk() {
        FruitRecordDto fruitRecordDto = FruitRecordDto.builder()
                .fruit(null)
                .type(OperationType.BALANCE).amount(10).build();
        boolean actual = validator.validateRecord(fruitRecordDto);
    }

    @Test(expected = RuntimeException.class)
    public void validateRecord_nullType_NotOk() {
        FruitRecordDto fruitRecordDto = FruitRecordDto.builder()
                .fruit(Fruit.builder().name("apricot").build())
                .type(null).amount(16).build();
        boolean actual = validator.validateRecord(fruitRecordDto);
    }

    @Test(expected = RuntimeException.class)
    public void validAmount_notANumber_NotOk() {
        String line = "abc";
        boolean expected = validator.validAmount(line);
    }
}
