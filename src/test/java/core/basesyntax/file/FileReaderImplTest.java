package core.basesyntax.file;

import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/testReport";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final String NON_EXIST_FILE_PATH = "src/test/resources/nonExistFile.csv";
    private FileReader fileReader;

    @BeforeEach
    public void setup() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_ValidFile_ok() {
        List<String> lines = fileReader.read(VALID_FILE_PATH);
        Assert.assertFalse(lines.isEmpty());
    }

    @Test
    public void read_NonExistFilePath_notOK() {
        RuntimeException exception =
                Assert.assertThrows(RuntimeException.class, () -> {
                    fileReader.read(NON_EXIST_FILE_PATH);
                });
        Assert.assertTrue(exception.getMessage().contains("Error reading file at path: "));
    }

    @Test
    public void read_EmptyFile_ok() {
        List<String> lines = fileReader.read(EMPTY_FILE_PATH);
        Assert.assertTrue(lines.isEmpty());
    }
}
