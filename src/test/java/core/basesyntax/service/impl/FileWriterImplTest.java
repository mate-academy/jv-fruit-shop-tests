package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    public static final String PATH = "src/test/resources/testReportFile.csv";
    public static final String INCORRECT_PATH = "src/test/resour/testReportFile.csv";
    private static final FileWriter fileWriter = new FileWriterImpl();
    private String report;

    @Before
    public void createReport() {
        report = "fruit,quantity" + System.lineSeparator()
                + "banana" + "," + 152 + System.lineSeparator()
                + "apple" + "," + 90;
    }

    @Test
    public void writeData_ok() {
        fileWriter.write(report, PATH);
        List<String> expected = List.of("fruit,quantity", "banana,152", "apple,90");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data: " + PATH);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeFile_incorrectPath_notOk() {
        try {
            fileWriter.write(report, INCORRECT_PATH);
        } catch (RuntimeException e) {
            return;
        }
        fail("Storage was empty");
    }
}
