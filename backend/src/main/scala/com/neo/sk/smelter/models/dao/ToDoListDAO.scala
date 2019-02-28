package com.neo.sk.smelter.models.dao

import com.neo.sk.smelter.utils.DBUtil.db
import slick.jdbc.PostgresProfile.api._
import com.neo.sk.smelter.models.SlickTables._

import scala.concurrent.ExecutionContext.Implicits.global._
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import com.neo.sk.smelter.common.AppSettings
import org.slf4j.LoggerFactory

import scala.collection.mutable
/**
  * User: sky
  * Date: 2018/6/1
  * Time: 15:17
  */
object ToDoListDAO {
  private val log = LoggerFactory.getLogger(this.getClass)
  private val list:mutable.HashSet[(String,String,Long)]=mutable.HashSet() //(name,record,time)

  def addRecord(userName:String,record:String)={
    try {
      list.add(userName,record,System.currentTimeMillis())
      Future.successful(1)
    } catch {
      case  e: Throwable =>
        log.error(s"add record error with error $e")
        Future.successful(-1)
    }
  }

  def delRecord(userName:String,record:String,time:Long)={
    try {
      list.remove(userName,record,time)
      Future.successful(1)
    } catch {
      case  e: Throwable =>
        log.error(s"del record error with error $e")
        Future.successful(-1)
    }
  }

  def getRecordList(userName:String)={
    try{
      Future.successful(list.filter(_._1==userName).map(r=>(r._2,r._3)).toList.sortBy(_._2))
    } catch {
      case e: Throwable=>
        log.error(s"get recordList error with error $e")
        Future.successful(Nil)
    }
  }

}
