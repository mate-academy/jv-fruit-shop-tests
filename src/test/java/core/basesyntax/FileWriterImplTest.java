package core.basesyntax;

import core.basesyntax.filewriter.FileWriterImpl;
import core.basesyntax.filewriter.WriteIntoFile;
import core.basesyntax.service.reportdb.ReportDataStoragePerMapImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static HashMap<String, Integer> correctOutput;
    private static ReportDataStoragePerMapImpl reportDataStorage;
    private WriteIntoFile fileWriter = new FileWriterImpl();

    @BeforeClass
    public static void beforeAll() {
        correctOutput = new HashMap<>();
        correctOutput.put("banana", 332);
        correctOutput.put("apple", 90);
        reportDataStorage = new ReportDataStoragePerMapImpl(correctOutput);
    }

    @Test
    public void writeInFile_whenWrongFilePath_NotOK() {
        Assert.assertThrows(RuntimeException.class, () ->
                fileWriter.writeInFile(reportDataStorage.getAllData(), ""));
    }

    @Test
    public void writeInFile_whenCorrectFilePath_OK() {
        fileWriter.writeInFile(reportDataStorage.getAllData(), "result.csv");
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,332");
        expected.add("apple,90");
        List<String> actual = new ArrayList<>();
        try (FileReader fileReader = new FileReader("result.csv");
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                actual.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("Data into file written incorrect", expected, actual);
    }
}
