package my

import cats.effect.IO
import fs2.{Pipe, Pull, Pure, Stream}
import my.Model.Actor

object Utils:
  def actorsByFirstName(actors: Stream[Pure, Actor]): Stream[Pure, Map[String, List[Actor]]] =
    actors.fold(Map.empty[String, List[Actor]]) { (map, actor) =>
      map + (actor.firstName -> (actor :: map.getOrElse(actor.firstName, Nil)))
    }

  def takeByName(name: String): Pipe[IO, Actor, Actor] =
    def go(s: Stream[IO, Actor], name: String): Pull[IO, Actor, Unit] =
      s.pull.uncons1.flatMap {
        case Some((head, tail)) if head.firstName == name => Pull.output1(head) >> go(tail, name)
        case Some(_, tail) => go(tail, name)
        case None => Pull.done
      }

    in => go(in, name).stream
