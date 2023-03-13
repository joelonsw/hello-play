package models

import java.util.Date
import java.util.Calendar

case class Member(mid: Int, userId: String, password: String,
                  nickname: String, email: String, regdate: Option[Date])

object Member {

  def getList = ???

  val date = Option(Calendar.getInstance.getTime)

  var members = Set(
    Member(1, "admin", "12345", "admin", "admin@google.com", date),
    Member(2, "Joel", "1234", "joel.jo", "joel@jo", date),
    Member(3, "Jay", "5678", "jay.den", "jay@den", date)
  )
}
