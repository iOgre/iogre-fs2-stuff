package my

import cats.effect.IO

object Concurrent:

  import my.Lifted.*

  val concurrentJL = liftedJLActors.evalMap(actor =>
    IO {
      Thread.sleep(100)
      actor
    }
  )

  val concurrentAA = liftedAva.evalMap(actor =>
    IO {
      Thread.sleep(500)
      actor
    }
  )
  val merged = concurrentJL.merge(concurrentAA)
