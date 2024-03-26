package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.IllegalInputDataException;
import core.basesyntax.exception.WriteToFileException;
import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String VALID_FILE_PATH = "src/test/resources/valid_output.csv";
    private static final String INVALID_FILE_PATH = "src/invalidFolder/resources/valid_output.csv";
    private static final String DATA_TO_WRITE = "test data to write";
    private static final String VALID_DATA = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";
    private final FileWriter csvFileWriter = new CsvFileWriter();

    @BeforeEach
    void setUp() {
        Path filePath = Path.of(VALID_FILE_PATH);

        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
            } catch (IOException ex) {
                throw new RuntimeException("Can`t delete the file", ex);
            }
        }
    }

    @Test
    void writeData_inputFilePathIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> csvFileWriter.writeData(null, VALID_DATA));

        assertEquals("Report path is null or empty", expected.getMessage());
    }

    @Test
    void writeData_inputFilePathIsEmpty_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> csvFileWriter.writeData("", VALID_DATA));

        assertEquals("Report path is null or empty", expected.getMessage());
    }

    @Test
    void writeData_inputDataIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> csvFileWriter.writeData(VALID_FILE_PATH, null));

        assertEquals("Data is null or empty", expected.getMessage());
    }

    @Test
    void writeData_inputDataIsEmpty_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> csvFileWriter.writeData("src/test/resources/valid_output.csv", ""));

        assertEquals("Data is null or empty", expected.getMessage());
    }

    @Test
    void writeData_invalidFilePath_notOk() {
        WriteToFileException expected = assertThrows(WriteToFileException.class,
                () -> csvFileWriter.writeData(INVALID_FILE_PATH, DATA_TO_WRITE));

        assertEquals(String.format("Can`t write data to the file %s", INVALID_FILE_PATH),
                expected.getMessage());
    }

    @Test
    void writeData_writeDataToTheFile_ok() throws IOException {
        csvFileWriter.writeData(VALID_FILE_PATH, VALID_DATA);

        String actual = readData(VALID_FILE_PATH);

        assertEquals(VALID_DATA, actual);
    }

    private static String readData(String filePath) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(filePath));
        return strings.stream()
                .collect(Collectors.joining(System.lineSeparator()));

    }
}
