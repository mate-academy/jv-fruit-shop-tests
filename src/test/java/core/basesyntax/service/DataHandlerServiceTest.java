package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.impl.DataHandlerServiceImpl;
import core.basesyntax.strategy.StorageUpdateHandler;
import core.basesyntax.strategy.impl.FruitBalanceHandler;
import core.basesyntax.strategy.impl.FruitPurchaseHandler;
import core.basesyntax.strategy.impl.FruitReturnHandler;
import core.basesyntax.strategy.impl.FruitSupplyHandler;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataHandlerServiceTest {
    private static final String EMPY_STRING = "";
    private static DataHandlerService dataHandlerService;

    @BeforeAll
    public static void setUp() {
        List<StorageUpdateHandler> storageUpdateHandlers = new ArrayList<>();
        storageUpdateHandlers.add(new FruitBalanceHandler());
        storageUpdateHandlers.add(new FruitSupplyHandler());
        storageUpdateHandlers.add(new FruitPurchaseHandler());
        storageUpdateHandlers.add(new FruitReturnHandler());

        dataHandlerService = new DataHandlerServiceImpl(storageUpdateHandlers);
    }

    @Test
    void calculate_correctInputData_Ok() {
        String inputString = """
                type,fruit,quantity
                b,banana,20
                b,apple,100
                s,banana,100
                p,banana,13
                r,apple,10
                p,apple,20
                p,banana,5
                s,banana,50
                """;
        String expected = """
                fruit,quantity
                banana,152
                apple,90
                """;
        String actual = dataHandlerService.calculateInputData(inputString);
        assertEquals(expected, actual);
    }

    @Test
    void calculate_nullInputData_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataHandlerService.calculateInputData(null));
    }

    @Test
    void calculate_emptyInput_Ok() {
        String actual = dataHandlerService.calculateInputData(EMPY_STRING);
        assertEquals(EMPY_STRING, actual);
    }
}
