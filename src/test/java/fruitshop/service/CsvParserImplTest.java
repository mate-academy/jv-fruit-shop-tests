package fruitshop.service;

import fruitshop.model.FruitTransaction;
import fruitshop.model.Operation;
import fruitshop.service.impl.CsvParser;
import fruitshop.service.impl.ReaderServiceImpl;
import fruitshop.service.impl.WriterServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CsvParserImplTest {
    private static final String TEST_FILE_NAME = "testData.csv";
    private Parser csvParser;
    private ReaderService reader;
    private WriterService writer;

    @BeforeAll
    void setUp() {
        csvParser = new CsvParser();
        reader = new ReaderServiceImpl();
        writer = new WriterServiceImpl();
        writeTestFile();
    }

    void writeTestFile() {
        String text =
                """
            type,fruit,quantity
            b,banana,20
            b,apple,100
            s,banana,150
            p,banana,13
            r,apple,10
                """;
        writer.writeToFile(text, TEST_FILE_NAME);
    }

    @Test
    void csvParser_Ok() {
        List<String> readResult = reader.readFromFile(TEST_FILE_NAME);
        List<FruitTransaction> expected = new ArrayList<>();

        expected.add(FruitTransaction.of(Operation.BALANCE, "banana", 20));
        expected.add(FruitTransaction.of(Operation.BALANCE, "apple", 100));
        expected.add(FruitTransaction.of(Operation.SUPPLY, "banana", 150));
        expected.add(FruitTransaction.of(Operation.PURCHASE, "banana", 13));
        expected.add(FruitTransaction.of(Operation.RETURN, "apple", 10));

        List<FruitTransaction> result = csvParser.parse(readResult);
        Assertions.assertEquals(result, expected,
                "Expected result is different. Incorrect parsing from CSV file.");
    }
}
