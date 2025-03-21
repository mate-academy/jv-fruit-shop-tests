package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CustomFileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {

    private static CustomFileReader fileReader;
    private static final Path startFilePath = Paths.get("src/test/resources", "reportToRead.csv");

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void emptyFile_ShouldThrowException() {
        assertThrows(RuntimeException.class, () -> fileReader.read(""));
    }

    @Test
    public void read_DefunctFilePath_NotOK() {
        final String filePathing = "reportToReadeer.csv";
        assertThrows(RuntimeException.class, () -> fileReader.read(filePathing));
    }

    @Test
    public void return_ListLengthEquals_Ok() {
        int expectedLength = 8;
        List<String> actualList = fileReader.read(String.valueOf(startFilePath));
        assertEquals(expectedLength, actualList.size());
    }
}
