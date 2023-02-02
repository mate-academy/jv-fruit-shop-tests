package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriteService;
import core.basesyntax.service.FileWriteServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriteServiceImplTest {
    private static final String ERROR_MESSAGE = "Can`t read data from files!";
    private static FileWriteService report;

    @BeforeClass
    public static void setUp() {
        report = new FileWriteServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToCsvFile_noValidPath_notOk() {
        String report = "It`s some message";
        String toFileName = "src/mai/resources/toFile.csv";
        File toFile = new File(toFileName);
        FileWriteServiceImplTest.report.writeToFile(report, toFile);
    }

    @Test
    public void writeReportToCsvFile_validData_ok() {
        String report = "It`s a message";
        String toFileNameExpected = "src/test/resources/toFileTestExpected.csv";
        String toFileNameActual = "src/test/resources/toFileTest.csv";
        File toFile = new File(toFileNameActual);
        FileWriteServiceImplTest.report.writeToFile(report, toFile);
        try {
            assertEquals(Files.readAllLines(Path.of(toFileNameExpected)).toString(),
                    Files.readAllLines(Path.of(toFileNameActual)).toString());
        } catch (IOException e) {
            throw new RuntimeException(ERROR_MESSAGE, e);
        }
    }
}
