package core.basesyntax.service;

import java.util.List;

public interface DataFromOrderProcessor {
    List<String[]> split(List<String> order);
}
