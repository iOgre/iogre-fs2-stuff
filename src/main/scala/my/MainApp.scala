package my

import cats.effect.{ExitCode, IO, IOApp}
import fs2.io.file.{Files, Flags, Path}
import fs2.{text, Stream}
import my.Initial.*
import my.Utils.toConsoleWithThread
object MainApp extends IOApp:
  val parJoinedActors: Stream[IO, Unit] = Stream(
    jlActors.through(toConsoleWithThread).as(()),
    avengersActors.through(toConsoleWithThread).as(()),
    spiderMen.through(toConsoleWithThread).as(())
  ).parJoin(2)

  val fileReadWrite: IO[ExitCode]         = Converter.converter().flatMap(s => s.compile.drain.as(ExitCode.Success))
  private val baseStream: Stream[IO, Int] = Inf.incrementing(1).take(3)

  private val folded: Stream[IO, Int] = baseStream.fold[Int](1)((k, v) => k * v)

  private val infinite: IO[ExitCode] =
    folded
      .through(toConsoleWithThread)
      .compile
      .drain
      .as(ExitCode.Success)
  override def run(args: List[String]): IO[ExitCode] = infinite

object Inf:
  def incrementing(start: Int): Stream[IO, Int] =
    val iterator: Iterator[Int] = Iterator.iterate(1)(_ + 1)
    Stream.fromIterator[IO](iterator, 11)

object Converter:
  private def ftc(f: Double): Double = (f - 32.0) * (5.0 / 9.0)

  def converter(): IO[Stream[IO, String]] =
    Files[IO].currentWorkingDirectory
      .map { path =>
        val readPath: Path  = path / "fahrenheit.txt"
        val writePath: Path = path / "celsius.txt"
        Stream.emit(path).through(toConsoleWithThread) >> Files[IO]
          .readUtf8Lines(readPath)
          .filter(s => !s.startsWith("//") && s.trim.nonEmpty)
          .map(l => ftc(l.toDouble).toString)
          .intersperse("\n")
          .through(toConsoleWithThread[String])
          .through(text.utf8.encode)
          .through(Files[IO].writeAll(writePath, Flags.Append))

      }
