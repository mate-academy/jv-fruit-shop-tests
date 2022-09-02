package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String FILE_NAME = "src/test/resources/writerServResult.csv";
    private static WriterService writerService;

    private static String getReportForTest() {
        return "fruit,quantity,banana,125";
    }

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_FileNameIsNull_ThrowRuntimeExc() {
        writerService.writeToFile(null, getReportForTest());
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_ReportNull_ThrowRuntimeExc() {
        writerService.writeToFile(FILE_NAME, null);
    }

    @Test
    public void writeToFile_writeToFile_ok() {
        writerService.writeToFile(FILE_NAME, "Done it");
        String actual = "";
        try {
            actual = Files.readAllLines(Path.of(FILE_NAME)).stream()
                    .flatMap(String::lines).collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Cant read this file " + FILE_NAME, e);
        }
        String expected = "Done it";
        assertEquals(expected, actual);
    }
}
