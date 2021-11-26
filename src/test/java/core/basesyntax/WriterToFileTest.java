package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterToFile;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterToFileImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterToFileTest {
    private static WriterToFile writer;
    private static ReportService report;
    private static String expected;
    private static String actual;
    private static final String VALID_PATH_TO_FILE = "src/test/files/resultCorrectTest";
    private static final String RESULT_WITH_EMPTY_DATABASE =
            "src/test/files/resultEmptyDataBaseTest";
    private static final String INVALID_PATH_TO_FILE = "src/test/files1/resultWriterService";
    private static final String PATH_TO_CORRECT_FILE = "src/test/files/correctPathToFile";
    private static final String PATH_TO_EMPTY_DATABASE_FILE = "src/test/files/emptyDataBaseFile";

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterToFileImpl();
        report = new ReportServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeData_incorrectPathToFile_notOk() {
        writer.writeData(INVALID_PATH_TO_FILE, report.getReport());
    }

    @Test
    public void writeData_correctPathToFile_ok() {
        Storage.getDataBase().put(new Fruit("banana"), 10);
        expected = readFromFile(PATH_TO_CORRECT_FILE);
        writer.writeData(VALID_PATH_TO_FILE, report.getReport());
        actual = readFromFile(VALID_PATH_TO_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeData_withEmptyDataBase_ok() {
        Storage.getDataBase().clear();
        expected = readFromFile(PATH_TO_EMPTY_DATABASE_FILE);
        writer.writeData(RESULT_WITH_EMPTY_DATABASE, report.getReport());
        actual = readFromFile(RESULT_WITH_EMPTY_DATABASE);
        Assert.assertEquals(expected, actual);
    }

    private String readFromFile(String path) {
        File file = new File(path);
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append(System.lineSeparator());
                line = reader.readLine();
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file" + e);
        }
    }

    @AfterClass
    public static void afterClass() {
        Storage.getDataBase().clear();
    }
}
