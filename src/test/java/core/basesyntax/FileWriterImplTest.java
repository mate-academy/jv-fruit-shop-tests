package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String validPath =
            "src/test/java/core/basesyntax/fileWriterImplTestResources/validPath.txt";
    private static final String invalidPath =
            "D/D/D/invalidPath";
    private static final String validContent = "valid content";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeToFile_validCase_Ok() {
        FileReader fileReader = new FileReaderImpl();
        fileWriter.writeToFile(validPath, validContent);
        Assertions.assertEquals(List.of("valid content"), fileReader.readFromFile(validPath));
    }

    @Test
    void writeToFile_nullPath_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                fileWriter.writeToFile(null, validContent));
    }

    @Test
    void writeToFile_nullContent_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                fileWriter.writeToFile(validPath, null));
    }

    @Test
    void writeToFile_invalidPath_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                fileWriter.writeToFile(invalidPath, validContent));
    }
}
