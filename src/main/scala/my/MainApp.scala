package my

import cats.effect.{ExitCode, IO, IOApp}
import fs2.{Pipe, Stream}
import my.Initial.*
import my.Model.Actor

object MainApp extends IOApp:
  val parJoinedActors: Stream[IO, Unit] = Stream(
    jlActors.through(toConsoleWithThread),
    avengersActors.through(toConsoleWithThread),
    spiderMen.through(toConsoleWithThread)
  ).parJoin(2)

  override def run(args: List[String]): IO[ExitCode] =
    parJoinedActors.compile.drain
      .as(ExitCode.Success)

  private def toConsoleWithThread: Pipe[IO, Actor, Unit] = in =>
    in.evalMap(actor => IO.println(s"[${Thread.currentThread().getName}] consumed $actor"))
