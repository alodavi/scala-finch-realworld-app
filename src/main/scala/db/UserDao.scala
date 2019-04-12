package db

import java.time.Instant

import cats.effect.IO
import doobie._
import doobie.implicits._
import cats._
import cats.effect._
import cats.implicits._
import models.{DbUser, User}
import java.time.OffsetDateTime

trait UserDao extends DbConnection {

  import y._
  import OffsetDateTimeModule._

  def insertUpdateUser(email: String,
                       username: String,
                       bio: Option[String],
                       image: Option[String],
                       createdAt: OffsetDateTime,
                       updatedAt: OffsetDateTime): IO[DbUser] =
    sql"insert into users (email, username, bio, image, created_at, updated_at) values ($email, $username, $bio, $image, $createdAt, $updatedAt)".update.run
      .transact(xa)
      .map { _ =>
        DbUser(email, username, bio, image, createdAt, updatedAt)
      }

}
