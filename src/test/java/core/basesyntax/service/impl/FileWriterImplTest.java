package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitShopException;
import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String VALID_PATH = "src/test/java/core/basesyntax/resources/report.csv";
    private static final String INVALID_PATH = "vH-@&5nNSz93";
    private static final String REPORT_PATH = "src/test/java/core/basesyntax/resources/report.csv";
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void writeToFile_validDataAndPath_ok() throws IOException {
        String expected = "type,fruit,quantity\n"
                + "    b,banana,20\n"
                + "    b,apple,100\n"
                + "    s,banana,100\n"
                + "    p,banana,13\n"
                + "    r,apple,10\n"
                + "    p,apple,20\n"
                + "    p,banana,5\n"
                + "    s,banana,50";
        fileWriter.writeToFile(REPORT_PATH, expected);
        String actual = Files.readString(Path.of(REPORT_PATH), StandardCharsets.UTF_8);
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_invalidPath_notOk() {
        assertThrows(FruitShopException.class,
                () -> fileWriter.writeToFile(INVALID_PATH, "report"));
    }

    @Test
    void writeToFile_nullPath_notOk() {
        assertThrows(FruitShopException.class,
                () -> fileWriter.writeToFile(null, "report"));
    }

    @Test
    void writeToFile_nullReport_notOk() {
        assertThrows(FruitShopException.class,
                () -> fileWriter.writeToFile(VALID_PATH, null));
    }
}
