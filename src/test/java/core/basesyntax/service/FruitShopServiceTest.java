package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.functionalityexpansion.ActivityHandlerProvider;
import core.basesyntax.service.functionalityexpansion.ActivityType;
import core.basesyntax.service.parsefileinfo.FruitTransactionInfo;
import core.basesyntax.service.strategy.ActivityHandler;
import core.basesyntax.service.strategy.BalanceHandler;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitShopServiceTest {
    private final Storage storage = new Storage();
    private final ActivityHandlerProvider activityProvider = new ActivityHandlerProvider(storage);

    @Test
    void execute_nullInput_Ok() {
        FruitShopService fruitShopService = new FruitShopService(activityProvider);
        assertThrows(NullPointerException.class, () -> fruitShopService.execute(null));
    }

    @Test
    void execute_correctActivity_Ok() {
        FruitShopService fruitShopService = new FruitShopService(activityProvider);
        List<FruitTransactionInfo> testList = getList();
        ActivityHandler expectedActivity = new BalanceHandler(storage);

        ActivityHandler actualActivity = fruitShopService.execute(testList);
        assertEquals(expectedActivity.getClass(), actualActivity.getClass());
    }

    private List<FruitTransactionInfo> getList() {
        List<FruitTransactionInfo> fruits = new ArrayList<>();
        fruits.add(new FruitTransactionInfo(ActivityType.BALANCE, "banana", 20));
        fruits.add(new FruitTransactionInfo(ActivityType.BALANCE, "apple", 20));
        return fruits;
    }
}
