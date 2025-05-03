package core.basesyntax.dao;

import static core.basesyntax.dao.FileReaderCsvImplTest.EMPTY_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String OUTPUT_FILE = "src/test/java/resources/TestOutput.csv";
    private FruitFileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void testFileWriterThrowsException_NotOk() {
        assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(List.of(), EMPTY_FILE),
                "Should throw RuntimeException when writing with empty file path"
        );
    }

    @Test
    void testFileWriter_Ok() throws IOException {
        Path outPut = Path.of(OUTPUT_FILE);
        List<String> inputList = List.of(
                "fruit,quantity",
                "banana,152",
                "apple,90"

        );
        fileWriter.write(inputList, OUTPUT_FILE);
        List<String> fileOutPutImpl = Files.readAllLines(outPut);
        assertEquals(fileOutPutImpl,
                inputList,
                "Written content should match expected output");
    }
}
