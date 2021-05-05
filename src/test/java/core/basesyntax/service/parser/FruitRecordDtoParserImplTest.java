package core.basesyntax.service.parser;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.fileservice.FileReader;
import core.basesyntax.service.fileservice.FileReaderForCsvImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitRecordDtoParserImplTest {
    private static final String validCsvFile = "src/test/resources/valid_instructions.csv";
    private static final String firstInvalidCsvFile = "src/test/resources/invalid_instructions.csv";
    private static final String secondInvalidCsvFile =
            "src/test/resources/invalid_instructions_2.csv";
    private static final String thirdInvalidCsvFile =
            "src/test/resources/invalid_instructions_3.csv";
    private static final String fourthInvalidCsvFile =
            "src/test/resources/invalid_instructions_4.csv";
    private static final String emptyCsvFile = "src/test/resources/empty.csv";
    private static final FruitRecordDtoParser parser = new FruitRecordDtoParserImpl();
    private static final FileReader fileReader = new FileReaderForCsvImpl();
    private static List<FruitRecordDto> expected;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        expected = List.of(
                new FruitRecordDto(Operation.getOperationByLetter("b"), new Fruit("banana"), 300),
                new FruitRecordDto(Operation.getOperationByLetter("b"), new Fruit("apple"), 400),
                new FruitRecordDto(Operation.getOperationByLetter("s"), new Fruit("banana"), 100),
                new FruitRecordDto(Operation.getOperationByLetter("p"), new Fruit("banana"), 13),
                new FruitRecordDto(Operation.getOperationByLetter("r"), new Fruit("apple"), 10),
                new FruitRecordDto(Operation.getOperationByLetter("p"), new Fruit("apple"), 20),
                new FruitRecordDto(Operation.getOperationByLetter("p"), new Fruit("banana"), 99),
                new FruitRecordDto(Operation.getOperationByLetter("p"), new Fruit("banana"), 1),
                new FruitRecordDto(Operation.getOperationByLetter("s"), new Fruit("banana"), 150)
        );
    }

    @Test
    public void check_parserWithValidData_OK() {
        List<FruitRecordDto> actual = parser.parse(fileReader.readAllLinesFromFile(validCsvFile));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_parserWithValidDataFirst_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(firstInvalidCsvFile));
    }

    @Test(expected = RuntimeException.class)
    public void check_parserWithValidDataSecond_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(secondInvalidCsvFile));
    }

    @Test(expected = RuntimeException.class)
    public void check_parserWithValidDataThird_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(thirdInvalidCsvFile));
    }

    @Test(expected = NumberFormatException.class)
    public void check_parserWithValidDataFourth_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(fourthInvalidCsvFile));
    }

    @Test(expected = RuntimeException.class)
    public void check_parserWithEmptyFile_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(emptyCsvFile));
    }
}
