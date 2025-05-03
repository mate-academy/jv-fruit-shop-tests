package core.basesyntax.service.parser;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.reader.InputDataReader;
import core.basesyntax.service.reader.InputDataReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationParserImplTest {
    private static final String READER_TEST_FILE_PATH = "src/test/resources/reader_test.csv";
    private static List<TransactionDto> expected;
    private static OperationParser operationParser;
    private static InputDataReader inputDataReader;

    @BeforeClass
    public static void beforeClass() {
        operationParser = new OperationParserImpl();
        inputDataReader = new InputDataReaderImpl();
        expected = new ArrayList<>();
        expected.add(new TransactionDto(OperationType.BALANCE, new Fruit("banana"), 20));
        expected.add(new TransactionDto(OperationType.BALANCE, new Fruit("apple"), 100));
        expected.add(new TransactionDto(OperationType.SUPPLY, new Fruit("banana"), 100));
    }

    @Test
    public void parseOperations_ok() {
        List<TransactionDto> actual = operationParser.parseOperations(
                inputDataReader.getDataFromFile(READER_TEST_FILE_PATH));
        assertEquals(expected, actual);
    }
}
