package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String RESOURCES_PATH_STRING = "src/test/resources/";
    private static final Path INPUT_PATH = Path.of(RESOURCES_PATH_STRING + "input.csv");
    private static final Path UNEXISTING_PATH = Path.of(RESOURCES_PATH_STRING + "output.csv");
    private static FileWriterService writer;
    private static String testData;

    @BeforeClass
    public static void beforeAll() {
        testData = "line1Header" + System.lineSeparator()
                + "line2Content" + System.lineSeparator()
                + "line3Content";
    }

    @Before
    public void beforeEach() {
        writer = new FileWriterServiceImpl(INPUT_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_withNullPath_notOk() {
        writer = new FileWriterServiceImpl(null);
        writer.writeToFile(testData);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_withNullData_notOk() {
        writer.writeToFile(null);
    }

    @Test
    public void writeToFile_toExistingFile_ok() {
        writer.writeToFile(testData);
        List<String> lines;
        try {
            lines = Files.readAllLines(INPUT_PATH);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to read: %s", INPUT_PATH), e);
        }
        String actual = String.join(System.lineSeparator(), lines);
        assertEquals(testData, actual);
    }

    @Test
    public void writeToFile_toUnExistingFile_ok() {
        writer = new FileWriterServiceImpl(UNEXISTING_PATH);
        writer.writeToFile(testData);
        assertTrue(Files.exists(UNEXISTING_PATH));
        try {
            Files.delete(UNEXISTING_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Can't delete testFile!", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_toUnExistingOuterFile_notOk() {
        writer = new FileWriterServiceImpl(Path.of("DISC:/z/x/c.csv"));
        writer.writeToFile(testData);
    }
}
