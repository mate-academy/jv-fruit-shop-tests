package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String PATH_TO_RESOURCES = "src/main/resources/";
    private static final String PATH_TO_DEFAULT_FILE =
            PATH_TO_RESOURCES + "sampleInput.txt";

    private static final String LOREM = "Lorem ipsum"
            + "dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor"
            + "incididunt ut labore et dolore magna aliqua.";

    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
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
    public void readFrom_defaultCase_ok() {
        try {
            Files.write(Path.of(PATH_TO_DEFAULT_FILE), LOREM.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(readerService.readFrom(PATH_TO_DEFAULT_FILE), LOREM);
    }

    @Test
    public void readFrom_emptyFile_ok() {
        try {
            Files.write(Path.of(PATH_TO_DEFAULT_FILE), "".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(readerService.readFrom(PATH_TO_DEFAULT_FILE), "");
    }

    @Test(expected = RuntimeException.class)
    public void readFrom_fileNotFound_notOk() {
        readerService.readFrom("abcdabcd");
    }

    @Test(expected = RuntimeException.class)
    public void readFrom_argumentNull_notOk() {
        readerService.readFrom(null);
    }
}
