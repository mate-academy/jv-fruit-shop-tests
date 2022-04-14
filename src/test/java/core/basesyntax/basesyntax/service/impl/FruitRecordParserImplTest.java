package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.basesyntax.service.FruitRecordParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordParserImplTest {
    private static FruitRecordParser fruitRecordParser;
    private static final String INPUT_FILE
            = "src/test/java/core/basesyntax/basesyntax/resources/inputData.csv";
    private static final String INCORRECT_DATA
            = "src/test/java/core/basesyntax/basesyntax/resources/incorrectData.csv";
    private static final String INPUT_NEGATIVE_NUMBERS
            = "src/test/java/core/basesyntax/basesyntax/resources/inputNegativeNumbers.csv";
    private static final String INCORRECT_FIELD_NUMBER
            = "src/test/java/core/basesyntax/basesyntax/resources/incorrectFieldNumber";
    private static final String INVALID_OPERATION
            = "src/test/java/core/basesyntax/basesyntax/resources/invalidOperation";
    private static final String INCORRECT_FORMAT
            = "src/test/java/core/basesyntax/basesyntax/resources/incorrectFormat";

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitRecordParser = new FruitRecordParserImpl();
    }

    @Test
    public void parse_inputData_isOk() throws IOException {
        List<String> inputData = Files.readAllLines(Path.of(INPUT_FILE));
        List<FruitRecordDto> dtos = new ArrayList<>();
        dtos.add(new FruitRecordDto("s", "apple", 100));
        dtos.add(new FruitRecordDto("r", "banana", 50));
        dtos.add(new FruitRecordDto("p", "apple", 50));
        List<FruitRecordDto> actual = fruitRecordParser.parse(inputData);
        Assert.assertEquals(dtos.toString(), actual.toString());
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectData_NotOk() throws IOException {
        List<String> inputData = Files.readAllLines(Path.of(INCORRECT_DATA));
        fruitRecordParser.parse(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_negativeNumbers_NotOk() throws IOException {
        List<String> inputData = Files.readAllLines(Path.of(INPUT_NEGATIVE_NUMBERS));
        fruitRecordParser.parse(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectFieldNumber_NotOk() throws IOException {
        List<String> inputData = Files.readAllLines(Path.of(INCORRECT_FIELD_NUMBER));
        fruitRecordParser.parse(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidOperation_NotOk() throws IOException {
        List<String> inputData = Files.readAllLines(Path.of(INVALID_OPERATION));
        fruitRecordParser.parse(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectFormat_NotOk() throws IOException {
        List<String> inputData = Files.readAllLines(Path.of(INCORRECT_FORMAT));
        fruitRecordParser.parse(inputData);
    }
}
