package core.basesyntax;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.RecordDtoParser;
import core.basesyntax.service.implementions.RecordDtoParserImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RecordDtoParserImplTest {
    private static final String FILE_NAME_FROM = "src/test/java/resources/inputCorrect.csv";
    private static final String FILE_NAME_WITH_INVALID_DATA =
                                        "src/test/java/resources/inputInvalidData.csv";
    private static final String FILE_NAME_WITH_INVALID_DATA_SIZE =
                                        "src/test/java/resources/inputInvalidSize.csv";
    private static final String FILE_NAME_WITH_INVALID_OPERATION =
                                        "src/test/java/resources/inputInvalidOperationType.csv";
    private static RecordDtoParser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new RecordDtoParserImpl();
    }

    @Test
    public void parse_DataFromFile_isOk() {
        Fruit apple = new Fruit("apple");
        Fruit banana = new Fruit("banana");
        List<String> dataFromFile = readFromFile(FILE_NAME_FROM);
        List<FruitRecordDto> expectedResult = List.of(
                new FruitRecordDto(banana, 20, OperationType.BALANCE),
                new FruitRecordDto(apple, 100, OperationType.BALANCE),
                new FruitRecordDto(banana, 100, OperationType.SUPPLY),
                new FruitRecordDto(banana, 13, OperationType.PURCHASE),
                new FruitRecordDto(apple, 10, OperationType.RETURN));
        List<FruitRecordDto> actualResult = parser.parse(dataFromFile);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void parse_InvalidData_NotOk() {
        List<String> dataFromFile = readFromFile(FILE_NAME_WITH_INVALID_DATA);
        parser.parse(dataFromFile);
    }

    @Test (expected = RuntimeException.class)
    public void parse_InvalidDataSize_NotOk() {
        List<String> dataFromFile = readFromFile(FILE_NAME_WITH_INVALID_DATA_SIZE);
        parser.parse(dataFromFile);
    }

    @Test (expected = RuntimeException.class)
    public void parse_InvalidOperationType_NotOk() {
        List<String> dataFromFile = readFromFile(FILE_NAME_WITH_INVALID_OPERATION);
        parser.parse(dataFromFile);
    }

    private List<String> readFromFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + fileName, e);
        }
    }
}
