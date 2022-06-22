package core.basesyntax.service.impl;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.db.Shop;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationMap;
import core.basesyntax.service.OperationService;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceImplTest {
    private static final String INPUT_FILE_PATH_TEST =
            "src/test/resources/fruit_shop_input_file.csv";
    private static final String EMPTY_FILE_TEST =
            "src/test/resources/fruit_shop_empty_file_test.csv";
    private static OperationService operationService;
    private static OperationMap operationMap;
    private static ShopDao shopDao;

    public List<String> readFileTest(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + filePath);
        }
    }

    @BeforeClass
    public static void beforeClass() {
        shopDao = new ShopDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandler = new HashMap<>();
        operationHandler.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(shopDao));
        operationHandler.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(shopDao));
        operationHandler.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(shopDao));
        operationHandler.put(FruitTransaction.Operation.RETURN, new ReturnHandler(shopDao));
        operationMap = new OperationMapImpl(operationHandler);
        operationService = new OperationServiceImpl(operationMap);
    }

    @Test(expected = RuntimeException.class)
    public void action_FromEmptyFile_notOk() {
        List<String> infoFromFile = readFileTest(EMPTY_FILE_TEST);
        operationService.action(infoFromFile);
    }

    @Test
    public void action_fromFileAll_ok() {
        List<String> infoFromFile = readFileTest(INPUT_FILE_PATH_TEST);
        operationService.action(infoFromFile);
    }

    @AfterClass
    public static void afterClass() {
        Shop.fruits.clear();
    }
}
