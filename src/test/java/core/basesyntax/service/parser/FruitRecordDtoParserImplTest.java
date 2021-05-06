package core.basesyntax.service.parser;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.fileservice.FileReader;
import core.basesyntax.service.fileservice.FileReaderForCsvImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static final String VALID_CSV_FILE = "src/test/resources/valid_instructions.csv";
    private static final String FIRST_INVALID_CSV_FILE =
            "src/test/resources/invalid_instructions.csv";
    private static final String SECOND_INVALID_CSV_FILE =
            "src/test/resources/invalid_instructions_2.csv";
    private static final String THIRD_INVALID_CSV_FILE =
            "src/test/resources/invalid_instructions_3.csv";
    private static final String FOURTH_INVALID_CSV_FILE =
            "src/test/resources/invalid_instructions_4.csv";
    private static final String emptyCsvFile = "src/test/resources/empty.csv";
    private static final String invalidPathCsvFile = "src/test/resourcesempty.csv";
    private static final FruitRecordDtoParser parser = new FruitRecordDtoParserImpl();
    private static final FileReader fileReader = new FileReaderForCsvImpl();
    private static List<FruitRecordDto> expected;

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
        List<FruitRecordDto> actual = parser.parse(fileReader.readAllLinesFromFile(VALID_CSV_FILE));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_parserWithValidDataFirst_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(FIRST_INVALID_CSV_FILE));
    }

    @Test(expected = RuntimeException.class)
    public void check_parserWithValidDataSecond_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(SECOND_INVALID_CSV_FILE));
    }

    @Test(expected = RuntimeException.class)
    public void check_parserWithValidDataThird_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(THIRD_INVALID_CSV_FILE));
    }

    @Test(expected = NumberFormatException.class)
    public void check_parserWithValidDataFourth_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(FOURTH_INVALID_CSV_FILE));
    }

    @Test(expected = RuntimeException.class)
    public void check_parserWithEmptyFile_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(emptyCsvFile));
    }

    @Test(expected = RuntimeException.class)
    public void check_parserWrongPath_Not_OK() {
        parser.parse(fileReader.readAllLinesFromFile(invalidPathCsvFile));
    }
}
