package core.basesyntax;

import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import core.basesyntax.parse.DataParserImpl;
import core.basesyntax.readdata.DataReaderImpl;
import core.basesyntax.report.ReportDataImpl;
import core.basesyntax.storage.DataBase;
import core.basesyntax.storage.DataBaseImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.writedata.DataWriter;
import core.basesyntax.writedata.DataWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShopWorkingTest {
    private static final String APPLES_ORANGES_REPORT = "applesOrangesReport.csv";
    private static final String APPLES_GRAPES_REPORT = "applesGrapesReport.csv";
    private static final String APPLES_BANANAS_REPORT = "applesBananasReport.csv";
    private static final String BANANAS_ORANGES_REPORT = "bananasOrangesReport.csv";
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static DataBase dataBase;

    @After
    public void clearData() {
        operationHandlerMap.clear();
        try {
            Files.deleteIfExists(Path.of(APPLES_ORANGES_REPORT));
            Files.deleteIfExists(Path.of(APPLES_GRAPES_REPORT));
            Files.deleteIfExists(Path.of(APPLES_BANANAS_REPORT));
            Files.deleteIfExists(Path.of(BANANAS_ORANGES_REPORT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() throws Exception {
        dataBase = new DataBaseImpl();
        operationHandlerMap = newHashMap((DataBaseImpl) dataBase);
    }

    @Test
    public void getStatisticAboutApplesOranges_Ok() {
        String actualResult = getActualResult("applesOranges.csv", APPLES_ORANGES_REPORT);
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "orange,75" + System.lineSeparator()
                + "apple,99";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getStatisticAboutApplesGrapes_Ok() {
        String actualResult = getActualResult("applesGrapes.csv", APPLES_GRAPES_REPORT);
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "apple,111" + System.lineSeparator()
                + "grape,14";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getStatisticAboutApplesBananas_Ok() {
        String actualResult = getActualResult("applesBananas.csv", APPLES_BANANAS_REPORT);
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,100";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getStatisticAboutBananasOranges_Ok() {
        String actualResult = getActualResult("bananasOranges.csv", BANANAS_ORANGES_REPORT);
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,185" + System.lineSeparator()
                + "orange,75";
        Assert.assertEquals(expectedResult, actualResult);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }

    private String getActualResult(String dataFile, String reportFile) {
        List<String> dataFromFile = new DataReaderImpl().readData(dataFile);
        List<FruitTransaction> transactions = new DataParserImpl().parse(dataFromFile);
        processingData(transactions);
        String dataReport = new ReportDataImpl().createDataReport(dataBase.getAll());
        DataWriter dataWriter = new DataWriterImpl();
        dataWriter.writeData(reportFile, dataReport);
        return readFromFile(reportFile).trim();
    }

    private static Map<FruitTransaction.Operation,
            OperationHandler> newHashMap(DataBaseImpl dataBase) {
        Map<FruitTransaction.Operation, OperationHandler> operationHandler = new HashMap<>();
        operationHandler.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(dataBase));
        operationHandler.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(dataBase));
        operationHandler.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(dataBase));
        operationHandler.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(dataBase));
        return operationHandler;
    }

    public static void processingData(List<FruitTransaction> transactions) {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactions.forEach(f -> operationStrategy.get(f.getOperation())
                .processingOperation(f));
    }

}
