package core.basesyntax.service.file.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.file.ShopFileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ShopFileReaderImplTest {
    private static final String CONTENT_HEADER = "type,fruit,quantity";
    private static final String CONTENT_LINE_1 = "b,banana,20";
    private static final String CONTENT_LINE_2 = "s,apple,10";
    private ShopFileReader shopFileReader;
    @TempDir
    private Path tempDir;

    @BeforeEach
    void beforeEach() {
        shopFileReader = new ShopFileReaderImpl();
    }

    @Test
    void readCsv_readsFileSuccessfully_ok() throws IOException {
        Path inputFilePath = tempDir.resolve("testInput.csv");
        Files.write(inputFilePath, List.of(CONTENT_HEADER, CONTENT_LINE_1, CONTENT_LINE_2));

        List<String> actualLines = shopFileReader.readCsv(inputFilePath.toString());

        assertEquals(2, actualLines.size());
        assertEquals(CONTENT_LINE_1, actualLines.get(0));
        assertEquals(CONTENT_LINE_2, actualLines.get(1));
    }

    @Test
    void readCsv_invalidPath_notOk() {
        assertThrows(RuntimeException.class, () -> shopFileReader.readCsv("qwe"));
    }

    @Test
    void readCsv_emptyFile_ok() throws IOException {
        Path inputFilePath = tempDir.resolve("testInput.csv");
        Files.write(inputFilePath, List.of());
        List<String> actualLines = shopFileReader.readCsv(inputFilePath.toString());
        assertEquals(List.of(), actualLines);
    }
}
