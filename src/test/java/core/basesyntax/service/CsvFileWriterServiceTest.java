package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.service.impl.CsvFileReporterServiceImpl;
import core.basesyntax.service.impl.CsvFileUniqueFruitsServiceAdderImpl;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import core.basesyntax.service.impl.OperationStrategyServiceImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceTest {
    private static final String VALID_FILE_PATH = "src/test/resources/input.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final String FILE_TO_WRITE = "src/test/resources/output.csv";
    private static final Map<FruitTransaction.Operation, OperationHandler> operationProviderMap =
            Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    private OperationStrategyService operationStrategy =
            new OperationStrategyServiceImpl(operationProviderMap);
    private Storage storage = new Storage();

    @Test
    void write_getValidReport_Ok() {
        FileReaderService fileReader = new CsvFileReaderServiceImpl();
        List<String> transactions = fileReader.readFile(new File(VALID_FILE_PATH));
        UniqueFruitsAdderService uniqueFruitsAdder =
                new CsvFileUniqueFruitsServiceAdderImpl(storage);
        uniqueFruitsAdder.add(transactions, storage);
        FileReporterService fileReporter =
                new CsvFileReporterServiceImpl(operationStrategy, storage);
        fileReporter.getReport(transactions);
        FileWriterService fileWriter = new CsvFileWriterServiceImpl();
        String actual = fileWriter.write(new File(FILE_TO_WRITE), storage.getFruitQuantityMap());
        String expected = """
                fruit,quantity
                banana,152
                apple,90
                """;
        assertEquals(expected, actual);
    }
}
