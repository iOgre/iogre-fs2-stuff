package my

import fs2.{Chunk, Pure, Stream}
import my.Data.*
import my.Model.Actor
import my.Model.Command.SPM

object Initial:
  val jlActors: Stream[Pure, Actor] = Stream(
    henryCavil,
    galGodot,
    ezraMiller,
    benFisher,
    rayHardy,
    jasonMomoa
  )

  val avengersActors: Stream[Pure, Actor] = Stream(
    scarlettJohansson,
    robertDowneyJr,
    chrisEvans,
    markRuffalo,
    chrisHemsworth,
    jeremyRenner
  )

  val tomHollandStream: Stream[Pure, Actor] = Stream.emit(tomHolland)

  val spiderMen: Stream[Pure, Actor] = Stream.emits(
    List(
      tomHolland.withCommand(SPM),
      tobeyMaguire.withCommand(SPM),
      andrewGarfield.withCommand(SPM)
    )
  )

  val avengersActorsChunked: Stream[Pure, Actor] = Stream.chunk(
    Chunk.array(
      Array(
        scarlettJohansson,
        robertDowneyJr,
        chrisEvans,
        markRuffalo,
        chrisHemsworth,
        jeremyRenner
      )
    )
  )
