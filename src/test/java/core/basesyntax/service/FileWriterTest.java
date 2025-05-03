package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.CsvWriterImpl;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static FileWriterService fileWriterService;
    private static File testReportFile;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new CsvWriterImpl(
                new ReportCreatorServiceImpl(new StorageDaoImpl())
        );
        testReportFile = new File("./src/resources/test_report.csv");
    }

    @Before
    public void setUp() {
        testReportFile.delete();
    }

    @Test
    public void writeStringToFile_Ok() {
        String expected = "string was written to file successfully";
        try {
            testReportFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("cant create new test file");
        }
        fileWriterService.writeStringToFile(testReportFile, expected);
        String actual = null;
        try {
            actual = Files.readAllLines(testReportFile.toPath()).get(0);
        } catch (IOException e) {
            throw new RuntimeException("cant read test file or file is empty");
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeStringToFile_notOk() {
        fileWriterService.writeStringToFile(testReportFile, "test string");
    }

    @Test
    public void writeToFile_Ok() {
        storage.add(new Fruit("banana", 100));
        storage.add(new Fruit("orange", 10));
        fileWriterService.writeToFile(testReportFile.toPath().toString());
        final String expectedLine1 = "fruit,quantity";
        final String expectedLine2 = "banana,100";
        final String expectedLine3 = "orange,10";
        List<String> dataFromReportFile = null;
        try {
            dataFromReportFile = Files.readAllLines(testReportFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String actualLine1 = dataFromReportFile.get(0);
        String actualLine2 = dataFromReportFile.get(1);
        String actualLine3 = dataFromReportFile.get(2);
        assertEquals(expectedLine1, actualLine1);
        assertEquals(expectedLine2, actualLine2);
        assertEquals(expectedLine3, actualLine3);
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
