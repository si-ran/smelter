package com.neo.sk.smelter.front.pages

import com.neo.sk.smelter.front.utils.{Http, JsFunc}
import com.neo.sk.smelter.front.{Page, Routes}
import com.neo.sk.smelter.shared.ptcl.SuccessRsp
import org.scalajs.dom
import org.scalajs.dom.html.Input
import com.neo.sk.smelter.shared.ptcl.UserProtocol._
import com.neo.sk.smelter.shared.ptcl.ToDoListProtocol._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser._
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by haoshuhan on 2018/6/4.
  */
object Login extends Page{

  override val pageUrl: String = "#/Login"

  def login() : Unit = {
    val username=dom.window.document.getElementById("username").asInstanceOf[Input].value
    val password=dom.window.document.getElementById("password").asInstanceOf[Input].value
    val data = UserLoginReq(username, password).asJson.noSpaces
    Http.postJsonAndParse[SuccessRsp](Routes.User.login, data).map {
      case Right(rsp) =>
        if(rsp.errCode == 0) {
          JsFunc.alert("登录成功！")
          dom.window.location.hash = s"#/List/$username"
        } else if(rsp.errCode == 100102){
          JsFunc.alert(s"用户名不存在!")
        } else if(rsp.errCode == 100103){
          JsFunc.alert(s"密码不正确！")
        } else {
          JsFunc.alert("登录失败，请稍后再试！")
        }

      case Left(error) =>
        println(s"login error: $error")
        JsFunc.alert("登录失败，请输入正确的水木账号密码！")
    }
  }

   override def render: xml.Elem = {
    <div>
      <div>
        <div>欢迎登录</div>
      </div>
      <div>
        <div>用户名：<input id="username"></input>
        </div>
        <div>
          <pre>密  码：<input id="password"></input>
          </pre>
        </div>
      </div>
      <button onclick={() => login()}>登录</button>
    </div>
  }
}
