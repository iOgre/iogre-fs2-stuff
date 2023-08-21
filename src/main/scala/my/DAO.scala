package my

import cats.effect.IO
import fs2.Stream
import my.Initial.jlActors

object DAO:
  private val count = new java.util.concurrent.atomic.AtomicLong(0)
  private val savedJlActors = jlActors.evalMap(ActorRepository.save)
  private val acquire = IO {
    val conn = DatabaseConnection("jlaConnection")
    println(s"Acquiring connection to the database: $conn count: ${count.incrementAndGet()}")

    conn
  }
  private val release = (conn: DatabaseConnection) =>
    IO.println(s"Releasing connection to db: $conn: count: ${count.decrementAndGet()}")
  val errorHandledActors: Stream[IO, AnyVal] =
    savedJlActors.handleErrorWith(err => Stream.eval(IO.println(s"Something occurred: ${err.getMessage}")))
  val managedJlActors: Stream[IO, Int] = Stream.bracket(acquire)(release).flatMap(conn => savedJlActors)


  private case class DatabaseConnection(connection: String) extends AnyVal
