package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FileWriterService;
import core.basesyntax.dao.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String ERROR_MESSAGE = "Can`t read data from files!";
    private static FileWriterService report;

    @BeforeClass
    public static void setUp() {
        report = new FileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToCsvFile_noValidPath_notOk() {
        String report = "It`s some message";
        String toFileName = "src/mai/resources/toFile.csv";

        FileWriterServiceImplTest.report.writeToFile(report, toFileName);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToCsvFile_validData_ok() {
        String report = "It`s a message";
        String toFileNameExpected = "src/test/resources/toFileTestExpected.csv";
        String toFileNameActual = "src/test/resources/toFileTest.csv";
        String toFileName = "src/mai/resources/toFile.csv";
        FileWriterServiceImplTest.report.writeToFile(report, toFileName);
        try {
            assertEquals(Files.readAllLines(Path.of(toFileNameExpected)).toString(),
                    Files.readAllLines(Path.of(toFileNameActual)).toString());
        } catch (IOException e) {
            throw new RuntimeException(ERROR_MESSAGE, e);
        }
    }
}
