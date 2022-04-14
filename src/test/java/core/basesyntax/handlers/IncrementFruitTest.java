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

public class IncrementFruitTest {
    private static final String COCONUT = "coconut";
    private static final int NEGATIVE_DATA = -55;
    private static final int POSITIVE_DATA = 55;
    private static TransactionDto transactionDto;
    private static IncrementFruit incrementFruit;

    @BeforeClass
    public static void beforeClass() {
        incrementFruit = new IncrementFruit();
    }

    @After
    public void clearData() {
        FruitDataBase.getFruitMap().clear();
    }

    @Test(expected = IncorrectAmountException.class)
    public void inecrementFruitWithNegativeData_notOK() {
        transactionDto = new TransactionDto(Operation.BALANCE, new Fruit(COCONUT), NEGATIVE_DATA);
        incrementFruit.change(transactionDto);
    }

    @Test
    public void incrementNewFruitWithValidData_OK() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit(COCONUT), POSITIVE_DATA);
        transactionDto = new TransactionDto(Operation.SUPPLY, new Fruit(COCONUT), POSITIVE_DATA);
        incrementFruit.change(transactionDto);
        Map<Fruit, Integer> actual = FruitDataBase.getFruitMap();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void incrementFruitWithReturnOperation_OK() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit(COCONUT), POSITIVE_DATA);
        transactionDto = new TransactionDto(Operation.RETURN, new Fruit(COCONUT), POSITIVE_DATA);
        incrementFruit.change(transactionDto);
        Map<Fruit, Integer> actual = FruitDataBase.getFruitMap();
        Assert.assertEquals(expected, actual);
    }
}
