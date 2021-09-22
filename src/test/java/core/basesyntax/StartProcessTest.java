package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.shop.item.Fruit;
import core.basesyntax.shop.service.FileReader;
import core.basesyntax.shop.service.FileReaderImpl;
import core.basesyntax.shop.service.OperationsStrategy;
import core.basesyntax.shop.service.OperationsStrategyImpl;
import core.basesyntax.shop.service.ShopService;
import core.basesyntax.shop.service.ShopServiceImpl;
import core.basesyntax.shop.service.operations.Balance;
import core.basesyntax.shop.service.operations.OperationHandler;
import core.basesyntax.shop.service.operations.Purchase;
import core.basesyntax.shop.service.operations.Return;
import core.basesyntax.shop.service.operations.Supply;
import core.basesyntax.validators.ValidatorForFile;
import core.basesyntax.validators.ValidatorForFileImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StartProcessTest {

    private static final Fruit BANANA0 = new Fruit("banana", 0);
    private static final Fruit BANANA30 = new Fruit("banana", 30);
    private static final String[] FRUITS = new String[]{"type,fruit,quantity",
            "s,banana,20"};

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private Map<String, OperationHandler> operationMap = new HashMap<>();

    private OperationsStrategy operationsStrategy = new OperationsStrategyImpl(operationMap);
    private ShopService shop = new ShopServiceImpl(new FruitDaoImpl(), operationsStrategy);

    @After
    public void init() {
        Storage.fruits.clear();
    }

    @Test
    public void testAddFruitDaoImpl() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.add(BANANA30);
        Fruit actual = Storage.fruits.get(0);
        assertEquals(BANANA30, actual);
    }

    @Test
    public void testGetFruitDaoImpl() {
        FruitDao fruitDao = new FruitDaoImpl();
        Storage.fruits.add(BANANA30);
        assertEquals(BANANA30, fruitDao.get(BANANA30));
    }

    @Test
    public void testUpdateFruitDaoImpl() {
        Storage.fruits.clear();
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.add(BANANA0);
        fruitDao.update(BANANA30);
        Integer expected = 30;
        assertEquals("Method UPDATE in class FruitDaoImpl no work",
                expected,
                fruitDao.getAll().get(0).getQuality());
    }

    @Test
    public void testOkFileReaderImpl() throws IOException {
        FileReader fileReader = new FileReaderImpl();
        String expected = "type,fruit,quantity";
        String actual = fileReader.read("10.09.99")[0];
        assertEquals("File not right read", expected, actual);
    }

    @Test
    public void testNoOkFileReaderImpl() throws IOException {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Error!!! File not found!!!");
        FileReader fileReader = new FileReaderImpl();
        fileReader.read("nofile");
    }

    @Test
    public void testOperationStrategyMapOk() {
        Map<String, OperationHandler> operationMap = new HashMap<>();
        operationMap.put("b", new Balance());
        OperationsStrategy operationsStrategy = new OperationsStrategyImpl(operationMap);
        assertEquals(Balance.class, operationsStrategy.get("b").getClass());
    }

    @Test
    public void testBalanceOk() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler operationHandler = new Balance();
        operationHandler.apply(BANANA30);
        Integer expected = 30;
        assertEquals(expected, fruitDao.getAll().get(0).getQuality());
    }

    @Test
    public void testPurchaseOk() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler operationHandler = new Purchase();
        fruitDao.add(BANANA30);
        operationHandler.apply(BANANA30);
        Integer expected = 0;
        assertEquals(expected, fruitDao.getAll().get(0).getQuality());
    }

    @Test
    public void testReturnOk() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler operationHandler = new Return();
        fruitDao.add(BANANA30);
        operationHandler.apply(BANANA30);
        Integer expected = 60;
        assertEquals(expected, fruitDao.getAll().get(0).getQuality());
    }

    @Test
    public void testSupplyOk() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler operationHandler = new Supply();
        fruitDao.add(BANANA30);
        operationHandler.apply(BANANA30);
        Integer expected = 60;
        assertEquals(expected, fruitDao.getAll().get(0).getQuality());
    }

    @Test
    public void testShopServiceOK() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<String, OperationHandler> operationMap = new HashMap<>();
        operationMap.put("b", new Balance());
        operationMap.put("s", new Supply());
        operationMap.put("p", new Purchase());
        operationMap.put("r", new Return());
        ShopService shopService =
                new ShopServiceImpl(fruitDao, new OperationsStrategyImpl(operationMap));
        shopService.saveToStorage(FRUITS);
        Integer expected = 20;
        assertEquals(expected, fruitDao.getAll().get(0).getQuality());
    }

    @Test
    public void testValidatorNoOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Not found operation or name of fruit!!!");
        ValidatorForFile validator = new ValidatorForFileImpl();
        validator.test("src/main/resources/noOperation.vcs");
    }

    @Test
    public void testValidatorCorrectNoOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("The quality must be greater than zero!!!");
        ValidatorForFile validator = new ValidatorForFileImpl();
        validator.test("src/main/resources/noCorrect.vcs");
    }

}
