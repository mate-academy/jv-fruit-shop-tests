package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.CsvFileReaderImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private final FileReader fileReader = new CsvFileReaderImpl();
    private final String testFolderPath = "src/test/resources/";

    @Test
    void readFromFile_validFile_ok() {
        String fileName = testFolderPath + "test2.csv";
        List<String> content = fileReader.readFromFile(fileName);
        List<String> expectedContent = Arrays.asList("Line 1", "Line 2", "Line 3");
        Assertions.assertEquals(expectedContent, content);
    }

    @Test
    void readFromFile_nonExistentFile_notOk() {
        String nonExistentFileName = "non_existent_file.csv";
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReader.readFromFile(nonExistentFileName));
    }
}
