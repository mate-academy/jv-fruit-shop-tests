package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NotCsvFileException;
import core.basesyntax.service.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final static String INPUT_PATH = "src/main/resources/test.csv";
    private final FileReader fileReader = new FileReaderImpl();

    @BeforeAll
    static void beforeAll() {
        File file = new File(INPUT_PATH);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedWriter.write("");
            bufferedWriter.append("type,fruit,quantity\n");
            bufferedWriter.append("b,banana,20\n");
            bufferedWriter.append("b,apple,100\n");
            bufferedWriter.append("s,banana,100\n");
            bufferedWriter.append("p,banana,13\n");
            bufferedWriter.append("r,apple,10\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void readFromExistFileTest_Ok() {
        Assertions.assertDoesNotThrow(() -> {
            fileReader.read(INPUT_PATH);
        });
    }

    @Test
    void readFromNonExistFileTest_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.read("nonExist.csv");
        });
    }

    @Test
    void readNotCsvFileTest_NotOk() {
        Assertions.assertThrows(NotCsvFileException.class, () -> {
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
        Assertions.assertEquals(expectedResult, result);
    }
}
