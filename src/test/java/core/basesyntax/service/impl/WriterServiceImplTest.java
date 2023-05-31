package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String fileName = "src/test/resources/testReport.csv";
    private static final String report = "report for file";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_InputDataIsNotValid_NotOk() {
        writerService.writeToFile(report, "");
    }

    @Test
    public void writeToFile_InputDataIsValid_Ok() {
        writerService.writeToFile(report, fileName);
        String expected = null;
        try {
            expected = Files.readAllLines(Path.of(fileName)).stream().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Can't read for test from " + fileName, e);
        }
        assertEquals(expected, report);
    }

    @After
    public void tearDown() {
        if (Files.exists(Path.of(fileName))) {
            try {
                Files.delete(Path.of(fileName));
            } catch (IOException e) {
                throw new RuntimeException("Can't delete file " + fileName, e);
            }
        }
    }
}
