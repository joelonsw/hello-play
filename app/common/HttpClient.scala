package common

import play.api.libs.json._
import play.api.libs.ws.WSClient

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait HttpClient {

  val ws: WSClient

  def get[T](url: String)(implicit rds: Reads[T]): Future[Option[T]] = {
    ws.url(url)
      .get()
      .map { response =>
        if (response.status == 200) {
          response.json.validate[T].asOpt
        } else {
          None
        }
      }
  }

  def post[T, R](url: String, body: T)(implicit wrt: Writes[T], rds: Reads[R]): Future[Option[R]] = {
    ws.url(url)
      .post(Json.toJson(body))
      .map { response =>
        if (response.status == 201) {
          response.json.validate[R].asOpt
        } else {
          None
        }
      }
  }
}

@Singleton
class GlobalHttpClient @Inject()(client: WSClient) extends HttpClient {
  val ws = client
}