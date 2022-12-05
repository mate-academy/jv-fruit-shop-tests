package core.basesyntax.service.impl;

import core.basesyntax.service.FileWrite;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class FileWriteImplTest {
    private static final String FILE_NAME = "src/test/resources/report_file_test.csv";
    private static FileWrite fileWrite;
    private String report = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    @BeforeClass
    public static void beforeClass() {
        fileWrite = new FileWriteImpl();
    }

    @Test
    public void writeToFile_CorrectFileName_Ok() {
        fileWrite.writeToFile(report, FILE_NAME);
        String actual;
        try {
            actual = Files.readString(Path.of(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read date from file " + FILE_NAME, e);
        }
        Assert.assertEquals(report, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_IncorrectFileName_NotOk() {
        String fileName = "/";
        fileWrite.writeToFile(report, fileName);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_Null_NotOk() {
        fileWrite.writeToFile(report, null);
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't clear result files after test", e);
        }
    }
}
