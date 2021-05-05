package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitRecordDtoParser;
import core.basesyntax.service.operations.OperationType;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static FruitRecordDtoParser fruitRecordDtoParser;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitRecordDtoParser = new FruitRecordDtoParserImpl();
    }

    @Test
    public void parseOperationType_Ok() {
        assertEquals(OperationType.b,fruitRecordDtoParser.parse(List.of("b,banana,20"))
                .get(0).getOperationType());
        assertEquals(OperationType.s,fruitRecordDtoParser.parse(List.of("s,apple,2"))
                .get(0).getOperationType());
        assertEquals(OperationType.p,fruitRecordDtoParser.parse(List.of("p,banana,6"))
                .get(0).getOperationType());
        assertEquals(OperationType.r,fruitRecordDtoParser.parse(List.of("r,apple,13"))
                .get(0).getOperationType());
    }

    @Test
    public void parseFruitName_Ok() {
        assertEquals("banana",fruitRecordDtoParser.parse(List.of("b,banana,20"))
                .get(0).getFruitName());
        assertEquals("apple",fruitRecordDtoParser.parse(List.of("b,apple,20"))
                .get(0).getFruitName());
    }

    @Test(expected = RuntimeException.class)
    public void parseBadOperationType_NotOk() {
        fruitRecordDtoParser.parse(List.of("y,coconut,21"));
        fruitRecordDtoParser.parse(List.of("#4@,dog,5"));
    }

    @Test (expected = RuntimeException.class)
    public void parseValueWithIncorrectInputData_notOk() {
        fruitRecordDtoParser.parse(List.of("pvalue31"));
    }
}
