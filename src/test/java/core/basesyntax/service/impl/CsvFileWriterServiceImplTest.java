package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.creator.CsvFileWriterServiceImplCreator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String OUTPUT_FOUR_PRODUCTS = "src/main/resources/output_4.csv";
    private static final String OUTPUT_ONE_PRODUCT = "src/main/resources/output_1.csv";
    private static final String OUTPUT_WITHOUT_PRODUCTS = "src/main/resources/output_0.csv";
    private static final String SEPARATOR = System.lineSeparator();
    private static CsvFileWriterServiceImplCreator writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new CsvFileWriterServiceImplCreator();
    }

    @Test
    public void write_fourProducts_ok() {
        String generatedData = "fruit,quantity" + SEPARATOR
                + "banana,30" + SEPARATOR
                + "apple,100" + SEPARATOR
                + "lemon,150" + SEPARATOR
                + "tomato,1000";
        writer.createWriter(OUTPUT_FOUR_PRODUCTS).write(generatedData);
        String actual = readFromFile(OUTPUT_FOUR_PRODUCTS);
        assertEquals(generatedData, actual);
    }

    @Test
    public void write_oneProduct_ok() {
        String generatedData = "fruit,quantity" + SEPARATOR
                + "carrot,5000";
        writer.createWriter(OUTPUT_ONE_PRODUCT).write(generatedData);
        String actual = readFromFile(OUTPUT_ONE_PRODUCT);
        assertEquals(generatedData, actual);
    }

    @Test
    public void write_withoutProducts_notOk() {
        String generatedData = "fruit,quantity";
        writer.createWriter(OUTPUT_WITHOUT_PRODUCTS).write(generatedData);
        String actual = readFromFile(OUTPUT_WITHOUT_PRODUCTS);
        assertEquals(generatedData, actual);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
