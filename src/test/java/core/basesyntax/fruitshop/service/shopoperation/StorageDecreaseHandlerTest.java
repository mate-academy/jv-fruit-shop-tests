package core.basesyntax.fruitshop.service.shopoperation;

import core.basesyntax.fruitshop.dao.FruitStorageDaoImpl;
import core.basesyntax.fruitshop.db.FruitStorage;
import core.basesyntax.fruitshop.exception.FruitQuantityException;
import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.dto.FruitOperationDto;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDecreaseHandlerTest {
    private static Fruit apple;
    private static Fruit banana;
    private static FruitOperationDto fruitOperationDto;
    private static OperationHandler storageDecrease;

    @BeforeClass
    public static void beforeClass() {
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        fruitOperationDto = new FruitOperationDto(OperationType.PURCHASE,
                "banana", new BigDecimal(20));
        storageDecrease = new StorageDecreaseHandler(new FruitStorageDaoImpl());
    }

    @Test
    public void apply_CorrectFruitQuantityForPurchase() {
        FruitStorage.getFruitMap().put(banana, new BigDecimal(30));
        BigDecimal expectedValue = BigDecimal.valueOf(10);
        FruitOperationDto expected = new FruitOperationDto(OperationType.PURCHASE,
                "banana", expectedValue);
        FruitOperationDto actual = storageDecrease.apply(fruitOperationDto);
        BigDecimal actualValue = FruitStorage.getFruitMap().get(banana);
        Assert.assertEquals(expectedValue, actualValue);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = FruitQuantityException.class)
    public void apply_ExcessiveFruitQuantityForPurchase() {
        FruitStorage.getFruitMap().put(banana, new BigDecimal(10));
        storageDecrease.apply(fruitOperationDto);
    }

    @Test(expected = FruitQuantityException.class)
    public void apply_PurchaseFromEmptyStorage() {
        storageDecrease.apply(fruitOperationDto);
    }

    @Test(expected = FruitQuantityException.class)
    public void apply_PurchaseOfFruitNotPresentInStorage() {
        FruitStorage.getFruitMap().put(apple, new BigDecimal(10));
        storageDecrease.apply(fruitOperationDto);
    }

    @Test(expected = NullPointerException.class)
    public void apply_FruitOperationDtoWithNullValues() {
        FruitStorage.getFruitMap().put(apple, new BigDecimal(10));
        FruitOperationDto nullableDto = new FruitOperationDto(null, null, null);
        storageDecrease.apply(nullableDto);
    }

    @Test(expected = NullPointerException.class)
    public void apply_NullFruitOperationDtoException_isThrown() {
        storageDecrease.apply(null);
    }

    @After
    public void tearDown() {
        FruitStorage.getFruitMap().clear();
    }
}
