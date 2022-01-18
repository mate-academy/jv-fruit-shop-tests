package core.basesyntax.dao;

import java.io.File;
import java.util.List;

public interface MyFileReader {

    List<String> readDataFromFile(File file);

}
