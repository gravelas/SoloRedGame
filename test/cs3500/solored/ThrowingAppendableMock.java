package cs3500.solored;

import java.io.IOException;

/**
 * Mock of appendable that always throws IOExceptions for tests.
 */
public class ThrowingAppendableMock implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    return null;
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
