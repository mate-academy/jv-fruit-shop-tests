package core.basesyntax.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final FileWriter FILE_WRITER = new FileWriterImpl();
    private static final String EXPECTED_FILE_PATH = "src/test/resources/writertest/expected.csv";
    private static final String ACTUAL_FILE_PATH = "src/test/resources/writertest/actual.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/unexistedfolder/actual.csv";
    private static final String VALID_EXCEPTION_MESSAGE = "Can't write data to file "
            + INVALID_FILE_PATH;
    private static final List<String> INPUT_LIST = List.of("banana,20", System.lineSeparator(),
            "apple,100");

    @Test
    void writeToFile_validCase_isOk() {
        FILE_WRITER.writeToFile(INPUT_LIST, ACTUAL_FILE_PATH);
        assertEquals(readFile(EXPECTED_FILE_PATH), readFile(ACTUAL_FILE_PATH));
        File toFile = new File(ACTUAL_FILE_PATH);
        toFile.delete();
    }

    @Test
    void writeToFile_invalidCase_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            FILE_WRITER.writeToFile(INPUT_LIST, INVALID_FILE_PATH);
        });
        assertEquals(VALID_EXCEPTION_MESSAGE, exception.getMessage());
    }

    private String readFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(
                new java.io.FileReader(filePath))) {
            String fromText = bufferedReader.readLine();
            while (fromText != null) {
                stringBuilder.append(fromText).append(System.lineSeparator());
                fromText = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + filePath, e);
        }
        return stringBuilder.toString();
    }
}
