package core.basesyntax.store;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.model.OperationType;
import core.basesyntax.store.strategy.BalanceHandler;
import core.basesyntax.store.strategy.PurchaseHandler;
import core.basesyntax.store.strategy.ReturnHandler;
import core.basesyntax.store.strategy.SupplyHandler;
import core.basesyntax.store.strategy.TypeHandler;
import core.basesyntax.validator.quantity.QuantityValidator;
import core.basesyntax.validator.quantity.QuantityValidatorImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageServiceImplTest {
    private static List<FruitRecord> fruitRecordList;
    private static StorageService storageService;
    private static final String APPLE = "apple";
    private static final long QUANTITY = 100;
    private static Fruit fruit;
    private static final OperationType BALANCE = OperationType.B;

    @BeforeClass
    public static void setUp() {
        Map<OperationType, TypeHandler> typeHandlerMap = new HashMap<>();
        FruitDao fruitDao = new FruitDaoImpl();
        QuantityValidator quantityValidator = new QuantityValidatorImpl();
        typeHandlerMap.put(OperationType.B, new BalanceHandler(fruitDao));
        typeHandlerMap.put(OperationType.S, new SupplyHandler(fruitDao));
        typeHandlerMap.put(OperationType.P, new PurchaseHandler(fruitDao, quantityValidator));
        typeHandlerMap.put(OperationType.R, new ReturnHandler(fruitDao));
        TypeStrategy typeStrategy = new TypeStrategyImpl(typeHandlerMap);
        fruitRecordList = new ArrayList<>();
        storageService = new StorageServiceImpl(
                typeStrategy, new FruitDaoImpl());
        fruit = new Fruit(APPLE, QUANTITY);
        FruitRecord fruitRecord = new FruitRecord(fruit, BALANCE);
        fruitRecordList.add(fruitRecord);
    }

    @Test
    public void saveData() {
        List<Fruit> expected = new ArrayList<>();
        expected.add(fruit);
        List<Fruit> actual = storageService.saveData(fruitRecordList);
        assertEquals(expected, actual);

    }

    @Test
    public void getReport() {
        String expected = "fruit,quantity\n" + APPLE + ',' + QUANTITY;
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(fruit);
        String actual = storageService.getReport(fruitList);
        assertEquals(expected, actual);
    }
}
