package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.NotCsvFileException;
import core.basesyntax.service.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String INPUT_PATH = "src/main/resources/test.csv";
    private final FileReader fileReader = new FileReaderImpl();

    @BeforeAll
    static void beforeAll() {
        File file = new File(INPUT_PATH);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedWriter.write("");
            bufferedWriter.append("type,fruit,quantity").append(System.lineSeparator());
            bufferedWriter.append("b,banana,20").append(System.lineSeparator());
            bufferedWriter.append("b,apple,100").append(System.lineSeparator());
            bufferedWriter.append("s,banana,100").append(System.lineSeparator());
            bufferedWriter.append("p,banana,13").append(System.lineSeparator());
            bufferedWriter.append("r,apple,10").append(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void readFromExistFileTest_Ok() {
        assertDoesNotThrow(() -> {
            fileReader.read(INPUT_PATH);
        });
    }

    @Test
    void readFromNonExistFileTest_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read("nonExist.csv");
        });
    }

    @Test
    void readNotCsvFileTest_NotOk() {
        assertThrows(NotCsvFileException.class, () -> {
            fileReader.read("notCsvFile.txt");
        });
    }

    @Test
    void readDataFromCsvTest_Ok() {
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("type,fruit,quantity");
        expectedResult.add("b,banana,20");
        expectedResult.add("b,apple,100");
        expectedResult.add("s,banana,100");
        expectedResult.add("p,banana,13");
        expectedResult.add("r,apple,10");
        List<String> result = fileReader.read(INPUT_PATH);
        assertEquals(expectedResult, result);
    }
}
