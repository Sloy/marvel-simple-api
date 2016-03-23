package net.infojobs.marvel;

import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsConcat {

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
}
