package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceTest {
    private static final String VALID_INPUT_PATH = "src/main/resources/InputFile";
    private static FileWriterService fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterServiceImpl();
    }

    @Test
    void writeToFile_validDataInput_Ok() {
        String dataInput = "banana,23";
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,23";
        fileWriter.writeToFile(VALID_INPUT_PATH, dataInput);
        String actual = readFromFile(VALID_INPUT_PATH);
        assertEquals(expected, actual);
    }

    private String readFromFile(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String data = bufferedReader.readLine();
            return data + System.lineSeparator() + bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can`t read from: " + path, e);
        }
    }
}
