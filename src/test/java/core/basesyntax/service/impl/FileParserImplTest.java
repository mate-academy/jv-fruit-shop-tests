package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitModel;
import core.basesyntax.model.OperationModel;
import core.basesyntax.service.FileParserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileParserImplTest {
    private FileParserService fileParser;

    @BeforeEach
    void setUp() {
        fileParser = new FileParserImpl();
    }

    @Test
    void testParse_ValidInput_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,15",
                "r,pineapple,10"
        );
        List<OperationModel> operations = fileParser.parse(input);
        assertEquals(3, operations.size());
        OperationModel.Operation[] expectedOperations = {
                OperationModel.Operation.BALANCE,
                OperationModel.Operation.SUPPLY,
                OperationModel.Operation.RETURN
        };
        FruitModel[] expectedFruits = {
                FruitModel.BANANA,
                FruitModel.APPLE,
                FruitModel.PINEAPPLE
        };
        int[] expectedQuantities = {20, 15, 10};
        for (int i = 0; i < operations.size(); i++) {
            assertEquals(expectedOperations[i], operations.get(i).getOperation());
            assertEquals(expectedFruits[i], operations.get(i).getFruit());
            assertEquals(expectedQuantities[i], operations.get(i).getAmount());
        }
    }

    @Test
    void testParse_InvalidInput_throwsException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana",
                "r,apple,invalid_quantity"
        );
        assertThrows(IllegalArgumentException.class, () -> fileParser.parse(input));
    }

    @Test
    void testParse_EmptyInput_notOk() {
        List<String> input = List.of();
        List<OperationModel> operations = fileParser.parse(input);
        assertTrue(operations.isEmpty());
    }

    @Test
    void testParse_HeaderOnly_notOk() {
        List<String> input = List.of("type,fruit,quantity");
        List<OperationModel> operations = fileParser.parse(input);
        assertTrue(operations.isEmpty());
    }

    @Test
    void testParse_SingleLineInput_ok() {
        List<String> input = List.of("type,fruit,quantity",
                "b,pineapple,10");
        List<OperationModel> operations = fileParser.parse(input);
        assertEquals(1, operations.size());
        assertEquals(OperationModel.Operation.BALANCE, operations.get(0).getOperation());
        assertEquals(FruitModel.PINEAPPLE, operations.get(0).getFruit());
        assertEquals(10, operations.get(0).getAmount());
    }

    @Test
    void testParse_InvalidOperationCode_throwsException() {
        List<String> input = List.of("type,fruit,quantity",
                "x,lime,10");
        assertThrows(IllegalArgumentException.class, () -> fileParser.parse(input));
    }

    @Test
    void parse_invalidQuantity_throwsException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "p,pineapple,abc"
        );
        NumberFormatException thrown = assertThrows(NumberFormatException.class, () -> {
            fileParser.parse(input);
        });
        assertTrue(thrown.getMessage().contains("For input string"));
    }

    @Test
    void testParse_InvalidFruitType_throwsException() {
        List<String> input = List.of("type,fruit,quantity",
                "b,pear,10");
        assertThrows(IllegalArgumentException.class, () -> fileParser.parse(input));
    }

    @Test
    void testParse_NegativeQuantityPurchase_throwsException() {
        List<String> inputData = List.of("type,fruit,quantity",
                "p,banana,-20");
        assertThrows(IllegalArgumentException.class, () -> {
            fileParser.parse(inputData);
        });
    }

    @Test
    void testParse_NegativeQuantityBalance_throwsException() {
        List<String> inputData = List.of("type,fruit,quantity",
                "b,lime,-56");
        assertThrows(IllegalArgumentException.class, () -> {
            fileParser.parse(inputData);
        });
    }

    @Test
    void parse_invalidSeparator_throwsException() {
        List<String> firstInput = List.of(
                "type,fruit,quantity",
                "s;peach;48"
        );
        List<String> secondInput = List.of(
                "type,fruit,quantity",
                "s peach 48"
        );
        IllegalArgumentException firstException = assertThrows(IllegalArgumentException.class,
                () -> fileParser.parse(firstInput));
        assertEquals("Unexpected data type", firstException.getMessage());
        IllegalArgumentException secondException = assertThrows(IllegalArgumentException.class,
                () -> fileParser.parse(secondInput));
        assertEquals("Unexpected data type", secondException.getMessage());
    }
}
