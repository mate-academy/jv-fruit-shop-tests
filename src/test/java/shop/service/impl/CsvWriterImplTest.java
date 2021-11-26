package shop.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.db.DataStorage;
import shop.service.Writer;

public class CsvWriterImplTest {
    private static Writer writer;

    @BeforeClass
    public static void beforeAll() {
        writer = new CsvWriterImpl();
    }

    @AfterClass
    public static void afterAll() {
        DataStorage.storage.clear();
    }

    @Test
    public void csvWriter_write_ok() {
        List<String> list = new ArrayList<>();
        list.add("Congratulations");
        writer.write(list,"src/test/resources/test.csv");
        List<String> read;
        File fromFile = new File("src/test/resources/test.csv");
        try {
            read = Files.readAllLines(fromFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file ", e);
        }
        Assert.assertTrue(read.contains("Congratulations"));
    }

    @Test
    public void csvWriter_write_couple_strings_ok() {
        List<String> list = new ArrayList<>();
        list.add("something about java");
        list.add("wanna some tests?");
        writer.write(list, "src/test/resources/test.csv");
        List<String> read;
        File fromFile = new File("src/test/resources/test.csv");
        try {
            read = Files.readAllLines(fromFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file ", e);
        }
        Assert.assertTrue(read.contains("something about java"));
        Assert.assertTrue(read.contains("wanna some tests?"));
    }
}
