package tutorial.webapp

object TutorialApp {
  def setupUI(): Unit = {
    println("setup UI called")
  }

  def addClickedMessage(): Unit = {
    println("You clicked the button!")
  }

  def main(args: Array[String]): Unit = {
    setupUI()
  }
}
