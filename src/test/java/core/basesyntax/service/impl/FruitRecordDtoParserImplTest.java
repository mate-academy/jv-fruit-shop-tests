package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitRecordDtoParser;
import core.basesyntax.service.operations.OperationType;
import core.basesyntax.validate.ValidationData;
import core.basesyntax.validate.impl.ValidationDataImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static FruitRecordDtoParser fruitRecordDtoParser;
    private static ValidationData validationData;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitRecordDtoParser = new FruitRecordDtoParserImpl();
        validationData = new ValidationDataImpl();
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

    @Test
    public void parseValueWithCorrectInputDada_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(OperationType.b,"banana",2);
        List<FruitRecordDto> testList = new ArrayList<>();
        testList.add(fruitRecordDto);
        assertEquals(testList,fruitRecordDtoParser.parse(List.of("b,banana,2")));
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

    @Test
    public void validationByOperationTest_Ok() {
        assertTrue(validationData.validationByOperation("b"));
    }

    @Test (expected = RuntimeException.class)
    public void validationByIncorrectOperationTypeTest_NotOk() {
        assertFalse(validationData.validationByOperation("q"));
    }

    @Test
    public void validationByFruitName_Ok() {
        assertTrue(validationData.validationByFruitName("banana"));
    }

    @Test (expected = RuntimeException.class)
    public void validationByIncorrectFruitName_NotOk() {
        validationData.validationByFruitName("42cat");
    }

    @Test
    public void validationByQuantity_Ok() {
        assertTrue(validationData.validationByQuantity("9"));
    }

    @Test (expected = RuntimeException.class)
    public void validationByIncorrectQuantity_NotOk() {
        validationData.validationByQuantity("asd");
    }

    @Test
    public void validationData_Ok() {
        assertTrue(validationData.validationData("b","banana","21"));
    }

    @Test (expected = RuntimeException.class)
    public void validationIncorrectData_NotOk() {
        validationData.validationData("m","123","asd");
    }
}
