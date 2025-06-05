package core.basesyntax.file.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FileProcessingException;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.file.FileReader;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final String correctFile = "src/test/resources/data.csv";
    private final String unreadableFile = "src/test/resources/unreadableFile.csv";
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_ValidFile_Ok() {
        String expectedContent = "[type,fruit,quantity, b,banana,20, b,apple,100,"
                + " s,banana,100, p,banana,13, r,apple,10, "
                + "p,apple,20, p,banana,5, s,banana,50]";
        String actualContent = fileReader.read(correctFile).toString();
        Assertions.assertEquals(expectedContent, actualContent);
    }

    @Test
    void read_FileNotFound_notOk() {
        String nonExistentPath = "not-existing-file.csv";

        FileProcessingException exception = assertThrows(
                FileProcessingException.class,
                () -> fileReader.read(nonExistentPath)
        );
        Assertions.assertTrue(exception.getMessage().startsWith("File not found:"));
    }

    @Test
    void read_UnreadableFile_notOk() {
        File file = new File(unreadableFile);
        file.setReadable(false);

        InvalidDataException exception = assertThrows(
                InvalidDataException.class,
                () -> fileReader.read(unreadableFile)
        );
        Assertions.assertTrue(exception.getMessage().startsWith("Error while reading file:"));
    }
}
