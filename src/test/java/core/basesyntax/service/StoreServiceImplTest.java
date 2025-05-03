package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.MyFileReader;
import core.basesyntax.dao.MyFileReaderList;
import core.basesyntax.dao.MyFileWriter;
import core.basesyntax.dao.MyFileWriterList;
import core.basesyntax.dao.RemnantsDao;
import core.basesyntax.dao.RemnantsDaoMap;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.service.operationhandler.BalanceHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.PurchaseHandler;
import core.basesyntax.service.operationhandler.ReturnHandler;
import core.basesyntax.service.operationhandler.SupplyHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StoreServiceImplTest {
    private static Map<OperationTypes, OperationHandler> operationHandlersMap;
    private final RemnantsDao remnantsDao = new RemnantsDaoMap();
    private final PathService pathService = new PathServiceImpl();
    private final MyFileReader myFileReader = new MyFileReaderList();
    private final MyFileWriter myFileWriter = new MyFileWriterList();
    private final OperationStrategy operationStrategy =
            new OperationStrategyImpl(operationHandlersMap);
    private final StoreService storeService = new StoreServiceImpl(
            remnantsDao, pathService, myFileReader, myFileWriter, operationStrategy);

    @BeforeClass
    public static void beforeClass() {
        System.out.println("method beforeClass()");
        operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(OperationTypes.BALANCE, new BalanceHandler());
        operationHandlersMap.put(OperationTypes.SUPPLY, new SupplyHandler());
        operationHandlersMap.put(OperationTypes.PURCHASE, new PurchaseHandler());
        operationHandlersMap.put(OperationTypes.RETURN, new ReturnHandler());
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("method setUp()");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("method tearDown()");
    }

    @Test
    public void getOperationHandler_unknown_ok() {
        OperationTypes operationType = OperationTypes.UNKNOWN;
        try {
            operationStrategy.getOperationHandler(operationType);
        } catch (ValidationException e) {
            return;
        }
        fail("ValidationException must be thrown for operationType=OperationTypes.UNKNOWN");
    }

    @Test
    public void process_generate_compare_DBandFile_ok() {
        List<String> listForInput = new ArrayList<>();
        listForInput.add("type,fruit,quantity");
        listForInput.add("b,banana,20");
        listForInput.add("b,apple,100");
        listForInput.add("s,banana,100");
        listForInput.add("p,banana,13");
        listForInput.add("r,apple,10");
        listForInput.add("p,apple,20");
        listForInput.add("p,banana,5");
        listForInput.add("s,banana,50");
        DateService dateService = new DateServiceImpl();
        String currentDateString = dateService.getCurrentDateString();
        String inputFullPath = pathService.getInputFullPath(currentDateString);
        myFileWriter.writeDataToFile(listForInput, inputFullPath);
        storeService.processTodaysInputFile();
        storeService.generateTodaysReportFile();
        List<String> listFromDB = remnantsDao.getRemnantsReportList();
        listFromDB.add(0, "fruit,quantity");
        String dateOfReport = dateService.getCurrentDateString();
        String reportFullPath = pathService.getReportFullPath(dateOfReport);
        File reportFile = new File(reportFullPath);
        List<String> listFromReportFile = myFileReader.readDataFromFile(reportFile);
        assertEquals(listFromDB, listFromReportFile);
    }

    @Test
    public void process_generate_getFromDB_ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("banana,152");
        expectedList.add("apple,90");
        storeService.processTodaysInputFile();
        storeService.generateTodaysReportFile();
        List<String> listFromDB = remnantsDao.getRemnantsReportList();
        assertEquals(expectedList, listFromDB);
    }

    @Test
    public void process_generate_getFromFile_ok() throws ValidationException {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("fruit,quantity");
        expectedList.add("banana,152");
        expectedList.add("apple,90");
        storeService.processTodaysInputFile();
        storeService.generateTodaysReportFile();
        DateService dateService = new DateServiceImpl();
        String reportFullPath = pathService.getReportFullPath(
                dateService.getCurrentDateString());
        File reportFile = new File(reportFullPath);
        List<String> listFromReportFile = myFileReader.readDataFromFile(reportFile);
        assertEquals(expectedList, listFromReportFile);
    }

    @Test
    public void process_generate_create_getFromFile_ok() throws ValidationException {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,152");
        expectedList.add("b,apple,90");
        storeService.processTodaysInputFile();
        storeService.generateTodaysReportFile();
        storeService.createInputFileForNextDay();
        DateService dateService = new DateServiceImpl();
        String inputFullPath = pathService.getInputFullPath(
                dateService.getNextDayDateString());
        File nextDayInputFile = new File(inputFullPath);
        List<String> nextInputFileList = myFileReader.readDataFromFile(nextDayInputFile);
        assertEquals(expectedList, nextInputFileList);
    }

    @Test
    public void processInputFile_unknownOperation_notOk() {
        DateService dateService = new DateServiceImpl();
        String currentDateString = dateService.getCurrentDateString();
        String inputFullPath = pathService.getInputFullPath(currentDateString);
        List<String> listWithUnknown = new ArrayList<>();
        listWithUnknown.add("w,kiwi,20");
        myFileWriter.writeDataToFile(listWithUnknown, inputFullPath);
        try {
            storeService.processTodaysInputFile();//file with unknownOperation
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException must be trown for input file with unknown operation!");
    }

}
