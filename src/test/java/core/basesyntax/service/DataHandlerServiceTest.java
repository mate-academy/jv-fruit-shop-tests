package core.basesyntax.service;

import core.basesyntax.service.impl.DataHandlerServiceImpl;
import core.basesyntax.strategy.StorageUpdateHandler;
import core.basesyntax.strategy.impl.FruitBalanceHandler;
import core.basesyntax.strategy.impl.FruitPurchaseHandler;
import core.basesyntax.strategy.impl.FruitReturnHandler;
import core.basesyntax.strategy.impl.FruitSupplyHandler;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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
    public void calculate_correctInputData_Ok() {
        StringBuilder inputStringBuilder = new StringBuilder("type,fruit,quantity");
        inputStringBuilder.append(System.lineSeparator()).append("b,banana,20")
                .append(System.lineSeparator()).append("b,apple,100")
                .append(System.lineSeparator()).append("s,banana,100")
                .append(System.lineSeparator()).append("p,banana,13")
                .append(System.lineSeparator()).append("r,apple,10")
                .append(System.lineSeparator()).append("p,apple,20")
                .append(System.lineSeparator()).append("p,banana,5")
                .append(System.lineSeparator()).append("s,banana,50")
                .append(System.lineSeparator());
        String inputString = inputStringBuilder.toString();
        StringBuilder reportStringBuilder = new StringBuilder("fruit,quantity");
        reportStringBuilder.append(System.lineSeparator()).append("banana,152")
                .append(System.lineSeparator()).append("apple,90")
                .append(System.lineSeparator());
        String expected = reportStringBuilder.toString();
        String actual = dataHandlerService.calculateInputData(inputString);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void calculate_nullInputData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> dataHandlerService.calculateInputData(null));
    }

    @Test
    public void calculate_emptyInput_Ok() {
        String actual = dataHandlerService.calculateInputData(EMPY_STRING);
        Assertions.assertEquals(EMPY_STRING, actual);
    }
}
