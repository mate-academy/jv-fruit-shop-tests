package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import core.basesyntax.model.FruitActivity;
import core.basesyntax.service.DataParser;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DataParserImplTest {

    private static final DataParser dataParser = new DataParserImpl();

    @ParameterizedTest
    @MethodSource("validLines")
    void parseData_validActivities_Ok(List<String> lines, List<FruitActivity> activities) {
        assertDoesNotThrow(() -> {
            List<FruitActivity> expected = activities;
            List<FruitActivity> actual = dataParser.parseLines(lines);
            assertEquals(expected, actual, "Data parser doesnt work properly");
        }, "Parsing valid lines shouldnt throw any exceptions");
    }

    static Stream<Arguments> validLines() {
        String head = "type,fruit,quantity";
        return Stream.of(
                arguments(
                        new ArrayList<String>() {{
                            add(head);
                            add("b,banana,20");
                            add("b,apple,100");
                            add("s,banana,100");
                            add("p,banana,13");
                        }},
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 20));
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "apple", 100));
                            add(new FruitActivity(FruitActivity.Type.SUPPLY, "banana", 100));
                            add(new FruitActivity(FruitActivity.Type.PURCHASE, "banana", 13));
                        }}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidLines")
    void parseData_invalidActivities_NotOk(List<String> lines) {
        assertThrows(RuntimeException.class,
                () -> dataParser.parseLines(lines),
                "Parsing invalid lines should throw exception");

    }

    static Stream<Arguments> invalidLines() {
        String head = "type,fruit,quantity";
        return Stream.of(
                arguments(null, null),
                arguments(
                        new ArrayList<String>() {{
                            add(head);
                            add("l,banana,20");
                            add("n,apple,100");
                            }}
                )
        );
    }
}
