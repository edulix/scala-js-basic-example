package tutorial.webapp

import org.scalajs.jquery.jQuery
import org.scalajs.dom
import dom.document
import scala.scalajs.js.annotation.JSExportTopLevel

object TutorialApp {
  def setupUI(): Unit = {
    jQuery("#click-me-button").click(() => addClickedMessage())
    jQuery("body").append("<p>Hello World</p>")
  }

  @JSExportTopLevel("addClickedMessage")
  def addClickedMessage(): Unit = {
    jQuery("body").append("You clicked the button!")
  }

  def main(args: Array[String]): Unit = {
    jQuery(() => setupUI())
  }
}
