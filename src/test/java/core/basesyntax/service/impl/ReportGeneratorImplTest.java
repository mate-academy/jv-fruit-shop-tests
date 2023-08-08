package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import core.basesyntax.db.FruitsDb;
import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReportGeneratorImplTest {
    private static final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() {
        FruitsDb.fruitDb.clear();
    }

    @ParameterizedTest
    @MethodSource("mapsWithFruits")
    void generateReport_Ok(Map<String, Integer> fruitMap, String report) {
        assertDoesNotThrow(() -> {
            FruitsDb.fruitDb.putAll(fruitMap);
            String expexted = report;
            String actual = reportGenerator.generateReport();
            assertEquals(expexted, actual, "Genarated report is doesnt match requirements");
        }, "Generatig report shouldnt throw any exceptions");

    }

    static Stream<Arguments> mapsWithFruits() {
        return Stream.of(
                arguments(
                        new HashMap<String, Integer>() {{
                            put("fruit1", 1);
                            put("fruit2", 2);
                            put("fruit3", 3);
                        }},
                        "fruit,quantity" + System.lineSeparator()
                                + "fruit3," + 3 + System.lineSeparator()
                                + "fruit2," + 2 + System.lineSeparator()
                                + "fruit1," + 1 + System.lineSeparator()
                ),
                arguments(
                        new HashMap<String, Integer>(),
                        "fruit,quantity" + System.lineSeparator()
                ),
                arguments(
                        new HashMap<String, Integer>() {{
                            put("fruit", Integer.MAX_VALUE);
                        }},
                        "fruit,quantity" + System.lineSeparator()
                                + "fruit," + Integer.MAX_VALUE + System.lineSeparator()
                )

        );
    }
}
