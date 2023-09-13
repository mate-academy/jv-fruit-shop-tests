package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.CsvParserImpl;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CsvParserServiceTest {
    private static final String HEADER = "type,fruit,quantity";
    private static CsvParserService csvParserService;

    @BeforeAll
    static void beforeAll() {
        csvParserService = new CsvParserImpl();
    }

    @Test
    void parse_ValidListData_Ok() {
        List<String> validList = List.of(
                HEADER, "b,banana,20", "b,apple,100",
                "s,banana,100", "p,apple,20", "p,banana,20"
        );
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, new Fruit("banana"), 20),
                new FruitTransaction(Operation.BALANCE, new Fruit("apple"), 100),
                new FruitTransaction(Operation.SUPPLY, new Fruit("banana"), 100),
                new FruitTransaction(Operation.PURCHASE, new Fruit("apple"), 20),
                new FruitTransaction(Operation.PURCHASE, new Fruit("banana"), 20)
        );
        List<FruitTransaction> actual = csvParserService.parse(validList);
        assertEquals(expected, actual);
    }

    @Test
    void parse_EmptyListDataWithValidHeader_Ok() {
        List<String> emptyList = List.of(HEADER);
        List<FruitTransaction> expected = List.of();
        List<FruitTransaction> actual = csvParserService.parse(emptyList);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("invalidHeaderData")
    void parse_EmptyListDataInvalidHeader_NotOk(List<String> wrongHeaderList) {
        assertThrows(RuntimeException.class, () -> csvParserService.parse(wrongHeaderList),
                "List must start with header " + HEADER);
    }

    @ParameterizedTest
    @MethodSource("invalidQuantityData")
    void parse_ListWithInvalidAmountType_NotOk(List<String> invalidQuantity) {
        assertThrows(RuntimeException.class, () -> csvParserService.parse(invalidQuantity),
                "amount must be an integer type");
    }

    @Test
    void parse_ListWithNegativeQuantity_NotOk() {
        List<String> invalidData = List.of(
                HEADER, "b,banana,-10", "b,apple,20"
        );
        assertThrows(RuntimeException.class, () -> csvParserService.parse(invalidData),
                "amount must be positive");
    }

    @Test
    void parse_ListWithInvalidOperation_NotOk() {
        List<String> invalidData = List.of(
                HEADER, "balance,banana,10", "b,apple,20"
        );
        assertThrows(RuntimeException.class, () -> csvParserService.parse(invalidData),
                "invalid operation type");
    }

    @ParameterizedTest
    @MethodSource("invalidRowFormatData")
    void parse_ListWithInvalidRowFormat_NotOk(List<String> invalidData) {
        assertThrows(RuntimeException.class, () -> csvParserService.parse(invalidData),
                "Invalid number of columns in a row");
    }

    private static Stream<List<String>> invalidHeaderData() {
        return Stream.of(
                List.of(),
                List.of("types,fruits,quantity"),
                List.of("type,fruit,quantity,"),
                List.of("b,banana,20")
        );
    }

    private static Stream<List<String>> invalidQuantityData() {
        return Stream.of(
                List.of(HEADER, "b,banana,1.0"),
                List.of(HEADER, "b,banana,20,0"),
                List.of(HEADER, "b,banana,"),
                List.of(HEADER, "b,banana 20")
        );
    }

    private static Stream<List<String>> invalidRowFormatData() {
        return Stream.of(
                List.of(HEADER, "b banana 10"),
                List.of(HEADER, "b,banana,10, "),
                List.of(HEADER,"b.banana,10"),
                List.of(HEADER, " , , ")
        );
    }
}
