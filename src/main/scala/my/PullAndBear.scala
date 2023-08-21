package my

import fs2.{Pull, Pure, Stream}
import my.Data.{andrewGarfield, tobeyMaguire, tomHolland}
import my.Initial.avengersActors
import my.Model.Actor

object PullAndBear:
  // val aaPullEcho: Pull[Pure, Actor, Unit] = avengersActors.pull.echo
  lazy val ed1: Stream.ToPull[Pure, Actor] = avengersActors.pull
  lazy val echoed: Pull[Pure, Actor, Unit] = ed1.echo
  lazy val streamed: Stream[Pure, Actor] = echoed.stream
  val tomHollandActorPull: Pull[Pure, Actor, Unit] = Pull.output1(tomHolland)
  val thaps: Stream[Pure, Actor] = tomHollandActorPull.stream
  val spdmActPull: Pull[F, Actor, Unit] = Pull.output1(tomHolland) >> Pull.output1(tobeyMaguire) >> Pull.output1(andrewGarfield)
