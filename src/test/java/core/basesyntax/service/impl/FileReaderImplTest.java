package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_INPUT_FILE_PATH = "src/test/resources/input.csv";
    private static final String INVALID_INPUT_FILE_PATH = "src/test/resources/invalidinput.csv";
    private static final FileReader fileReader = new FileReaderImpl();

    @BeforeEach
    void tearsUp() {
        StringBuilder builder = new StringBuilder();
        builder.append("b,banana,20");
        builder.append(System.lineSeparator());
        builder.append("b,apple,120");
        builder.append(System.lineSeparator());
        builder.append("s,banana,100");
        builder.append(System.lineSeparator());
        File file = new File(VALID_INPUT_FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {
            writer.write(builder.toString());
        } catch (IOException e) {
            throw new RuntimeException("Can't write to the file:" + VALID_INPUT_FILE_PATH, e);
        }
    }

    @Test
    void readFromFile_fileNotExists_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readFromFile(INVALID_INPUT_FILE_PATH));
    }

    @Test
    void readFromFile_readData_isOk() {
        String expectedData = "b,banana,20;b,apple,120;s,banana,100;";
        assertEquals(expectedData,fileReader.readFromFile(VALID_INPUT_FILE_PATH));
    }
}
