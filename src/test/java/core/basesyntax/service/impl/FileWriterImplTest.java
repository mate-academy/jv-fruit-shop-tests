package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String EMPTY_LINE = " ";
    private static final String TEST_REPORT = "test,written,text,for,empty,file,name";
    private static final String TEST_FILE_NAME = "src/test/resources/test_file.csv";
    private static final String TEST_FILE_NAME2 = "src/test/resources/test_file2.csv";
    private Writer fileWriter = new FileWriterImpl();

    @Test(expected = RuntimeException.class)
    public void emptyFileName_NotOk() {
        fileWriter.write("", TEST_REPORT);
    }

    @Test
    public void emptyWriteData_Ok() {
        createAndReadFromFile(TEST_FILE_NAME, EMPTY_LINE);
    }

    @Test
    public void regularData_Ok() {
        createAndReadFromFile(TEST_FILE_NAME2, TEST_REPORT);
    }

    private void createAndReadFromFile(String fileName, String report) {
        File resultFile = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Could not write in file " + fileName, e);
        }
        Path testFilePath = Path.of(fileName);
        List<String> actual;
        try {
            actual = Files.readAllLines(testFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not read from file " + fileName, e);
        }
        List<String> expected = new ArrayList<>();
        expected.add(report);
        Assert.assertEquals(expected, actual);
    }
}
