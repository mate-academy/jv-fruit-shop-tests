package core.basesyntax.service.impl;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private static final String INCORRECT_PATH_OF_OUTPUT_FILE
            = "src/test/service/resources/output.csv";
    private static final String INCORRECT_NAME_OF_OUTPUT_FILE
            = "src/test/service/resources/not_output.csv";
    private static final String CORRECT_PATH_OF_OUTPUT_FILE
            = "src/test/resources/output.csv";
    private static final String CORRECT_DATA_OF_REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "apple,90"
            + System.lineSeparator()
            + "banana,152";
    private static Writer writer;

    @BeforeEach
    void setUp() {
        writer = new WriterImpl();
    }

    @Test
    void writeToFile_nullDataOfOutputFilePath_notOk() {
        Assert.assertThrows(NullPointerException.class, () ->
                writer.writeToFile(null, CORRECT_DATA_OF_REPORT));
    }

    @Test
    void writeToFile_nullDataOfReport_notOk() {
        Assert.assertThrows(NullPointerException.class, () ->
                writer.writeToFile(CORRECT_PATH_OF_OUTPUT_FILE, null));
    }

    @Test
    void writeToFile_incorrectPathOfOutputFile_notOk() {
        Assert.assertThrows(FruitShopException.class, () ->
                writer.writeToFile(INCORRECT_PATH_OF_OUTPUT_FILE, CORRECT_DATA_OF_REPORT));
    }

    @Test
    void writeToFile_incorrectNameOfOutputFile_notOk() {
        Assert.assertThrows(FruitShopException.class, () ->
                writer.writeToFile(INCORRECT_NAME_OF_OUTPUT_FILE, CORRECT_DATA_OF_REPORT));
    }

    @Test
    void writeToFile_correctWritingToOutputFile_ok() {
        writer.writeToFile(CORRECT_PATH_OF_OUTPUT_FILE, CORRECT_DATA_OF_REPORT);
        String actual;
        try (Stream<String> fileLines = Files.lines(Paths.get(CORRECT_PATH_OF_OUTPUT_FILE))) {
            actual = fileLines.collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new FruitShopException("Can't read data from file by path "
                    + CORRECT_PATH_OF_OUTPUT_FILE);
        }
        Assertions.assertEquals(CORRECT_DATA_OF_REPORT, actual);
    }
}
