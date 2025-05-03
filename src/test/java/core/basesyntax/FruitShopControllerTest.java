package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.controller.FruitShopController;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FruitShopControllerTest {
    private static final String REPORT_FILE = "src/main/resources/finalReport.csv";

    @Test
    void run_shouldGenerateReportFile() {
        new FruitShopController().run();
        assertTrue(Files.exists(Path.of(REPORT_FILE)));
    }
}
