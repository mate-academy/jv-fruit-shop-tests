package core.basesyntax.service;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadDataFileTest {

    private static String TEST_FILE = "src/test/resources/TestFile.csv";
    private static String TEST_FILE1 = "src/test/resources/TestFile1.csv";
    private static String TEST_FILE2 = "src/test/resources/TestFile2.csv";
    private static String TEST_FILE3 = "src/test/resources/TestFile3.csv";

    private static ReadDataFile readTestFile;
    private static ReadDataFile readTestFile1;
    private static ReadDataFile readTestFile2;
    private static ReadDataFile readTestFile3;

    @BeforeEach
    void setUp() {
        readTestFile = new ReadDataFile(TEST_FILE);
        readTestFile1 = new ReadDataFile(TEST_FILE1);
        readTestFile2 = new ReadDataFile(TEST_FILE2);
        readTestFile3 = new ReadDataFile(TEST_FILE3);
    }

    @Test
    void getInputDataFromFile_ShouldReturnCorrectFruitQuantities_WhenFileIsCorrect_ok() {
        Map<String, Integer> expectedFruitQuantities = Map.of(
                "apple", 13,
                "banana", 3
        );

        Map<String, Integer> actualFruitQuantities = readTestFile.getInputDataFromFile();

        Assertions.assertEquals(expectedFruitQuantities, actualFruitQuantities,
                "Fruit quantities do not match the expected values");
    }

    @Test
    void getInputDataFromFile_ShouldReturnCorrectFruitQuantities_WhenFileIsCorrect_noOk() {
        Map<String, Integer> expectedFruitQuantities = Map.of(
                "apple", 3,
                "banana", 13
        );

        Map<String, Integer> actualFruitQuantities = readTestFile.getInputDataFromFile();

        Assertions.assertNotEquals(expectedFruitQuantities, actualFruitQuantities,
                "Values should not be same");
    }

    @Test
    void getInputDataFromFile_ShouldReturnCorrectFruitQuantities_WhenFileIsCorrect_But_No_Heeder() {
        Map<String, Integer> expectedFruitQuantities = Map.of(
                "apple", 13,
                "banana", 3
        );

        Map<String, Integer> actualFruitQuantities = readTestFile1.getInputDataFromFile();

        equals(expectedFruitQuantities);
    }

    @Test
    void getInputDataFromFile_ShouldReturnCorrectFruitQuantities_Extra_fruit_OK() {
        Map<String, Integer> expectedFruitQuantities = Map.of(
                "apple", 13,
                "banana", 3,
                "cherry", 20
        );

        Map<String, Integer> actualFruitQuantities = readTestFile2.getInputDataFromFile();

        equals(expectedFruitQuantities);
    }

    @Test
    void getInputDataFromFile_ShouldReturnCorrectFruitQuantities_Extra_fruit_TooMuch_Soled_NoOK() {
        Map<String, Integer> expectedFruitQuantities = Map.of(
                "apple", 13,
                "banana", 3,
                "cherry", -1
        );

        Assertions.assertThrows(RuntimeException.class, () -> {
            readTestFile3.getInputDataFromFile();
        });
    }
}
