package core.basesyntax.fruitshop.dao;

import core.basesyntax.fruitshop.db.FruitStorage;
import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.dto.FruitOperationDto;
import core.basesyntax.fruitshop.service.shopoperation.OperationType;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStorageDaoImplTest {
    private static Fruit apple;
    private static Fruit banana;
    private static FruitOperationDto fruitOperationDto;
    private static FruitStorageDao fruitStorageDao;

    @BeforeClass
    public static void beforeClass() {
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        fruitOperationDto = new FruitOperationDto(OperationType.BALANCE,
                "banana", new BigDecimal(10));
        fruitStorageDao = new FruitStorageDaoImpl();
    }

    @Test
    public void getValueFromStorage_EmptyStorage() {
        BigDecimal expected = BigDecimal.ZERO;
        BigDecimal actual = fruitStorageDao.getValueFromStorage(fruitOperationDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getValueFromStorage_RequestedFruitNotPresentInStorage() {
        FruitStorage.getFruitMap().put(apple, new BigDecimal(5));
        BigDecimal expected = BigDecimal.ZERO;
        BigDecimal actual = fruitStorageDao.getValueFromStorage(fruitOperationDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getValueFromStorage_FruitIsPresentInStorage() {
        FruitStorage.getFruitMap().put(apple, new BigDecimal(5));
        FruitStorage.getFruitMap().put(banana, new BigDecimal(20));
        BigDecimal expected = BigDecimal.valueOf(20);
        BigDecimal actual = fruitStorageDao.getValueFromStorage(fruitOperationDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getValueFromStorage_FruitOperationDtoWithNullValues() {
        FruitStorage.getFruitMap().put(apple, new BigDecimal(10));
        FruitOperationDto nullableDto = new FruitOperationDto(null, null, null);
        BigDecimal expected = BigDecimal.ZERO;
        BigDecimal actual = fruitStorageDao.getValueFromStorage(nullableDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateDataInStorage_EmptyStorage() {
        fruitStorageDao.updateDataInStorage(fruitOperationDto);
        Fruit expectedFruit = banana;
        BigDecimal expectedValue = BigDecimal.valueOf(10);
        Assert.assertTrue(FruitStorage.getFruitMap().containsKey(expectedFruit));
        Assert.assertTrue(FruitStorage.getFruitMap().containsValue(expectedValue));
    }

    @Test
    public void updateDataInStorage_NotEmptyStorage() {
        FruitStorage.getFruitMap().put(banana, new BigDecimal(20));
        fruitStorageDao.updateDataInStorage(fruitOperationDto);
        BigDecimal expectedValue = BigDecimal.valueOf(10);
        BigDecimal actualValue = FruitStorage.getFruitMap().get(banana);
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void updateDataInStorage_FruitOperationDtoWithNullValues() {
        FruitOperationDto nullableDto = new FruitOperationDto(null, null, null);
        fruitStorageDao.updateDataInStorage(nullableDto);
        Fruit fruit = new Fruit(null);
        Assert.assertTrue(FruitStorage.getFruitMap().containsKey(fruit));
    }

    @Test
    public void getDataReportFromStorage() {
        FruitStorage.getFruitMap().put(banana, new BigDecimal(20));
        FruitStorage.getFruitMap().put(apple, new BigDecimal(10));
        Set<Map.Entry<Fruit, BigDecimal>> actual = fruitStorageDao.getDataReportFromStorage();
        Set<Map.Entry<Fruit, BigDecimal>> expected = FruitStorage.getFruitMap().entrySet();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void updateDataInStorage_NullFruitOperationDtoException_isThrown() {
        fruitStorageDao.getValueFromStorage(null);
    }

    @After
    public void tearDown() {
        FruitStorage.getFruitMap().clear();
    }
}
