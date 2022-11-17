package core.basesyntax.dao;

import java.util.List;

public interface IStorageDao {
    List<String> getData(String path);

    void putData(String path);
}
