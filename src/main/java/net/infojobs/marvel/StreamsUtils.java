package net.infojobs.marvel;

import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamsUtils {

    public static <T> Collector<T, ?, Stream<T>> concat(T element) {
        return concat(Stream.of(element));
    }

    public static <T> Collector<T, ?, Stream<T>> concat(Stream<? extends T> other) {
        return combine(Collectors.toList(),
          list -> Stream.concat(list.stream(), other));
    }

    private static <T, A, R, S> Collector<T, ?, S> combine(Collector<T, A, R> collector, Function<? super R, ? extends S> function) {
        return Collector.of(
          collector.supplier(),
          collector.accumulator(),
          collector.combiner(),
          collector.finisher().andThen(function));
    }

    public static <T> Collector<T, ?, T> singleItem() {
        return Collectors.collectingAndThen(toList(), l -> {
            if (l.size() == 1) return l.get(0);
            throw new RuntimeException();
        });
    }
}
