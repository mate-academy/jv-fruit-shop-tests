package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.servise.WriterService;
import core.basesyntax.servise.impl.CsvFileWriterServiceImpl;
import core.basesyntax.testclasses.CsvFileReaderServiceForTest;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileWriterServiceTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String REPORT = "fruit,quantity"
            + LINE_SEPARATOR + "banana,152"
            + LINE_SEPARATOR + "apple,90";
    private static final String EMPTY_REPORT = "";
    private static WriterService writerService;
    private String pathOutFile;

    @BeforeAll
    public static void setUp() {
        writerService = new CsvFileWriterServiceImpl();
    }

    @BeforeEach
    public void beforeTest() {
        pathOutFile = "src/test/resources/report.csv";
    }

    @Test
    public void csvFileWriterService_pathOutFileNull_notOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(null, REPORT));
    }

    @Test
    public void csvFileWriterService_invalidPathOutFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile("", REPORT));
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile("src/test/resources/", REPORT));
    }

    @Test
    public void csvFileWriterService_emptyReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> writerService.writeToFile(pathOutFile, EMPTY_REPORT));
    }

    @Test
    public void csvFileWriterService_reportNull_notOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(pathOutFile, null));
    }

    @Test
    public void csvFileWriterService_writeToFile_Ok() {
        writerService.writeToFile(pathOutFile, REPORT);
        List<String> actual = new CsvFileReaderServiceForTest().readFromFile(pathOutFile);
        assertEquals(List.of(REPORT.split(LINE_SEPARATOR)), actual);
    }
}
