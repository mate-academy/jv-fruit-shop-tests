package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitDto;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CsvFileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CsvFileReaderTest {
    private CsvFileReader csvFileReader;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        csvFileReader = new CsvFileReader();
    }

    @Test
    void readData_validFile_success() throws IOException {
        Path file = tempDir.resolve("input.csv");
        Files.write(file, List.of(
                "type,fruit,quantity",
                "b,banana,10",
                "s,apple,5"
        ));
        List<FruitDto> expected = List.of(
                new FruitDto(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitDto(FruitTransaction.Operation.SUPPLY, "apple", 5)
        );

        List<FruitDto> actual = csvFileReader.readData(file.toString());

        assertEquals(expected, actual);
    }

    @Test
    void readData_missingFile_throwsException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> csvFileReader.readData(tempDir.resolve("absent.csv").toString()));

        assertEquals("File not found", exception.getMessage());
    }
}
