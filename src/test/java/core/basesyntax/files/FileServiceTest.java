package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileServiceTest {
    private static final String expectedReport =
            "fruit,quantity" + System.lineSeparator()
            + "banana,300" + System.lineSeparator()
            + "apple,90";
    private static final String CORRECT_INPUT_DATA_FILEPATH = "src/test/resources/test.csv";
    private static final String INCORRECT_INPUT_DATA_FILEPATH = "src/test/resources/incorrect.csv";
    private static final String CORRECT_OUTPUT_DATA_FILEPATH = "src/test/resources/dateReport.csv";
    private static final String INCORRECT_OUTPUT_DATA_FILEPATH = "src/test/wrongWay/dateReport.csv";
    private static FileReaderService fileReaderService;
    private static List<String> expectedInputList;
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
        expectedInputList = new ArrayList<>();
        fileWriterService = new FileWriterServiceImpl();
        expectedInputList.add("type,fruit,quantity");
        expectedInputList.add("b,apple,60");
        expectedInputList.add("b,banana,50");
        expectedInputList.add("s,banana,100");
        expectedInputList.add("p,banana,50");
        expectedInputList.add("r,apple,30");
        expectedInputList.add("s,banana,200");
    }

    @Test
    void readFromFile_correctFilePathReading_Ok() {
        List<String> actual = fileReaderService.readFromFile(CORRECT_INPUT_DATA_FILEPATH);
        assertEquals(expectedInputList, actual);
    }

    @Test
    void readFromFile_NotExistingFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(INCORRECT_INPUT_DATA_FILEPATH));
    }

    @Test
    void writeToFile_NotCorrectFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.write(expectedReport, INCORRECT_OUTPUT_DATA_FILEPATH));
    }

    @Test
    void writeToFile_correctDataWriting_Ok() throws IOException {
        StringBuilder builder = new StringBuilder();
        boolean isFirstLine = true;
        String line;
        fileWriterService.write(expectedReport, CORRECT_OUTPUT_DATA_FILEPATH);
        try (BufferedReader reader = new BufferedReader(
                new FileReader(CORRECT_OUTPUT_DATA_FILEPATH))) {
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    builder.append(System.lineSeparator());
                }
                builder.append(line);
            }
        }
        assertEquals(expectedReport, builder.toString());
    }
}
