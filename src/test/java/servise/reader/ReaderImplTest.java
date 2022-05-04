package servise.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private static final String PATH_INPUT_FILE = "src/test/resources/inputData.csv";
    private final Reader reader;

    private ReaderImplTest() {
        this.reader = new ReaderImpl();
    }

    @Test
    void readFromFile_ok() {
        List<String> expected = readFromFile(PATH_INPUT_FILE);
        List<String> actual = reader.readFromFile(PATH_INPUT_FILE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readFromFile_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> reader.readFromFile(""));
    }

    private List<String> readFromFile(String path) {
        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read all line" + path, e);
        }
        return list;
    }
}
