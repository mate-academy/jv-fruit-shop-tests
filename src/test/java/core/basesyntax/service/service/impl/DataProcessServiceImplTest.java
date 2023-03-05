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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessServiceImplTest {
    private static final String FILE_FROM = "fileFrom.csv";
    private static final String FILE_TO = "fileTo.csv";
    private static DataProcessService dataProcessService;
    private static List<String> expected;
    private static List<String> actualReport;

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
        expected = new ArrayList<>();
        actualReport = new ArrayList<>();
    }

    @Test
    public void processReport_ok() {
        String okFileContent = String.join("", "type,fruit,quantity\n",
                "b,banana,20\n",
                "b,apple,100\n",
                "s,banana,100\n",
                "p,banana,13\n",
                "r,apple,10\n",
                "p,apple,20\n",
                "p,banana,5\n",
                "s,banana,50\n");
        try {
            Files.write(new File(FILE_FROM).toPath(), okFileContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file" + FILE_FROM);
        }
        dataProcessService.processReport(FILE_FROM, FILE_TO);
        expected = List.of("fruit,quantity",
                "banana,152",
                "apple,90");
        try {
            actualReport = Files.readAllLines(new File(FILE_TO).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file " + FILE_TO, e);
        }
        assertEquals(expected, actualReport);
    }

    @AfterClass
    public static void afterClass() {
        File fileFrom = new File(FILE_FROM);
        fileFrom.delete();
        File fileTo = new File(FILE_TO);
        fileTo.delete();
        Storage.getFruits().clear();
    }
}
