package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String OUTPUT_FOUR_PRODUCTS = "src/main/resources/output_4.csv";
    private static final String OUTPUT_ONE_PRODUCT = "src/main/resources/output_1.csv";
    private static final String OUTPUT_WITHOUT_PRODUCTS = "src/main/resources/output_0.csv";

    @Test
    public void write_fourProducts_ok() {
        String generatedData = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "lemon,150" + System.lineSeparator()
                + "tomato,1000";;
        getWriter(OUTPUT_FOUR_PRODUCTS).write(generatedData);
        String actual = readFromFile(OUTPUT_FOUR_PRODUCTS);
        assertEquals(generatedData, actual);
    }

    @Test
    public void write_oneProduct_ok() {
        String generatedData = "fruit,quantity" + System.lineSeparator()
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
            return new FileWriterServiceImpl(new BufferedWriter(new FileWriter(toFile)));
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
