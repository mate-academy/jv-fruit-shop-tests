package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.ReadFromFileException;
import core.basesyntax.filework.CsvFileReaderImpl;
import core.basesyntax.filework.FileReader;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.parser.Parser;
import core.basesyntax.service.parser.ParserCsvImpl;
import core.basesyntax.service.strategy.DecreaseOperationHandler;
import core.basesyntax.service.strategy.IncreaseOperationHandler;
import core.basesyntax.service.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static FileReader reader;
    private static Parser parser;
    private static Map<OperationType, OperationHandler> operationHandlerMap;
    private static final String CORRECT_PATH_FOR_READ = "src/main/resources/fruit_shop.csv";
    private static final String INCORRECT_PATH_FOR_READ = "src/main/resources/fruits_shop.csv";
    private static final String HEADER = "fruit,quantity\n";
    private static final String CORRECT_REPORT_DATA = "fruit,quantity\n"
            + "banana,152\n"
            + "apple,90\n";

    @Before
    public void setUp() {
        ParserCsvImpl.fruitStore.clear();
        FruitStorage.storage.clear();
        reader = new CsvFileReaderImpl();
        fruitService = new FruitServiceImpl();
        parser = new ParserCsvImpl();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new IncreaseOperationHandler());
        operationHandlerMap.put(OperationType.SUPPLY, new IncreaseOperationHandler());
        operationHandlerMap.put(OperationType.RETURN, new IncreaseOperationHandler());
        operationHandlerMap.put(OperationType.PURCHASE, new DecreaseOperationHandler());
    }

    @Test
    public void fruitServiceCreateReportWithHeader_Ok() {
        String report = fruitService.createReport(operationHandlerMap);
        assertEquals(HEADER, report);
    }

    @Test
    public void fruitServiceCreateWholeReport_Ok() {
        String[] lines = reader.read(CORRECT_PATH_FOR_READ);
        parser.parse(lines);
        String report = fruitService.createReport(operationHandlerMap);
        assertEquals(report, CORRECT_REPORT_DATA);
    }

    @Test(expected = ReadFromFileException.class)
    public void fruitServiceCreateWholeReportIncorrectPath_Ok() {
        reader.read(INCORRECT_PATH_FOR_READ);
    }
}
