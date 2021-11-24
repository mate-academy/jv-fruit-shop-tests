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

public class WriterToFileImplTest {
    private static WriterToFile writer;
    private static ReportService report;
    private static String expected;
    private static String validPathToFile;
    private static String invalidPathToFile;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterToFileImpl();
        report = new ReportServiceImpl();
        Storage.getDataBase().put(new Fruit("banana"), 10);
        validPathToFile = "src/test/files/resultWriterService";
        invalidPathToFile = "src/test/files1/resultWriterService";
        expected = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator();
    }

    @Test(expected = RuntimeException.class)
    public void writeData_incorrectPathToFile_notOk() {
        writer.writeData(invalidPathToFile, report.getReport());
    }

    @Test
    public void writeData_correctPathToFile_ok() {
        writer.writeData(validPathToFile, report.getReport());
        Assert.assertEquals(expected, readFromFile(validPathToFile));
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
            file.delete();
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file" + e);
        }
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getDataBase().clear();
    }
}
