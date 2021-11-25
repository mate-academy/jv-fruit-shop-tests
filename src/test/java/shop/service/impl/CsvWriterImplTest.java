package shop.service.impl;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.dao.FruitDao;
import shop.dao.FruitDaoImpl;
import shop.db.DataStorage;
import shop.model.Fruit;
import shop.service.Reader;
import shop.service.Writer;

public class CsvWriterImplTest {
    private static FruitDao fruitDao;
    private static Writer writer;
    private static Reader reader;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        writer = new CsvWriterImpl();
        reader = new CsvReaderImpl();
    }

    @AfterClass
    public static void afterAll() {
        DataStorage.storage.clear();
    }

    @Test
    public void csvWriter_write_ok() {
        Fruit fruit = new Fruit("MateFruit", 100);
        fruitDao.add(fruit);
        writer.write("src/test/resources/test.csv");
        List<String> read = reader.read("src/test/resources/test.csv");
        Assert.assertTrue(read.contains("MateFruit,100"));
    }

    @Test
    public void csvWriter_write_couple_strings_ok() {
        Fruit fruit = new Fruit("pineapple", 100);
        Fruit fruit2 = new Fruit("orange", 50);
        fruitDao.add(fruit);
        fruitDao.add(fruit2);
        writer.write("src/test/resources/test.csv");
        List<String> read = reader.read("src/test/resources/test.csv");
        Assert.assertTrue(read.contains("pineapple,100"));
        Assert.assertTrue(read.contains("orange,50"));
    }
}
