package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static final String OUTPUT_FILE = "src/test/resources/test_input.csv";
    private static final String NON_EXISTENT_OUTPUT_FILE = "src/test/someResources/shop_input.csv";
    private static final String EXPECTED_OUTPUT = "fruit,quantity" + System.lineSeparator()
                                                    + "banana,90" + System.lineSeparator()
                                                    + "apple,100" + System.lineSeparator();
    private static List<String> output;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
        output = List.of(
                "fruit,quantity", "banana,90", "apple,100");
    }

    @Test
    void writeToFile_validFile_ok() throws IOException {
        StringBuilder builder = new StringBuilder();
        fileWriterService.writeIntoFile(output, OUTPUT_FILE);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        }
        assertEquals(EXPECTED_OUTPUT, builder.toString());
    }

    @Test
    void writeToFIle_invalidFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeIntoFile(output, NON_EXISTENT_OUTPUT_FILE));
    }
}
