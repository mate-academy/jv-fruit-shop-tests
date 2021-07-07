package core.basesyntax.basesyntax.service;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllLines;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "vodka,350" + System.lineSeparator()
            + "apple,250";
    private static final String FILE_PATH = "src" + File.separator
            + "test" + File.separator
            + "resources" + File.separator
            + "reportTest.csv";
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writer_creatingfile_Ok() {
        fileWriter.writeToFile(REPORT, FILE_PATH);
        Assert.assertTrue(exists(Path.of(FILE_PATH)));
    }

    @Test
    public void writer_writeToExistFile_Ok() {
        List<String> expectedList = Arrays.asList(REPORT.split(System.lineSeparator()));
        List<String> actualList;
        try {
            actualList = readAllLines(Path.of(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file here: " + FILE_PATH, e);
        }
        fileWriter.writeToFile(REPORT,FILE_PATH);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void writer_writeToFileException_NotOk() {
        fileWriter.writeToFile(REPORT, "");
    }
}
