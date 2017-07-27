package tutorial.webapp

import utest._

object SharedExampleTest extends TestSuite {
  def tests = TestSuite {
    'SharedTest {
      assert(2 != 1)
    }
  }
}
