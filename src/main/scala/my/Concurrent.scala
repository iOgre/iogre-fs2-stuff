package my

import cats.effect.IO

object Concurrent:

  import my.Lifted.*

  val concurrentAA: fs2.Stream[IO, Model.Actor] = liftedAva.evalMap(actor =>
    IO {
      Thread.sleep(500)
      actor
    }
  )
  val merged: fs2.Stream[IO, Model.Actor] = concurrentJL.merge(concurrentAA)
  private val concurrentJL = liftedJLActors.evalMap(actor =>
    IO {
      Thread.sleep(100)
      actor
    }
  )
