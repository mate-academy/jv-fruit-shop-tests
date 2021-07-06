package core.basesyntax.fruitshop.service.shopoperation;

import core.basesyntax.fruitshop.dao.FruitStorageDao;
import core.basesyntax.fruitshop.dao.FruitStorageDaoImpl;
import core.basesyntax.fruitshop.db.FruitStorage;
import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.dto.FruitOperationDto;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageIncreaseHandlerTest {
    private static Fruit apple;
    private static Fruit banana;
    private static FruitOperationDto fruitOperationDto;
    private static OperationHandler storageIncrease;

    @BeforeClass
    public static void beforeClass() {
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        fruitOperationDto = new FruitOperationDto(OperationType.BALANCE,
                "banana", new BigDecimal(20));
        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl();
        storageIncrease = new StorageIncreaseHandler(fruitStorageDao);
    }

    @Test
    public void apply_IncreaseStorageByFruitQuantity_isOk() {
        FruitStorage.getFruitMap().put(banana, new BigDecimal(30));
        BigDecimal expectedValue = BigDecimal.valueOf(50);
        FruitOperationDto expected = new FruitOperationDto(OperationType.BALANCE,
                "banana", expectedValue);
        FruitOperationDto actual = storageIncrease.apply(fruitOperationDto);
        BigDecimal actualValue = FruitStorage.getFruitMap().get(banana);
        Assert.assertEquals(expectedValue, actualValue);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_IncreaseEmptyStorageByFruitQuantity_isOk() {
        BigDecimal expectedValue = BigDecimal.valueOf(20);
        FruitOperationDto expected = new FruitOperationDto(OperationType.BALANCE,
                "banana", expectedValue);
        FruitOperationDto actual = storageIncrease.apply(fruitOperationDto);
        BigDecimal actualValue = FruitStorage.getFruitMap().get(banana);
        Assert.assertEquals(expectedValue, actualValue);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_FruitOperationDtoWithNullValues() {
        FruitStorage.getFruitMap().put(apple, new BigDecimal(10));
        FruitOperationDto nullableDto = new FruitOperationDto(null, null, null);
        storageIncrease.apply(nullableDto);
    }

    @Test(expected = NullPointerException.class)
    public void apply_NullFruitOperationDtoException_isThrown() {
        storageIncrease.apply(null);
    }

    @After
    public void tearDown() {
        FruitStorage.getFruitMap().clear();
    }
}
