package core.basesyntax.dao;

import java.util.List;

public interface MyFileWriter {

    void writeDataToFile(List<String> data, String fileFullPath);

}
