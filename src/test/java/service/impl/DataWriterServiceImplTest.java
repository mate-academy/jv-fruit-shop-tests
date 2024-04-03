package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.handler.BalanceHandler;
import service.handler.OperationHandler;
import service.handler.PurchaseHandler;
import service.handler.ReturnHandler;
import service.handler.SupplyHandler;

class DataWriterServiceImplTest {
    private static DataReaderServiceImpl dataReaderService;
    private static DataWriterServiceImpl dataWriterService;
    private static ProcessorServiceImpl processorService;
    private static final String PATH_FILE_TO_READ = "src/main/java/resources/input.txt";
    private static final String PATH_FILE_TO_WRIGHT = "src/main/java/resources/output.txt";
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

    @Test
    public void checkNullInPathToWriteFile() {

        assertThrows(RuntimeException.class,
                () -> dataWriterService.writeProcessedDataToFile(null));
    }

    @Test
    public void checkNullInFilePath() {
        assertThrows(RuntimeException.class, () -> dataWriterService.writeProcessedDataToFile(null));
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