package core.basesyntax;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.servicesimpl.FileCsvReaderImpl;
import core.basesyntax.servicesimpl.FileCsvWriterImpl;
import core.basesyntax.servicesimpl.FruitShopSupplierImpl;
import core.basesyntax.servicesimpl.ParseFruitNamesImpl;
import core.basesyntax.servicesimpl.ResultMessageImpl;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static final File FILE_TO_WRITE_IN = new File("src/main/resources/fileInWriting.csv");
    private static final File FILE_TO_READ = new File("src/main/resources/fileFromReading.csv");

    public static void main(String[] args) throws IOException {
        List<String> list = new FileCsvReaderImpl().readFromFile(FILE_TO_READ.getPath());
        Map<String, Fruit> fruitMap = new ParseFruitNamesImpl().getFruitNamesMap(list);
        fruitMap = new FruitShopSupplierImpl().fillTheMap(list);
        String message = new ResultMessageImpl().makeMessage(new FruitDaoImpl());
        new FileCsvWriterImpl().writeInFile(message, FILE_TO_WRITE_IN.getPath());
    }
}
