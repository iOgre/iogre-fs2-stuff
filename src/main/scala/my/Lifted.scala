package my

import cats.effect.IO

object Lifted:

  import my.Initial.*

  val liftedJLActors: fs2.Stream[IO, Model.Actor] = jlActors.covary[IO]
  val liftedAva: fs2.Stream[IO, Model.Actor] = avengersActors.covary[IO]
