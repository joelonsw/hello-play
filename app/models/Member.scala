package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{JsValue, Json, Writes}
import slick.driver.MySQLDriver.api._
import slick.jdbc.JdbcProfile

import java.sql.Date
import java.util.Calendar
import javax.inject.{Inject, Singleton}
import scala.concurrent.Await
import scala.concurrent.duration.Duration

case class Member(userSeq: Int, userId: String, password: String,
                  nickname: String, score: Int, level: Int, regdate: Date)

class Members(tag: Tag) extends Table[Member](tag, "MEMBER") {
  def userSeq = column[Int]("userSeq", O.PrimaryKey, O.AutoInc)

  def userId = column[String]("userId")

  def password = column[String]("password")

  def nickname = column[String]("nickname")

  def score = column[Int]("score", O.Default(0))

  def level = column[Int]("level", O.Default(0))

  def regdate = column[Date]("regdate")

  def * = (userSeq, userId, password, nickname, score, level, regdate) <>
    ((Member.apply _).tupled, Member.unapply)
}


@Singleton
class MemberDataAccess @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  // 약간~ 일급 컬렉션 처럼 쓰면 될 듯
  val members = TableQuery[Members]

  implicit val memberWrites = new Writes[Member] {
    def writes(member: Member): JsValue = Json.obj(
      "userSeq" -> member.userSeq,
      "userId" -> member.userId,
      "password" -> member.password,
      "nickname" -> member.nickname,
      "score" -> member.score,
      "level" -> member.level,
      "regdate" -> member.regdate.toString
    )
  }

  def getMembersAsJson() = {
    val members = Await.result(getList, Duration.Inf)
    Json.toJson(members)
  }

  def getList = {
    db.run(members.result)
  }

  def createTable = {
    db.run(members.schema.create)
  }

  def insert = {
    db.run(members +=
      Member(1, "admin", "12345", "Admin", 100, 20, new Date(Calendar.getInstance().getTimeInMillis))
    )
  }

  // UPDATE member SET password = '1111' level = 10 WHERE userId = 'admin'
  def updateWithCondition() = {
    members.filter(_.userId === "admin").map(m => (m.password, m.level)).update(("1111", 10))
  }

  // DELETE member WHERE userId = 'admin'
  def deleteMember(): Unit = {
    members.filter(_.userId === "admin").delete
  }

  // ORDER BY
  def orderBy() = {
    members.sortBy(m => (m.level.asc, m.score)).result
  }
}
