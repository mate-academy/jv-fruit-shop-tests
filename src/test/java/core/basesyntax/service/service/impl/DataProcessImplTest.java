package core.basesyntax.service.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcess;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.impl.BalanceOperationHandler;
import core.basesyntax.service.operation.impl.PurchaseOperationHandler;
import core.basesyntax.service.operation.impl.ReturnOperationHandler;
import core.basesyntax.service.operation.impl.SupplyOperationHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.IIOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessImplTest {
    private static final String FILE_FROM = "fileFrom.scv";
    private static final String FILE_TO = "fileTo.scv";
    private static DataProcess dataProcess;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<FruitTransaction.Operation, OperationHandler> operationServiceMap = new HashMap<>();
        operationServiceMap.put(FruitTransaction.Operation.BALANCE,new BalanceOperationHandler());
        operationServiceMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationServiceMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationServiceMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        dataProcess = new DataProcessImpl(operationServiceMap);
        String okFileContent = "type,fruit,quantity"
                + System.lineSeparator()
                + "b,banana,20"
                + System.lineSeparator()
                + "b,apple,100"
                + System.lineSeparator()
                + "s,banana,100"
                + System.lineSeparator()
                + "p,banana,13"
                + System.lineSeparator()
                + "r,apple,10"
                + System.lineSeparator()
                + "p,apple,20"
                + System.lineSeparator()
                + "p,banana,5"
                + System.lineSeparator()
                + " s,banana,50";
        try {
            Files.write(new File(FILE_FROM).toPath(), okFileContent.getBytes());
        } catch (IIOException e) {
            throw new RuntimeException("Can`t write to file" + FILE_FROM);
        }
    }

    @Test
    public void processReport_ok() {
        dataProcess.processReport(FILE_FROM, FILE_TO);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        List<String> actualReport;
        try {
            actualReport = Files.readAllLines(new File(FILE_TO).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Cann`t read file " + FILE_TO, e);
        }
        assertEquals(expected, actualReport);
    }

    @AfterClass
    public static void afterClass() {
        File fileFrom = new File(FILE_FROM);
        fileFrom.delete();
        File fileTo = new File(FILE_TO);
        fileTo.delete();
        FruitsStorage.getFruits().clear();
    }
}
