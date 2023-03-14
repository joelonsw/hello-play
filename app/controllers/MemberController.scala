package controllers

import models.MemberDataAccess
import play.api.libs.json.JsValue
import play.api.mvc._
import services.MemberService

import javax.inject._

@Singleton
class MemberController @Inject()(val controllerComponents: ControllerComponents,
                                 val memberService: MemberService,
                                 val memberDataAccess: MemberDataAccess) extends BaseController {

  def getList() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.list(memberService.getList))
  }

  def createMemberTable() = Action {
    memberDataAccess.createTable
    Ok(views.html.index("Created"))
  }

  def insertMember() = Action {
    memberDataAccess.insert
    Ok(views.html.index("Inserted"))
  }

  def getMemberAsJson() = Action {
    val members: JsValue = memberDataAccess.getMembersAsJson()
    Ok(members)
  }
}
