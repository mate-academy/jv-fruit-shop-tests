package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.exceptions.ValidationException;
import core.basesyntax.service.functionalityexpansion.ActivityHandlerProvider;
import core.basesyntax.service.functionalityexpansion.ActivityType;
import core.basesyntax.service.parsefileinfo.FruitTransactionInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceTest {
    public static final List<FruitTransactionInfo> EMPTY_LIST = new ArrayList<>();
    private static final String FRUIT = "banana";
    private static final int QUANTITY = 20;
    private Storage storage;
    private ActivityHandlerProvider activityProvider;
    private FruitShopService fruitShopService;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        activityProvider = new ActivityHandlerProvider(storage);
        fruitShopService = new FruitShopService(activityProvider);
    }

    @Test
    void execute_nullInput_notOk() {
        assertThrows(ValidationException.class, () -> fruitShopService.execute(EMPTY_LIST));
    }

    @Test
    void execute_correctActivity_Ok() {
        List<FruitTransactionInfo> testList =
                List.of(new FruitTransactionInfo(ActivityType.BALANCE, FRUIT, QUANTITY));
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put(FRUIT, QUANTITY);

        fruitShopService.execute(testList);
        assertEquals(testMap, storage.getData());
    }
}
