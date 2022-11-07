package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        testData = "line1Header\n"
                + "line2Content\n"
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
}
