package services

import anorm.SqlParser._
import anorm._
import play.api.db.DBApi

import java.util.Date
import javax.inject.{Inject, Singleton}
import scala.language.postfixOps

@Singleton
class MemberService @Inject()(dbapi: DBApi) {
  val db = dbapi.database("default")

  val basicMember = {
    get[Int]("members.mid") ~
      get[String]("members.userId") ~
      get[String]("members.password") ~
      get[String]("members.nickname") ~
      get[String]("members.email") ~
      get[Option[Date]]("members.regdate") map {
      case mid ~ userId ~ password ~ nickname ~ email ~ regdate =>
        Member(mid, userId, password, nickname, email, regdate)
    }
  }

  def getList: List[Member] = db.withConnection { implicit connection =>
    SQL("SELECT * FROM members").as(basicMember *)
  }
}

case class Member(mid: Int, userId: String, password: String,
                  nickname: String, email: String, regdate: Option[Date])
