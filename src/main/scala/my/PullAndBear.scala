package my

import fs2.{Pull, Pure}
import my.Data.{andrewGarfield, tobeyMaguire, tomHolland}
import my.Initial.avengersActors
import my.Model.Actor

object PullAndBear:
  // val aaPullEcho: Pull[Pure, Actor, Unit] = avengersActors.pull.echo
  lazy val ed1 = avengersActors.pull
  lazy val echoed: Pull[Pure, Actor, Unit] = ed1.echo
  lazy val streamed = echoed.stream
  val tomHollandActorPull: Pull[Pure, Actor, Unit] = Pull.output1(tomHolland)
  val thaps = tomHollandActorPull.stream
  val spdmActPull = Pull.output1(tomHolland) >> Pull.output1(tobeyMaguire) >> Pull.output1(andrewGarfield)
