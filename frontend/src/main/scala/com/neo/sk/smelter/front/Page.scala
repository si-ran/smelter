package com.neo.sk.smelter.front

import scala.xml.Elem

/**
  * Created by haoshuhan on 2018/6/4.
  */
trait Page {
  def render: Elem

  val pageName: String = this.getClass.getSimpleName

  val pageUrl: String

}
