package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String PATH_TO_RESOURCES = "src/main/resources/";
    private static final String PATH_TO_DEFAULT_FILE =
            PATH_TO_RESOURCES + "sampleInput.txt";
    private static final String LOREM = "Lorem ipsum"
            + "dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor"
            + "incididunt ut labore et dolore magna aliqua.";

    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @AfterClass
    public static void after() {
        try {
            Files.deleteIfExists(Path.of(PATH_TO_DEFAULT_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void write_defaultCase_ok() {
        writerService.write(LOREM, PATH_TO_DEFAULT_FILE);
        try {
            assertEquals(Files.readAllLines(Path.of(PATH_TO_DEFAULT_FILE)).get(0), LOREM);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void write_emptyInput_ok() {
        writerService.write("", PATH_TO_DEFAULT_FILE);
        try {
            assertTrue(Files.readAllLines(Path.of(PATH_TO_DEFAULT_FILE)).isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_argumentsNull_notOk() {
        writerService.write(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void write_firstArgumentNull_notOk() {
        writerService.write(null, PATH_TO_DEFAULT_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void write_secondArgumentNull_notOk() {
        writerService.write(LOREM, null);
    }
}
