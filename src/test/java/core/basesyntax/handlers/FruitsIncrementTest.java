package core.basesyntax.handlers;

import core.basesyntax.dto.TransactionDto;
import core.basesyntax.exceptions.IncorrectAmountException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.FruitDataBase;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitsIncrementTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final Integer POSITIVE_AMOUNT = 100;
    private static final Integer NEGATIVE_AMOUNT = -20;
    private static TransactionDto transactionDto;
    private static FruitsIncrement fruitsIncrement;

    @BeforeClass
    public static void beforeClass() {
        fruitsIncrement = new FruitsIncrement();
    }

    @After
    public void tearDown() {
        FruitDataBase.getFruitData().clear();
    }

    @Test
    public void apply_TestDtoWithValidData() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit(APPLE), POSITIVE_AMOUNT);
        transactionDto = new TransactionDto(Operation.SUPPLY, new Fruit(APPLE), POSITIVE_AMOUNT);
        fruitsIncrement.apply(transactionDto);
        Map<Fruit, Integer> actual = FruitDataBase.getFruitData();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IncorrectAmountException.class)
    public void apply_TestDtoAmountLessThenZero() {
        transactionDto = new TransactionDto(Operation.BALANCE, new Fruit(BANANA), NEGATIVE_AMOUNT);
        fruitsIncrement.apply(transactionDto);
    }
}
