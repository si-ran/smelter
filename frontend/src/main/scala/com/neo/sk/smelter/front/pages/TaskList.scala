package com.neo.sk.smelter.front.pages

import com.neo.sk.smelter.front.{Page, Routes}
import com.neo.sk.smelter.front.utils.{Http, JsFunc, TimeTool}
import com.neo.sk.smelter.shared.ptcl.SuccessRsp
import com.neo.sk.smelter.shared.ptcl.ToDoListProtocol.{AddRecordReq, DelRecordReq, GetListRsp}
import mhtml._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser._
import org.scalajs.dom
import org.scalajs.dom.html.Input

import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by haoshuhan on 2018/6/4.
  */
class TaskList(username: String) extends Page{

  override val pageUrl: String = "#/List"

  val taskList = Var(List.empty[(String, Long)])
  var inputValue = ""

  def getDeleteButton(record: String, time: Long) =  <button onclick={()=>deleteRecord(record, time)}>删除</button>

  def addRecord: Unit = {
    val data = AddRecordReq(inputValue).asJson.noSpaces
    Http.postJsonAndParse[SuccessRsp](Routes.List.addRecord, data).map {
      case Right(rsp) =>
        if(rsp.errCode == 0) {
          JsFunc.alert("添加成功！")
          getList
        } else {
          JsFunc.alert("添加失败！")
          println(rsp.msg)
        }

      case Left(error) =>
        println(s"parse error,$error")
    }
  }

  def deleteRecord(record: String, time: Long): Unit = {
    val data = DelRecordReq(record, time).asJson.noSpaces
    Http.postJsonAndParse[SuccessRsp](Routes.List.delRecord, data).map {
      case Right(rsp) =>
        if(rsp.errCode == 0) {
          JsFunc.alert("删除成功！")
          getList
        } else {
          JsFunc.alert("删除失败！")
          println(rsp.msg)
        }

      case Left(error) =>
        println(s"parse error,$error")
    }
  }

  def getList: Unit = {
    Http.getAndParse[GetListRsp](Routes.List.getList).map {
      case Right(rsp) =>
        if(rsp.errCode == 0){
          taskList := rsp.list.get
        } else {
          JsFunc.alert(rsp.msg)
          dom.window.location.hash = s"#/Login"
          println(rsp.msg)
        }
      case Left(error) =>
        println(s"get task list error,$error")
    }
  }

  val taskListRx = taskList.map {
    case Nil => <div style ="margin: 30px; font-size: 17px;">暂无任务记录</div>
    case list => <div style ="margin: 20px; font-size: 17px;">
      <table>
        <tr>
          <th>任务</th>
          <th>创建时间</th>
          <th>操作</th>
        </tr>
        {list.map {l =>
        <tr>
          <td>{l._1}</td>
          <td>{TimeTool.dateFormatDefault(l._2)}</td>
          <td>{getDeleteButton(l._1, l._2)}</td>
        </tr>
      }
        }

      </table>

    </div>
  }

  def logout(): Unit = {
    Http.getAndParse[SuccessRsp](Routes.User.logout).map {
      case Right(rsp) =>
        if(rsp.errCode == 0) {
          JsFunc.alert("退出成功")
          dom.window.location.hash = s"#/Login"
        } else {
          JsFunc.alert("退出失败")
          println(s"logout error, ${rsp.msg}")
        }

      case Left(e) =>
        println(s"parse error,$e ")
    }
  }

  override def render: xml.Elem = {
   getList
  <div>
    <div>
      <button onclick={()=>logout()}>退出</button></div>
    <div style="margin:30px;font-size:25px;">任务记录</div>
    <div style="margin-left:30px;">
      <input onchange={(e: dom.Event) => inputValue = e.target.asInstanceOf[Input].value}></input>
    <button onclick={()=>addRecord}>+添加</button>
    </div>
    {taskListRx}
  </div>
  }

}
