package my

import cats.effect.IO
import my.Model.Actor

import scala.util.Random

object ActorRepository:
  def save(actor: Actor): IO[Int] = IO {
    println(s"saving actor $actor")
    if Random.nextInt() % 2 == 0 then throw new RuntimeException("Something went wrong while saving")
    println("saved")
    actor.id
  }
