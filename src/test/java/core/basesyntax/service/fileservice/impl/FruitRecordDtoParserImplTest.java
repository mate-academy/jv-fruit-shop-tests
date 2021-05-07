package core.basesyntax.service.fileservice.impl;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.model.dto.impl.FileReaderImpl;
import core.basesyntax.service.impl.OperationType;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static final FruitRecordDtoParserImpl fruitParser
            = new FruitRecordDtoParserImpl();
    private static List<String> linesFromFile;
    private static List<FruitRecordDto> actualList;
    private static List<FruitRecordDto> expectedList;

    @Before
    public void setUp() {
        linesFromFile = new FileReaderImpl().readFile("OperationsForTestParse.csv");
        expectedList = new ArrayList<>();
    }

    @Test
    public void parseInputStringTest_Ok() {
        actualList = fruitParser.parse(linesFromFile);
        expectedList.add(new FruitRecordDto(OperationType.BALANCE,"banana",20));
        expectedList.add(new FruitRecordDto(OperationType.BALANCE,"apple",100));
        expectedList.add(new FruitRecordDto(OperationType.SUPPLY,"banana",100));
        expectedList.add(new FruitRecordDto(OperationType.PURCHASE,"banana",13));
        Assert.assertEquals(expectedList,actualList);
    }
}
