package com.neo.sk.smelter.front

import cats.Show
import com.neo.sk.smelter.front.pages.{Login, TaskList}
import mhtml.mount
import org.scalajs.dom
import com.neo.sk.smelter.front.utils.{Http, JsFunc, PageSwitcher}
import mhtml._
import org.scalajs.dom
import io.circe.syntax._
import io.circe.generic.auto._

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.xml.{Elem, Node}
/**
  * Created by haoshuhan on 2018/6/4.
  */
@JSExportTopLevel("front.Main")
object Main extends PageSwitcher {

  val currentPage: Rx[Elem] = currentHashVar.map {
    case "Login" :: Nil => Login.render
    case "List" :: username :: Nil => new TaskList(username).render
    case _ => Login.render
  }

  def show(): Cancelable = {
    switchPageByHash()
    val page =
      <div>
        {currentPage}
      </div>
    mount(dom.document.body, page)
  }

  @JSExport
  def run(): Unit ={
    show()
  }
}
