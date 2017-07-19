import java.io.File

import scala.reflect.{ClassTag,classTag}

import org.scalajs.jsenv.selenium._
import org.scalajs.core.tools.io.{VirtualJSFile, FileVirtualJSFile}
import org.scalajs.core.tools.jsdep.ResolvedJSDependency
import org.scalajs.jsenv.{AsyncJSEnv, ComJSEnv}
import org.scalajs.jsenv.{JSRunner, AsyncJSRunner, ComJSRunner}

class SeleniumJSBundlerEnv private (browser: SeleniumBrowser, 
keepAlive: Boolean, materializer: FileMaterializer)
extends AsyncJSEnv with ComJSEnv 
{

  def this(browser: SeleniumBrowser) =
    this(browser, keepAlive = false, materializer = DefaultFileMaterializer)

  def withMaterializer(materializer: FileMaterializer): SeleniumJSBundlerEnv =
    new SeleniumJSBundlerEnv(browser, keepAlive, materializer)

  def withKeepAlive(): SeleniumJSBundlerEnv =
    new SeleniumJSBundlerEnv(browser, keepAlive = true, materializer)

  def browserName: String = browser.name

  def name: String = "SeleniumJSBundlerEnv for " + browserName
  
  private def bundledLibs(libs: Seq[ResolvedJSDependency]): 
  Seq[ResolvedJSDependency] = libs.map(
    (x: ResolvedJSDependency) => 
    org.scalajs.core.tools.jsdep.ResolvedJSDependency.minimal(
      FileVirtualJSFile(
        new File(
          x.lib.path.replace("fastopt.js", "fastopt-bundle.js")
        )
      )
    )
  )
  
  def jsRunner(libs: Seq[ResolvedJSDependency], code: VirtualJSFile): JSRunner = 
    new SeleniumRunner(browser, bundledLibs(libs), code, keepAlive, 
      materializer)

  def asyncRunner(libs: Seq[ResolvedJSDependency], code: VirtualJSFile): 
    AsyncJSRunner = new SeleniumAsyncJSRunner(browser, bundledLibs(libs), code, 
      keepAlive, materializer)

  def comRunner(libs: Seq[ResolvedJSDependency], code: VirtualJSFile): 
    ComJSRunner = new SeleniumComJSRunner(browser, bundledLibs(libs), code, 
      keepAlive, materializer)
}
