package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.WriteDataServiceImpl;
import core.basesyntax.service.strategy.WriteDataService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteDataServiceImplTest {
    private static final String CORRECT_FILE_PATH_TO_WRITE =
            "src/test/java/resources/outputDataTest.csv";
    private static final String EMPTY_TEXT_TO_WRITE = "";
    private WriteDataService writeDataService;
    private String testData;

    @BeforeEach
    void setUp() {
        writeDataService = new WriteDataServiceImpl();
        testData = "fruit,quantity" + System.lineSeparator()
                + "banana,20," + System.lineSeparator()
                + "apple,100";
    }

    @Test
    void writeData_writesDataToFile_ok() throws IOException {
        writeDataService.writeData(testData, CORRECT_FILE_PATH_TO_WRITE);
        File file = new File(CORRECT_FILE_PATH_TO_WRITE);
        assertTrue(file.exists());
        String content = readContentFromFile(file);
        assertEquals(testData, content);
    }

    @Test
    void writeData_writesEmptyDataToFile_ok() throws IOException {
        writeDataService.writeData(EMPTY_TEXT_TO_WRITE, CORRECT_FILE_PATH_TO_WRITE);
        File file = new File(CORRECT_FILE_PATH_TO_WRITE);
        assertTrue(file.exists());
        String content = readContentFromFile(file);
        assertEquals(EMPTY_TEXT_TO_WRITE, content);
    }

    private String readContentFromFile(File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String content = reader.readLine();
            while (content != null) {
                stringBuilder.append(content)
                        .append(System.lineSeparator());

                content = reader.readLine();
            }
        }
        return stringBuilder.toString().trim();
    }
}
