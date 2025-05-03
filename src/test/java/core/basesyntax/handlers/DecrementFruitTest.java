package core.basesyntax.handlers;

import core.basesyntax.db.FruitDataBase;
import core.basesyntax.dto.TransactionDto;
import core.basesyntax.exceptions.IncorrectAmountException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DecrementFruitTest {
    private static final String COCONUT = "coconut";
    private static final int NEGATIVE_DATA = -55;
    private static final int POSITIVE_DATA = 55;
    private static final int ZERO = 0;
    private static TransactionDto transactionDto;
    private static DecrementFruit decrementFruit;

    @BeforeClass
    public static void beforeClass() {
        decrementFruit = new DecrementFruit();
    }

    @After
    public void clearData() {
        FruitDataBase.getFruitMap().clear();
    }

    @Test(expected = IncorrectAmountException.class)
    public void decrementFruitWithEmtyDataBaze_notOK() {
        transactionDto = new TransactionDto(Operation.PURCHASE, new Fruit(COCONUT), POSITIVE_DATA);
        FruitDataBase.getFruitMap().put(new Fruit(COCONUT), ZERO);
        decrementFruit.change(transactionDto);
    }

    @Test(expected = IncorrectAmountException.class)
    public void dercementFruitWithNegativeValue_notOK() {
        transactionDto = new TransactionDto(Operation.BALANCE, new Fruit(COCONUT), NEGATIVE_DATA);
        decrementFruit.change(transactionDto);
    }

    @Test
    public void decrementFruitWithValidData_OK() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit(COCONUT), ZERO);
        transactionDto = new TransactionDto(Operation.PURCHASE, new Fruit(COCONUT), POSITIVE_DATA);
        FruitDataBase.getFruitMap().put(new Fruit(COCONUT), POSITIVE_DATA);
        decrementFruit.change(transactionDto);
        Map<Fruit, Integer> actual = FruitDataBase.getFruitMap();
        Assert.assertEquals(expected, actual);
    }
}
