package impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParseDataServiceImplTest {
    private static final String PATH = "src/test/java/resources/TestInputData.csv";
    private static final FruitTransaction[] TestsTransaction = new FruitTransaction[] {
            new FruitTransaction("b", "banana", 100),
            new FruitTransaction("b", "apple", 100),
            new FruitTransaction("s", "apple", 10),
            new FruitTransaction("s", "banana", 100),
            new FruitTransaction("p", "apple", 10),
            new FruitTransaction("p", "apple", 20),
            new FruitTransaction("r", "banana", 10),
            new FruitTransaction("r", "apple", 10)
    };
    private final ParseDataServiceImpl parse = new ParseDataServiceImpl();
    private final FileReaderImpl reader = new FileReaderImpl();

    @Test
    public void parseDataService_validData_ok() {
        List<FruitTransaction> actualData = parse.parseData(reader.readFile(PATH));
        List<FruitTransaction> expectedData = List.of(TestsTransaction);
        assertEquals(expectedData, actualData,
                "Your ParseService class works incorrectly");

    }

    @Test
    public void parseDataService_invalidQuantity_notOk() {
        List<String> invalidQuantity = List.of("b,banana,-10");
        assertThrows(RuntimeException.class, () -> parse.parseData(invalidQuantity));
    }

    @Test
    public void parseDataService_invalidLength_notOk() {
        List<String> invalidLength = List.of("b,apple,10,else");
        assertThrows(RuntimeException.class, () -> parse.parseData(invalidLength));
    }
}
