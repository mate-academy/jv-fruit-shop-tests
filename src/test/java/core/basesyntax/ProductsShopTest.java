package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import db.Storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.handler.BalanceHandler;
import service.handler.OperationHandler;
import service.handler.PurchaseHandler;
import service.handler.ReturnHandler;
import service.handler.SupplyHandler;
import service.impl.DataReaderServiceImpl;
import service.impl.DataWriterServiceImpl;
import service.impl.ProcessorServiceImpl;

class ProductsShopTest {

    private static DataReaderServiceImpl dataReaderService;
    private static DataWriterServiceImpl dataWriterService;
    private static ProcessorServiceImpl processorService;
    private static final String EMPTY_FILE = "src/main/java/resources/empty.txt";
    private static final String PATH_FILE_TO_READ = "src/main/java/resources/input.txt";
    private static final String PATH_FILE_TO_WRIGHT = "src/main/java/resources/output.txt";
    private static final String PATH_TO_INCORRECT_FILE = "src/main/java/resources/file.txt";
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;
    private static final String SEPARATOR = "\n";

    @BeforeAll
    public static void setHandlerMap() {
        handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE,
                new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(),
                FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(),
                FruitTransaction.Operation.RETURN,
                new ReturnHandler()
        );
    }

    @BeforeEach
      public void setObjects() {
        dataReaderService = new DataReaderServiceImpl();
        dataWriterService = new DataWriterServiceImpl();
        processorService = new ProcessorServiceImpl();
    }

    @AfterEach
    public void clearStorage() {
        Storage.getFruitStorage().clear();
    }

    @Test
      public void checkReadFileWithIncorectPath() {

        String path = "file.txt";

        assertThrows(RuntimeException.class,
                () -> dataReaderService.readDataInFile(path),
                "Was correct file path!");
        assertThrows(RuntimeException.class,
                () -> dataReaderService.readDataInFile(PATH_TO_INCORRECT_FILE),
                "File was in resources of file");
        assertThrows(RuntimeException.class,
                () -> dataReaderService.readDataInFile(null),
                "File was in resources of file");
    }

    @Test
      public void checkCorrectReadingData() {

        List<FruitTransaction> fruitTransactionList
                = dataReaderService.readDataInFile(PATH_FILE_TO_READ);
        String fruitFirstPosition = "apple";
        int quantityFirstPosition = 100;
        FruitTransaction.Operation thirdPosition
                = FruitTransaction.Operation.PURCHASE;

        assertEquals(fruitTransactionList
                .get(1)
                .getFruit(), fruitFirstPosition);
        assertEquals(fruitTransactionList
                .get(1)
                .getQuantity(), quantityFirstPosition);
        assertEquals(fruitTransactionList.get(3).getOperation(),
                thirdPosition);
    }

    @Test
    public void checkEmptyFileForReading() {

        List<FruitTransaction> fruitTransactionList =
                dataReaderService.readDataInFile(EMPTY_FILE);

        assertTrue(fruitTransactionList.isEmpty());
    }

    @Test
    public void checkProcessorServiceCorrectWork() {
        Map<String, Integer> correct = Map.of(
                "banana",152,
                "apple",90
        );

        processorService.processOnData(dataReaderService
                .readDataInFile(PATH_FILE_TO_READ), handlerMap);

        assertEquals(correct, Storage.getFruitStorage());
    }

    @Test
    public void checkEmptyProcessorServiceReport() {
        Map<String, Integer> correct = new HashMap<>();
        processorService.processOnData(dataReaderService
                .readDataInFile(EMPTY_FILE), handlerMap);

        assertEquals(correct, Storage.getFruitStorage());
    }

    @Test
    public void checkPurchaseOperation() {

        List<FruitTransaction> fruitTransactionList =
                new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,
                "banana", 123));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE,
                "banana", 23));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 100;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }

    @Test
    public void checkSupplyOperation() {

        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,
                "banana", 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.SUPPLY,
                "banana", 80));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 100;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }

    @Test
    public void checkReturnOperation() {

        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,
                "banana", 130));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.RETURN,
                "banana", 70));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 200;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }

    @Test
    public void checkNullInPathToWriteFile() {

        assertThrows(RuntimeException.class,
                () -> dataWriterService.writeProcessedDataToFile(null));
    }

    @Test
    public void correctWriteDataToFile() {
        String correct = "fruit,quantity" + SEPARATOR
                + "banana,152" + SEPARATOR
                + "apple,90" + SEPARATOR;
        processorService.processOnData(dataReaderService
                        .readDataInFile(PATH_FILE_TO_READ),
                handlerMap);
        dataWriterService.writeProcessedDataToFile(PATH_FILE_TO_WRIGHT);
        try {
            String actual = new String(Files.readAllBytes(Path.of(PATH_FILE_TO_WRIGHT)));
            assertEquals(actual,correct);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
