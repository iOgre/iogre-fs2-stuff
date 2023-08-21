package my

import cats.effect.IO

object Lifted:

  import my.Initial.*

  val liftedJLActors = jlActors.covary[IO]
  val liftedAva = avengersActors.covary[IO]
