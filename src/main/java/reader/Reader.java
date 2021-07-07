package reader;

import java.util.List;

@FunctionalInterface
public interface Reader<J, V> {
    List<J> read(V value);
}
