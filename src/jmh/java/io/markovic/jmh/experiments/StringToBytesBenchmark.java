package io.markovic.jmh.experiments;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;


public class StringToBytesBenchmark {

  @Benchmark
  @Fork(1)
  @Threads(1)
  @Warmup(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
  @Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public byte[] directConversionWithCheck(StringState state) {
    String str = state.testString;
    byte[] bytes = new byte[str.length()];
    for (int i = 0; i < str.length(); ++i) {
      byte b = (byte) str.charAt(i);
      if (b < 0) {
        throw new RuntimeException("bla");
      }
      bytes[i] = b;
    }
    return bytes;
  }

  @Benchmark
  @Fork(1)
  @Threads(1)
  @Warmup(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
  @Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public byte[] directConversion(StringState state) {
    String str = state.testString;
    byte[] bytes = new byte[str.length()];
    for (int i = 0; i < str.length(); ++i) {
      bytes[i] = (byte) str.charAt(i);
    }
    return bytes;
  }

  @Benchmark
  @Fork(1)
  @Threads(1)
  @Warmup(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
  @Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public byte[] getBytes(StringState state) {
    String str = state.testString;
    return str.getBytes(StandardCharsets.US_ASCII);
  }

  @Benchmark
  @Fork(1)
  @Threads(1)
  @Warmup(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
  @Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public byte[] getBytesUtf8(StringState state) {
    String str = state.testString;
    return str.getBytes(StandardCharsets.UTF_8);
  }

  @State(Scope.Benchmark)
  public static class StringState {
    String testString =
        "sfhkjsbkbsfbdskjbflkjsbfkjsdflkjdsflkjsbdglkjbslkjbgslkjbghslkjdgldsfgknmsflkjnmsflkjnslkngsuibuil";
  }

  /*
  Results
  Benchmark                                         Mode  Cnt    Score   Error  Units
StringToBytesBenchmark.directConversion           avgt   10   74.786 ± 2.231  ns/op
StringToBytesBenchmark.directConversionWithCheck  avgt   10   94.254 ± 4.749  ns/op
StringToBytesBenchmark.getBytes                   avgt   10   94.330 ± 1.559  ns/op
StringToBytesBenchmark.getBytesUtf8               avgt   10  140.309 ± 2.183  ns/op

   */
}
