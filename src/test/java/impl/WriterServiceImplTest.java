package impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WriterServiceImplTest {
    private static final String HEAD = "fruit,quantity" + System.lineSeparator();
    private static final String COMMA = "," + System.lineSeparator();
    private static final String FRUIT_QUANTITY = "100" + System.lineSeparator();
    private static final String PATH = "src/test/java/TestsFiles/TestForWriteService.csv";
    private WriterServiceImpl writeService;

    @BeforeAll
    void setUp() {
        writeService = new WriterServiceImpl();
    }

    @Test
    void writeServiceTest_Ok() {
        String expectedResult = HEAD + "banana"
                + COMMA
                + FRUIT_QUANTITY
                + System.lineSeparator()
                + "apple"
                + COMMA
                + FRUIT_QUANTITY;
        writeService.writeDataToFile(expectedResult, PATH);
        StringBuilder testDataFromFile = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                testDataFromFile.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file by path: " + PATH, e);
        }
        String actualResult = testDataFromFile.toString();
        assertEquals(expectedResult, actualResult,
                "Your service to write data to file work incorrectly");
    }
}
