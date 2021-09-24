package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.service.Transformation;
import java.util.LinkedList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDtoTransformationTest {
    private static Transformation<String, FruitOperationDto> mapper;
    private static List<FruitOperationDto> actual;
    private static List<String> inputValues;

    @BeforeClass
    public static void initialize() {
        mapper = new FruitDtoTransformation();
        actual = new LinkedList<>();
        actual.add(new FruitOperationDto(FruitOperationDto.Type.BALANCE, "apple", 10));
        actual.add(new FruitOperationDto(FruitOperationDto.Type.BALANCE, "banana", 10));
        inputValues = new LinkedList<>();
        inputValues.add("b,apple,10");
        inputValues.add("b,banana,10");

    }

    @Test
    public void fruitDtoTransformation_addCorrectData_Ok() {
        List<FruitOperationDto> expected = mapper.transform(inputValues);
        assertEquals(actual, expected);
    }
}
