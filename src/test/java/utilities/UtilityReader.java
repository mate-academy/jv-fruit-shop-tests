package utilities;

import java.util.List;

/**
 * This reader is needed only to test results of WriterImplTest
 * and FruitServiceImplTest
 */
public interface UtilityReader {
    List<String> readFile(String filename);

    String getDataFromList(List<String> dataFromFile);
}
