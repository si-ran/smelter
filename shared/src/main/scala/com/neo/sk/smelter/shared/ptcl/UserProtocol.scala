package com.neo.sk.smelter.shared.ptcl

/**
  * User: sky
  * Date: 2018/6/1
  * Time: 14:50
  */
object UserProtocol {
  case class UserLoginReq(
                         userName:String,
                         pwd:String
                         )
}
