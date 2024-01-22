package core.basesyntax.service.convertator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.file.FileReader;
import core.basesyntax.service.file.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DataConvertorImplTest {
    private static DataConvertor dataConvertor;
    private static List<FruitTransaction> correctObjects;
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
        dataConvertor = new DataConvertorImpl();
        correctObjects = getCorrectObjects();
    }

    @Test
    void convertData_isOk() {
        List<FruitTransaction> convertedData =
                dataConvertor.convertData(getContentForConvertation());
        assertTrue(convertedData.size() == correctObjects.size()
                && convertedData.equals(correctObjects), getContentForConvertation());
    }

    @ParameterizedTest
    @ValueSource(strings = """
            type,fruit,quantity\r
            q,banana,20"""
    )
    void convertDataWithIncorrectOperation_expectedException(String content) {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            dataConvertor.convertData(content);
        });
        String expectedMassage = "Invalid operation code: " + "q";
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }

    private static List<FruitTransaction> getCorrectObjects() {
        return dataConvertor.convertData(
                fileReader.readFromFile("src/test/resources/testInput.csv"));
    }

    private String getContentForConvertation() {
        return fileReader.readFromFile("src/test/resources/testInput.csv");
    }
}
