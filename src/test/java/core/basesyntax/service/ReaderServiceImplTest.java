package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.operations.BalanceOperationHandlerImpl;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.PurchaseOperationHandlerImpl;
import core.basesyntax.operations.ReturnOperationHandlerImpl;
import core.basesyntax.operations.SupplyOperationHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceImplTest {

    private static ReaderService readerService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private final String wrongInputFilename = "error.txt";
    private List<String> storeData = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<Operation.Type, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.Type.BALANCE, new BalanceOperationHandlerImpl());
        operationHandlerMap.put(Operation.Type.PURCHASE, new PurchaseOperationHandlerImpl());
        operationHandlerMap.put(Operation.Type.SUPPLY, new SupplyOperationHandlerImpl());
        operationHandlerMap.put(Operation.Type.RETURN, new ReturnOperationHandlerImpl());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        FruitStorageDaoImpl fruitStorageDao = new FruitStorageDaoImpl();

        readerService = new ReaderServiceImpl(fruitStorageDao, operationStrategy);
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruits.clear();

        storeData.add("b,banana,20");
        storeData.add("b,apple,15");
        storeData.add("p,banana,13");
        storeData.add("r,apple,15");
        storeData.add("p,banana,5");
    }

    @Test
    public void readFromFileTest() {
        String inputFileName = "src/main/resources/store_operations.csv";
        List<String> storeData = readerService.readFromFile(inputFileName);
        int expectedListSize = 8;
        int actualListSize = storeData.size();
        assertEquals("Test failed! Quantity of file row should be " + expectedListSize
                + " but it is " + actualListSize, expectedListSize, actualListSize);
    }

    @Test
    public void readFromAbsentFile_RuntimeException() {
        exceptionRule.expect(RuntimeException.class);
        readerService.readFromFile(wrongInputFilename);
    }

    @Test
    public void packToStorageEmptyList() {
        storeData.clear();
        readerService.packToStorage(storeData);
        int expectedStorageSize = 0;
        int actualStorageSize = Storage.fruits.size();
        assertEquals("Test failed! Storage size should be " + expectedStorageSize
                + " but it is " + actualStorageSize, expectedStorageSize, actualStorageSize);
    }

    @Test
    public void packToStorageSomeData() {
        readerService.packToStorage(storeData);
        int expectedStorageSize = 2;
        int actualStorageSize = Storage.fruits.size();
        assertEquals("Test failed! Storage size should be " + expectedStorageSize
                + " but it is " + actualStorageSize, expectedStorageSize, actualStorageSize);
    }

    @Test
    public void packToStorageSomeEmptyData_ExceptionThrown() {
        exceptionRule.expect(RuntimeException.class);
        String expectedMessage = "The input file has incorrect data!";
        exceptionRule.expectMessage(expectedMessage);
        storeData.add("");
        readerService.packToStorage(storeData);
    }

    @Test
    public void packToStorageQuantityLessThanZero_ExceptionThrown() {
        exceptionRule.expect(RuntimeException.class);
        String expectedMessage = "Validation data didn't pass! Data is incorrect!";
        exceptionRule.expectMessage(expectedMessage);
        storeData.add("b,orange,-4");
        readerService.packToStorage(storeData);
    }
}
