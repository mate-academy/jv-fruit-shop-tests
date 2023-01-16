package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.service.FileReadService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReadServiceImplTest {
    private static final String VALID_PATH = "src/main/resources/input.csv";
    private static FileReadService fileReadService;

    @BeforeClass
    public static void setUp() {
        fileReadService = new FileReadServiceImpl();
    }

    @Test
    public void readFromFile_validPath_ok() throws IOException {
        List<String> expected = Files.readAllLines(Path.of(VALID_PATH));
        List<String> actual = fileReadService.readFromFile(VALID_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullPath_notOk() {
        fileReadService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nonExistentPath_notOk() {
        fileReadService.readFromFile("non_existent_path");
    }

    @Test
    public void readFromFile_noDataRead_notOk() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReadService.readFromFile(VALID_PATH);
        assertNotEquals(expected, actual);
    }
}
