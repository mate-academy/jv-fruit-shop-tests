package core.basesyntax.service;

import java.util.List;
import core.basesyntax.model.FruitTransaction;

public interface DataProcessorService {
    /**
     * Process data from CSV-file.
     *
     * @param inputData list of lines from  CSV- file.
     */

    void process(List<FruitTransaction> inputData);
}
