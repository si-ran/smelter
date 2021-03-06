package com.neo.sk.smelter.ptcl

import com.neo.sk.smelter.shared.ptcl.ErrorRsp

/**
  * User: sky
  * Date: 2018/4/9
  * Time: 10:46
  */
object Protocols {
  trait Response
  case class CommonRsp(errCode: Int = 0, msg: String = "ok") extends Response

  //解析错误
  val parseError=ErrorRsp(100101,"parse error")
}
