package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_NonValidInputData_NotOk() {
        writerService.writeToFile("", "");
    }

    @Test
    public void writeToFile_ValidInputData_Ok() {
        String report = "report for file";
        String fileName = "src/test/resources/testReport.csv";
        writerService.writeToFile(report, fileName);
        String expected = null;
        try {
            expected = Files.readAllLines(Path.of(fileName)).stream().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("", expected, report);
    }
}
