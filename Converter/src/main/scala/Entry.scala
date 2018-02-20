package conversion.cf

import akka.actor._
import akka.event.Logging
import akka.io.IO
import spray.can.Http

object Entry extends App {

  implicit val actorSystem = ActorSystem("Converter-System")
  val log = Logging(actorSystem, getClass)

  val service = actorSystem.actorOf(Props[ConvertActor], "convert-service")

  IO(Http) ! Http.Bind(service, "0.0.0.0", 8085)
}