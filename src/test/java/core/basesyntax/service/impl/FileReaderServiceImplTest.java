package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReader;
    private static final String INPUT_PATH = "src/test/resources/input.csv";
    private static final String EMPTY_INPUT_PATH = "src/test/resources/emptyInput.csv";
    private static final String DATA = "type,fruit,quantity"
            + System.lineSeparator() + "b,banana,100";

    @BeforeClass
    public static void setUpFirst() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    public void read_correctData_Ok() {
        writeToFile(INPUT_PATH, DATA);
        assertEquals(List.of("type,fruit,quantity", "b,banana,100"),
                fileReader.readFromFile(INPUT_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void read_wrongFileName_NotOk() {
        fileReader.readFromFile("invalid path");
    }

    @Test(expected = RuntimeException.class)
    public void read_nullFileName_NotOk() {
        fileReader.readFromFile(null);
    }

    @Test
    public void read_emptyFile_Ok() {
        writeToFile(EMPTY_INPUT_PATH, "");
        List<String> actual = fileReader.readFromFile(EMPTY_INPUT_PATH);
        assertEquals(List.of(), actual);
    }

    private void writeToFile(String filePath, String data) {
        try {
            Files.write(Path.of(filePath), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can`t create file for read");
        }
    }
}
