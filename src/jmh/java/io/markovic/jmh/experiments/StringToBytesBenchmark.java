package io.markovic.jmh.experiments;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
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
  public byte[] directConversionWithGuavaCheck(StringState state) {
    String str = state.testString;
    Preconditions.checkArgument(CharMatcher.ascii().matchesAllOf(str));
    byte[] bytes = new byte[str.length()];
    for (int i = 0; i < str.length(); ++i) {
      byte b = (byte) str.charAt(i);
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
  public byte[] directConversionWithIfCheck(StringState state) {
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
  public byte[] directConversionAscii(StringState state) {
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
  public byte[] getBytesAscii(StringState state) {
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

StringToBytesBenchmark.directConversionAscii           avgt   10   76.413 ± 6.435  ns/op
StringToBytesBenchmark.directConversionWithGuavaCheck  avgt   10  124.436 ± 5.530  ns/op
StringToBytesBenchmark.directConversionWithIfCheck     avgt   10   92.406 ± 1.699  ns/op
StringToBytesBenchmark.getBytesAscii                   avgt   10   96.936 ± 2.217  ns/op
StringToBytesBenchmark.getBytesUtf8                    avgt   10  141.218 ± 3.013  ns/op

   */
}
