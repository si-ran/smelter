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
import com.neo.sk.smelter.front.styles.{ListStyles, LoginStyles}
/**
  * Created by haoshuhan on 2018/6/4.
  */
object Main extends PageSwitcher {
  val currentPage = currentHashVar.map { ls =>
    println(s"currentPage change to ${ls.mkString(",")}")
    ls match {
      case "Login" :: Nil => Login.app
      case "List" :: username :: Nil => new TaskList(username).app

      case _ => Login.app
    }

  }

  def show(): Cancelable = {
    switchPageByHash()
    val page =
      <div>
        {currentPage}
      </div>
    mount(dom.document.body, page)
  }


  def main(args: Array[String]): Unit ={
    import scalacss.ProdDefaults._
    LoginStyles.addToDocument()
    ListStyles.addToDocument()
    show()
  }
}
