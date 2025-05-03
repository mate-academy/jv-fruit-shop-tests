package core.basesyntax.services.impl;

import core.basesyntax.services.FileDataReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileDataReaderImplTest {
    private static final Path INPUT_PATH = Path.of("src/test/resources/input1.csv");
    private static final String INVALID_PATH = "src/test/resources/nonexistentFile.csv";

    private FileDataReader fileDataReader;

    @BeforeEach
    void setUp() {
        fileDataReader = new FileDataReaderImpl();
    }

    @Test
    void returnList_validList_ok() {
        List<String> list = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(String.valueOf(INPUT_PATH)))) {
            String string;
            bf.readLine();
            while ((string = bf.readLine()) != null) {
                list.add(string);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> list1 = fileDataReader.readData(INPUT_PATH);
        Assertions.assertEquals(list, list1);
    }

    @Test
    void check_inputPathIsValid_ok() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileDataReader.readData(Path.of(INVALID_PATH));
        });
    }
}
