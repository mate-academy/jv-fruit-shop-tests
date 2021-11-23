package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReadService;
import core.basesyntax.service.impl.FileReadServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFileTest {
    private static FileReadService fileReadService;
    private static final String VALID_PATH_FILE = "src/test/resources/date.csv";

    @BeforeClass
    public static void setUp() {
        fileReadService = new FileReadServiceImpl();
    }

    @Test(expected = NullPointerException.class)
    public void redFromFile_nullFilePath_notOk() {
        fileReadService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void redFromFile_invalidPath_notOk() {
        fileReadService.readFromFile(Path.of("not_valid_path"));
    }

    @Test
    public void redFromFile_validFilePath_ok() throws IOException {
        Path path = Path.of(VALID_PATH_FILE);
        String actual = fileReadService.readFromFile(path);
        String expected = Files.readString(path);
        assertEquals("File not read", expected, actual);
    }
}
