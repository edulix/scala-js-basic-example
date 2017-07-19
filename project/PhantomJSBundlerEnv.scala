import java.io.File

import org.scalajs.jsenv._
import org.scalajs.core.tools.io._
import org.scalajs.jsenv.phantomjs.PhantomJSEnv
import org.scalajs.core.tools.jsdep.ResolvedJSDependency
import org.scalajs.core.tools.io.VirtualJSFile

class PhantomJSBundlerEnv(
  jettyClassLoader: ClassLoader,
  phantomjsPath: String = "phantomjs", 
  addArgs: Seq[String] = Seq.empty,
  addEnv: Map[String, String] = Map.empty,
  override val autoExit: Boolean = true
)
extends PhantomJSEnv(
  phantomjsPath, addArgs, addEnv, autoExit,  jettyClassLoader
) 
{
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
  
  override def jsRunner(libs: Seq[ResolvedJSDependency], code: VirtualJSFile): JSRunner = 
    super.jsRunner(bundledLibs(libs), code)

  override def asyncRunner(libs: Seq[ResolvedJSDependency], code: VirtualJSFile): 
    AsyncJSRunner = super.asyncRunner(bundledLibs(libs), code)

  override def comRunner(libs: Seq[ResolvedJSDependency], code: VirtualJSFile): 
    ComJSRunner = super.comRunner(bundledLibs(libs), code)
} 
