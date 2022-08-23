package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MainTest {
    private static ParserService parserService;
    private static ReaderService readerService;
    private static ReportService reportService;
    private static WriterService writerService;
    private static FruitDao fruitDao;
    @Rule
    public ExpectedException exceptionRuleFileNotFound = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
        parserService = new ParserServiceImpl();
        readerService = new ReaderServiceImpl();
        reportService = new ReportServiceImpl(fruitDao);
        writerService = new WriterServiceImpl();
    }

    @Test
    public void reader_FileNotFoundNotOK() {
        final String inputFile = "./src/main/resources/notFound.txt";
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Can't read the file " + inputFile);
        readerService.readFromCsvFile(inputFile);
    }

    @Test
    public void reader_FileIsEmptyNotOK() {
        final String inputFile = "./src/main/resources/empty.txt";
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("File " + inputFile + " is empty.");
        readerService.readFromCsvFile(inputFile);
    }

    @Test
    public void writer_FileNotFoundNotOK() {
        final String outputFile = "./src/main/notFound/someFile.txt";
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Can't write the file " + outputFile);
        writerService.writeToCsvFile("fruit,quantity", outputFile);
    }

    @Test
    public void parser_InpuIstNullNotOK() {
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Input param is null.");
        parserService.parse(null);
    }

    @Test
    public void parser_InpuIsEmptyNotOK() {
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Input param is empty.");
        parserService.parse(List.of());
    }

    @Test
    public void parser_BadDataInFileNotOK() {
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Bad data in line: 2");
        parserService.parse(List.of("b,fruit,1", "b,,1"));
    }
}
