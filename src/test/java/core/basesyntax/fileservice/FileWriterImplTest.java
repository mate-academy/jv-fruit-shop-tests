package core.basesyntax.fileservice;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String OUTPUT_FILE = "src/test/resources/outputFile.csv";
    private static final String WRONG_PATH_OUTPUT_FILE = "src/test/r/outputFile.csv";
    private static final String REPORT_OK = "fruit, amount" + System.lineSeparator() + "apple,120";
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_EmptyReport_Ok() throws IOException {
        fileWriter.writeFile("", OUTPUT_FILE);
        compareFiles(OUTPUT_FILE);
    }

    @Test
    void write_ValidReport_Ok() {
        fileWriter.writeFile(REPORT_OK, OUTPUT_FILE);
        try {
            compareFiles(OUTPUT_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Writing date to this file is impossible");
        }
    }

    @Test
    void writing_writeFileToWrongFolder_NotOk() {
        assertThrows(RuntimeException.class, () ->
                        fileWriter.writeFile(REPORT_OK,WRONG_PATH_OUTPUT_FILE),
                "This path is wrong"
        );
    }

    @Test
    void writing_nameFileIsNull_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.writeFile(EMPTY_FILE,null),
                "Name of your file can`t be null");
    }

    private void compareFiles(String expectedFile) throws IOException {
        List<String> expectedFileContent = Files.readAllLines(Path.of(expectedFile));
        List<String> actualFileContent = Files.readAllLines(Path.of(OUTPUT_FILE));
        assertLinesMatch(expectedFileContent, actualFileContent);
    }
}
