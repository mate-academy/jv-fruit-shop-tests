package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class ReportDaoImplTest {
    private final ReportDao reportDao = new ReportDaoImpl();

    @Test(expected = RuntimeException.class)
    public void writeReportToCsvFile_noValidPath_NotOk() {
        String report = "It`s some message";
        String toFileName = "src/mai/resources/toFile.csv";
        File toFile = new File(toFileName);
        reportDao.writeReportToCsvFile(report, toFile);
    }

    @Test
    public void writeReportToCsvFile_validData_Ok() {
        String report = "It`s a message";
        String toFileNameExpected = "src/test/resources/toFileTestExpected.csv";
        String toFileNameActual = "src/test/resources/toFileTest.csv";
        File toFile = new File(toFileNameActual);
        reportDao.writeReportToCsvFile(report, toFile);
        try {
            assertEquals(Files.readAllLines(Path.of(toFileNameExpected)).toString(),
                    Files.readAllLines(Path.of(toFileNameActual)).toString());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
