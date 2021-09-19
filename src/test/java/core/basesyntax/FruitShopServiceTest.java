package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.HandlerStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.strategy.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.DecreaseAmountOperationHandlerImpl;
import core.basesyntax.strategy.IncreaseAmountOperationHandlerImpl;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitShopServiceTest {
    private static final String INPUT_FILE_NAME = "src/main/resources/fruits.csv";
    private static List<String> rowList;
    private static FruitShopService fruitShopService;

    @BeforeAll
    public static void initialize() {
        Map<FruitRecord.Operation, OperationHandler> handlerMap = Map.of(
                FruitRecord.Operation.PURCHASE, new DecreaseAmountOperationHandlerImpl(),
                FruitRecord.Operation.SUPPLY, new IncreaseAmountOperationHandlerImpl(),
                FruitRecord.Operation.BALANCE, new BalanceOperationHandlerImpl(),
                FruitRecord.Operation.RETURN, new IncreaseAmountOperationHandlerImpl()
        );
        fruitShopService = new FruitShopServiceImpl(new HandlerStorage(handlerMap));
        rowList = new FileReaderServiceImpl().readRowsFromFile(INPUT_FILE_NAME);
    }

    @Test
    public void addingInfoToStorage_Ok() {
        fruitShopService.addInfoToStorage(rowList);
        Map<Fruit, Integer> actual = Storage.getStorage();
        Map<Fruit, Integer> expected = Map.of(new Fruit("banana"), 152, new Fruit("apple"), 90);
        assertEquals(actual, expected);
    }
}
