package com.neo.sk.smelter.models.dao

import com.neo.sk.smelter.utils.DBUtil.db
import slick.jdbc.PostgresProfile.api._
import com.neo.sk.smelter.models.SlickTables._
import scala.concurrent.ExecutionContext.Implicits.global._
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import com.neo.sk.smelter.common.AppSettings
/**
  * User: sky
  * Date: 2018/6/1
  * Time: 15:17
  */
object UserDAO {
  def getUserByName(name:String)={
    Future.successful(AppSettings.userMap.get(name))
  }
}
