package my

import cats.effect.IO
import cats.effect.std.Queue
import fs2.Stream
import my.Lifted.liftedJLActors
import my.Model.Actor

import scala.concurrent.duration.*

object ConsumerProducer:
  private val queue: IO[Queue[IO, Actor]] = Queue.bounded[IO, Actor](10)
  val concurrentlyStreams: Stream[IO, Unit] = Stream.eval(queue).flatMap { q =>
    val producer: Stream[IO, Unit] =
      liftedJLActors
        .evalTap(actor => IO.println(s"[${Thread.currentThread().getName}] produced $actor"))
        .evalMap(q.offer)
        .metered(2.seconds)

    val consumer: Stream[IO, Unit] =
      Stream
        .fromQueueUnterminated(q)
        .evalMap(actor => IO.println(s"[${Thread.currentThread().getName}] consumed $actor"))

    producer.concurrently(consumer)

  }

