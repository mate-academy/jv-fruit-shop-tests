package core.basesyntax.service.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessService;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationHandlerOfBalance;
import core.basesyntax.service.strategy.OperationHandlerOfPurchase;
import core.basesyntax.service.strategy.OperationHandlerOfReturn;
import core.basesyntax.service.strategy.OperationHandlerOfSupply;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessServiceImplTest {
    private static final String FILE_FROM = "src/test/resources/fileFrom.csv";
    private static final String FILE_TO = "src/test/resources/fileTo.csv";
    private static DataProcessService dataProcessService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<FruitTransaction.Operation, OperationHandler> operationServiceMap = Map.of(
                FruitTransaction.Operation.BALANCE,
                new OperationHandlerOfBalance(),
                FruitTransaction.Operation.SUPPLY,
                new OperationHandlerOfSupply(),
                FruitTransaction.Operation.PURCHASE,
                new OperationHandlerOfPurchase(),
                FruitTransaction.Operation.RETURN,
                new OperationHandlerOfReturn());
        dataProcessService = new DataProcessServiceImpl(operationServiceMap);
    }

    @Test
    public void processReport_ok() {
        dataProcessService.processReport(FILE_FROM, FILE_TO);
        List<String> actualReport;
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,152",
                "apple,90");
        try {
            actualReport = Files.readAllLines(Path.of(FILE_TO));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file " + FILE_TO, e);
        }
        assertEquals(expected, actualReport);
    }

    @After
    public void tearDown() {
        Storage.getFruits().clear();
    }
}
