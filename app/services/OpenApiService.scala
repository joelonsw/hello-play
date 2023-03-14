package services

import common.GlobalHttpClient

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

@Singleton
class OpenApiService @Inject()(httpClient: GlobalHttpClient) {

  private val URL: String = "https://easy-deploy.kr/all-pages"

  def getList: List[String] = Await.result(getListWithConnection, Duration.Inf)

  private def getListWithConnection: Future[List[String]] = {
    httpClient.get[List[String]](URL).map {
      case Some(list) => list
      case None => throw new IllegalAccessError()
    }
  }
}
