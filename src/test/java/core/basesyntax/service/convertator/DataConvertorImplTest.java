package core.basesyntax.service.convertator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.file.FileReader;
import core.basesyntax.service.file.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
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

    @ParameterizedTest
    @ValueSource(strings = {"type,fruit,quantity\r\n"
            + "b,banana,20\r\n"
            + "b,apple,100\r\n"
            + "s,banana,100\r\n"
            + "p,banana,13\r\n"
            + "r,apple,10\r\n"
            + "p,apple,20\r\n"
            + "p,banana,5\r\n"
            + "s,banana,50\r\n"
    })
    void convertData_isOk(String content) {
        List<FruitTransaction> convertedData =
                dataConvertor.convertData(content);
        assertTrue(convertedData.size() == correctObjects.size()
                && convertedData.equals(correctObjects));
    }

    @ParameterizedTest
    @ValueSource(strings = {"type,fruit,quantity\r\n"
            + "q,banana,20\r\n"
    })
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
}
