package core.basesyntax.service;

import static core.basesyntax.TestConstants.APPLE;
import static core.basesyntax.TestConstants.BANANA;
import static core.basesyntax.TestConstants.BUY_TWENTY_BANANAS_LINE;
import static core.basesyntax.TestConstants.HEADER;
import static core.basesyntax.TestConstants.TYPE_FRUIT_QUANTITY;
import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.repository.Storage;
import core.basesyntax.service.parser.CsvParser;
import core.basesyntax.service.reader.CsvReader;
import core.basesyntax.service.report.ReportGenerator;
import core.basesyntax.service.storage.StorageService;
import core.basesyntax.service.writer.CsvWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private CsvReader csvReader;
    private CsvParser csvParser;
    private StorageService storageService;
    private Storage storage;
    private ReportGenerator reportGenerator;
    private CsvWriter csvWriter;
    private FruitServiceImpl fruitService;

    @BeforeEach
    void setUp() {
        csvReader = mock(CsvReader.class);
        csvParser = mock(CsvParser.class);
        storageService = mock(StorageService.class);
        storage = mock(Storage.class);
        reportGenerator = mock(ReportGenerator.class);
        csvWriter = mock(CsvWriter.class);
        fruitService = new FruitServiceImpl(csvReader, csvParser, storageService,
                storage, reportGenerator, csvWriter);
    }

    @Test
    void processCsvFile_isOk() {
        List<String> lines = List.of(HEADER, TYPE_FRUIT_QUANTITY, BUY_TWENTY_BANANAS_LINE);
        List<FruitTransaction> fruits = List.of(
                new FruitTransaction(BALANCE, APPLE, 10),
                new FruitTransaction(SUPPLY, BANANA, 20)
        );
        Map<String, Integer> storageAll = Map.of(
                APPLE, 10,
                BANANA, 20
        );
        List<String> generated = List.of("Apple,10", "Banana,20");
        Path fromFile = mock(Path.class);

        when(csvReader.readFile(fromFile)).thenReturn(lines);
        when(csvParser.parseFruits(lines)).thenReturn(fruits);
        doNothing().when(storageService).transfer(fruits);
        when(storage.getAll()).thenReturn(storageAll);
        when(reportGenerator.generate(storageAll)).thenReturn(generated);
        Path toFile = mock(Path.class);
        fruitService.processCsvFile(fromFile, toFile);

        verify(csvReader).readFile(fromFile);
        verify(csvParser).parseFruits(lines);
        verify(storageService).transfer(fruits);
        verify(storage).getAll();
        verify(reportGenerator).generate(storageAll);
        verify(csvWriter).writeToFile(toFile, generated);
    }
}
