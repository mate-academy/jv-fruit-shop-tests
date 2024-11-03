package core.basesyntax;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverterService;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MainTest {

    @Mock
    private FileReaderService fileReader;

    @Mock
    private DataConverterService dataConverter;

    @Mock
    private OperationStrategy operationStrategy;

    @Mock
    private ShopService shopService;

    @Mock
    private ReportGenerator reportGenerator;

    @Mock
    private FileWriterService fileWriter;

    @InjectMocks
    private Main main;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        main = new Main(
                fileReader,
                dataConverter,
                operationStrategy,
                shopService,
                reportGenerator,
                fileWriter
        );
    }

    @Test
    public void testRun_ok() {
        String inputFilePath = "input.csv";
        List<String> mockInputData = List.of("type,fruit,quantity", "b,apple,100");
        List<FruitTransaction> mockTransactions =
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        String mockReport = "fruit,quantity\napple,100";

        when(fileReader.read(inputFilePath)).thenReturn(mockInputData);
        when(dataConverter.convertToTransaction(mockInputData)).thenReturn(mockTransactions);
        when(reportGenerator.create()).thenReturn(mockReport);

        String outputFilePath = "output.csv";

        main.run(inputFilePath, outputFilePath);

        verify(fileReader).read(inputFilePath);
        verify(dataConverter).convertToTransaction(mockInputData);
        verify(shopService).process(mockTransactions);
        verify(reportGenerator).create();
        verify(fileWriter).writeToFile(outputFilePath, mockReport);
    }
}
