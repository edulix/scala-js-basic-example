package tutorial.webapp

import utest._

object SharedExampleTest extends TestSuite {
  // Initialize App
  TutorialApp.setupUI()

  def tests = TestSuite {
    'SharedTest {
      TutorialApp.addClickedMessage()
    }
  }
}
