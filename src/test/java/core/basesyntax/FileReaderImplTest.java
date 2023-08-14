package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String validPath =
            "src/test/resources/readerValidPath.txt";
    private static final String invalidPath =
            "D/invalidPath";
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFromFile_validCase_Ok() {
        List<String> actual = fileReader.readFromFile(validPath);
        Assertions.assertEquals(List.of("some text here",
                "and also here",
                "some numbers 900"), actual);
    }

    @Test
    void readFromFile_nullPath_notOk() {
        Assert.assertThrows(RuntimeException.class, () -> fileReader.readFromFile(null));
    }

    @Test
    void readFromFile_invalidPath_notOk() {
        Assert.assertThrows(RuntimeException.class, () -> fileReader.readFromFile(invalidPath));
    }
}
