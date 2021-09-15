package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FileDaoCsv;
import core.basesyntax.dao.FileDaoCsvImpl;
import core.basesyntax.filewriter.FileWriterImpl;
import core.basesyntax.filewriter.WriteIntoFile;
import core.basesyntax.operationprovider.DataProcessorImpl;
import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.Operations;
import core.basesyntax.service.operationhandler.PurchaseOperationHandler;
import core.basesyntax.service.operationhandler.ReturnOperationHandler;
import core.basesyntax.service.operationhandler.SupplyOperationHandler;
import core.basesyntax.service.operationstrategy.OperationStrategyImpl;
import core.basesyntax.service.reportdb.ReportDataStoragePerMapImpl;
import core.basesyntax.validator.Validator;
import core.basesyntax.validator.ValidatorImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitShopTest {
    private static DataProcessorImpl dataProcessor;
    private static ArrayList<String> correctInput;
    private static HashMap<String, Integer> correctOutput;
    private static ArrayList<String> invalidInput1;
    private static ArrayList<String> invalidInput2;
    private static OperationStrategyImpl operationStrategy;
    private static ReportDataStoragePerMapImpl reportDataStorage;
    private static final String INVALID_DATA_FILE = "file_incorrect_1_name";
    private static final String VALID_DATA_FILE = "report.csv";
    private FileDaoCsv fileDaoCsv = new FileDaoCsvImpl();
    private Validator validator = new ValidatorImpl();
    private WriteIntoFile fileWriter = new FileWriterImpl();

    @BeforeAll
    public static void beforeAll() {
        OperationHandler supplyOperationHandler = new SupplyOperationHandler();
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        OperationHandler returnOperationHandler = new ReturnOperationHandler();
        Map<String, OperationHandler> operationHandlerMap = new HashMap();
        operationHandlerMap.put(String.valueOf(Operations.s), supplyOperationHandler);
        operationHandlerMap.put(String.valueOf(Operations.b), balanceOperationHandler);
        operationHandlerMap.put(String.valueOf(Operations.r), returnOperationHandler);
        operationHandlerMap.put(String.valueOf(Operations.p), purchaseOperationHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        reportDataStorage = new ReportDataStoragePerMapImpl(new HashMap<>());
        correctInput = new ArrayList<>();
        dataProcessor = new DataProcessorImpl();
        correctInput.add("type,fruit,quantity");
        correctInput.add("b,banana,150");
        correctInput.add("b,apple,100");
        correctInput.add("s,banana,100");
        correctInput.add("p,banana,13");
        correctInput.add("r,apple,10");
        correctInput.add("p,apple,20");
        correctInput.add("p,banana,5");
        correctInput.add("s,banana,100");
        invalidInput1 = new ArrayList<>();
        invalidInput1.add("type,fruit,quantity");
        invalidInput1.add("b,banana,150");
        invalidInput1.add("b,apple,100");
        invalidInput1.add("s,banana,");
        invalidInput2 = new ArrayList<>();
        invalidInput2.add("type,fruit,quantity");
        invalidInput2.add("b,banana,150");
        invalidInput2.add("b,apple,100");
        invalidInput2.add("s,banana,-20");
        correctOutput = new HashMap<>();
        correctOutput.put("banana", 332);
        correctOutput.put("apple", 90);
    }

    @Test
    void wrongFileName() {
        assertThrows(RuntimeException.class, () -> fileDaoCsv.getData(INVALID_DATA_FILE));
    }

    @Test
    void correctDataFromFile() {
        List<String> actual = fileDaoCsv.getData(VALID_DATA_FILE);
        assertEquals(correctInput, actual, "Reading from file result is incorrect");
    }

    @Test
    void incorrectDataFromFile() {
        boolean expected = false;
        boolean actual = validator.validate(invalidInput1);
        assertEquals(expected, actual, "Data validation is incorrect (no number in input file");
    }

    @Test
    void incorrectDataFromFileNegativeNumber() {
        boolean expected = false;
        boolean actual = validator.validate(invalidInput2);
        assertEquals(expected, actual, "Data validation is incorrect (number is negative");
    }

    @Test
    void operationSupply() {
        OperationHandler actual = operationStrategy.getOperationHandler("s");
        OperationHandler expected = new SupplyOperationHandler();
        assertEquals(expected.getClass(), actual.getClass(), "OperationStrategy works incorrect "
                + "with SupplyOperationHandler");
    }

    @Test
    void operationBalance() {
        OperationHandler actual = operationStrategy.getOperationHandler("b");
        OperationHandler expected = new BalanceOperationHandler();
        assertEquals(expected.getClass(), actual.getClass(), "OperationStrategy works incorrect "
                + "with SupplyOperationHandler");
    }

    @Test
    void operationPurchase() {
        OperationHandler actual = operationStrategy.getOperationHandler("p");
        OperationHandler expected = new PurchaseOperationHandler();
        assertEquals(expected.getClass(), actual.getClass(), "OperationStrategy works incorrect "
                + "with PurchaseOperationHandler");
    }

    @Test
    void supplyOperation() {
        OperationHandler actual = operationStrategy.getOperationHandler("s");
        OperationHandler expected = new SupplyOperationHandler();
        assertEquals(expected.getClass(), actual.getClass(), "OperationStrategy works incorrect "
                + "with SupplyOperationHandler");
    }

    @Test
    void incorrectOperation() {
        OperationHandler actual = operationStrategy.getOperationHandler("f");
        assertEquals(null, actual, "OperationStrategy works incorrect with Incorrect Data");
    }

    @Test
    void calculationOperation() {
        List<String> correctInput1 = new ArrayList<>();
        correctInput1.addAll(correctInput);
        correctInput1.remove(0);
        dataProcessor.handleInput(correctInput1, operationStrategy, reportDataStorage);
        assertEquals(correctOutput.entrySet(), reportDataStorage.getAllData(), "OperationStrategy "
                + "works incorrect with SupplyOperationHandler");
    }

    @Test
    void wrongFileWrite() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.writeInFile(reportDataStorage.getAllData(), ""));
    }
}
