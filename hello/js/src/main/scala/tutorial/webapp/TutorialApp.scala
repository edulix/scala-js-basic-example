package tutorial.webapp

import org.scalajs.dom
import dom.document
import scala.scalajs.js.annotation.JSExportTopLevel

object TutorialApp {

  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }

  @JSExportTopLevel("addClickedMessage")
  def addClickedMessage(): Unit = {
    appendPar(document.body, "You clicked the button!")
  }

  def main(args: Array[String]): Unit = {
    println("Hello World!")
    appendPar(document.body, "Hello World")
  }
}
