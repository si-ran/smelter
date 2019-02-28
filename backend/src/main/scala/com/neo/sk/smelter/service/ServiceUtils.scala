package com.neo.sk.smelter.service

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ValidationRejection
import com.neo.sk.smelter.common.AppSettings
import com.neo.sk.smelter.utils.SecureUtil.PostEnvelope
import com.neo.sk.smelter.utils.{CirceSupport, SecureUtil}
import com.sun.xml.internal.ws.encoding.soap.DeserializationException
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.Error
import io.circe.Decoder
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * User: Taoz
  * Date: 11/18/2016
  * Time: 7:57 PM
  */
object ServiceUtils {
  private val log = LoggerFactory.getLogger("com.neo.sk.todos2018.service.ServiceUtils")
  case class CommonRsp(errCode: Int = 0, msg: String = "ok")
  final val SignatureError = CommonRsp(1000001, "signature error.")

  final val RequestTimeout = CommonRsp(1000003, "request timestamp is too old.")

  final val AppClientIdError = CommonRsp(1000002, "appClientId error.")

  final val INTERNAL_ERROR = CommonRsp(10001, "Internal error.")

  final val JsonParseError = CommonRsp(10002, "Json parse error.")
}

trait
ServiceUtils extends CirceSupport {

  import ServiceUtils._

  def htmlResponse(html: String): HttpResponse = {
    HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, html))
  }

  def jsonResponse(json: String): HttpResponse = {
    HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, json))
  }

  def dealFutureResult(future: ⇒ Future[server.Route]): server.Route = onComplete(future) {
    case Success(route) =>
      route
    case Failure(x: DeserializationException) ⇒ reject(ValidationRejection(x.getMessage, Some(x)))
    case Failure(e) =>
      e.printStackTrace()
      complete("error")
  }


}
