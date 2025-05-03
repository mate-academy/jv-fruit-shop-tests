package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.servise.ReaderService;
import core.basesyntax.servise.WriterService;
import core.basesyntax.servise.impl.CsvFileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    private static ReaderService readerServicesForTest;
    private String pathOutFile;

    @BeforeAll
    public static void setUp() {
        writerService = new CsvFileWriterServiceImpl();

        readerServicesForTest = pathInnFile -> {
            try (Stream<String> streamFromFile = Files.lines(Paths.get(pathInnFile))) {
                return streamFromFile.collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
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

        assertEquals(List.of(REPORT.split(LINE_SEPARATOR)),
                readerServicesForTest.readFromFile(pathOutFile));
    }
}
