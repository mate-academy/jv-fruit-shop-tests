package core.basesyntax.service.impl;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class WriterImplTest {
    private static final WriterImpl fileWriter = new WriterImpl();
    private static final String filePath = "src/test/resources/writerTest.csv";
    private static final List<String> expected = new ArrayList<>(List.of("some,information"));

    @After
    public void afterEachTest() {
        expected.clear();
    }

    @Test
    public void writerTest_Ok() {
        List<String> actual;
        fileWriter.writeToFile(expected, filePath);
        try {
            actual = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read from file", e);
        }
        assertEquals(expected, actual);
    }

    @Test (expected = AssertionError.class)
    public void writerTest_notOk() {
        List<String> actual = new ArrayList<>();
        fileWriter.writeToFile(expected, filePath);
        actual.add("SomeIncorrectData");
        assertEquals(expected, actual);
    }

    @Test
    public void invalidPathToFile_Ok() {
        fileWriter.writeToFile(expected, "wrongPath");
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullData_notOk() {
        fileWriter.writeToFile(null, filePath);
    }
}
