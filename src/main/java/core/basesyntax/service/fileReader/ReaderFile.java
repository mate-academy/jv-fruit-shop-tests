package core.basesyntax.service.fileReader;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ReaderFile {
    List<FruitTransaction> readFile(String inputFileName);
}
