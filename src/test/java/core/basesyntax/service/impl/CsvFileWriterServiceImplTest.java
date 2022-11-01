package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String OUTPUT_FOUR_PRODUCTS = "src/main/resources/output_4.csv";
    private static final String OUTPUT_ONE_PRODUCT = "src/main/resources/output_1.csv";
    private static final String OUTPUT_WITHOUT_PRODUCTS = "src/main/resources/output_0.csv";
    private static final String SEPARATOR = System.lineSeparator();

    @Test
    public void write_fourProducts_ok() {
        String generatedData = "fruit,quantity" + SEPARATOR
                + "banana,30" + SEPARATOR
                + "apple,100" + SEPARATOR
                + "lemon,150" + SEPARATOR
                + "tomato,1000";;
        getWriter(OUTPUT_FOUR_PRODUCTS).write(generatedData);
        String actual = readFromFile(OUTPUT_FOUR_PRODUCTS);
        assertEquals(generatedData, actual);
    }

    @Test
    public void write_oneProduct_ok() {
        String generatedData = "fruit,quantity" + SEPARATOR
                + "carrot,5000";
        getWriter(OUTPUT_ONE_PRODUCT).write(generatedData);
        String actual = readFromFile(OUTPUT_ONE_PRODUCT);
        assertEquals(generatedData, actual);
    }

    @Test
    public void write_withoutProducts_notOk() {
        String generatedData = "fruit,quantity";
        getWriter(OUTPUT_WITHOUT_PRODUCTS).write(generatedData);
        String actual = readFromFile(OUTPUT_WITHOUT_PRODUCTS);
        assertEquals(generatedData, actual);
    }

    private WriterService getWriter(String toFile) {
        try {
            return new CsvFileWriterServiceImpl(new BufferedWriter(new FileWriter(toFile)));
        } catch (IOException e) {
            throw new RuntimeException("Cannot create file with name: " + toFile);
        }
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
