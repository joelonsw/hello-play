package controllers

import play.api.libs.json.Json
import play.api.mvc._
import services.OpenApiService

import javax.inject._

@Singleton
class OpenApiController @Inject()(val controllerComponents: ControllerComponents,
                                  val openApiService: OpenApiService) extends BaseController {

  def getList: Action[AnyContent] = Action {
    val list: List[String] = openApiService.getList
    Ok(Json.toJson(list))
  }
}
