package conversion.cf

import akka.actor.Actor
import akka.event.Logging
import spray.routing.RequestContext

object CtoFActor {
  case class Convert(celcius: Double)
}

class CtoFActor(requestContext: RequestContext) extends Actor {
  import CtoFActor._

  implicit val system = context.system
  val log = Logging(system, getClass)

  def receive = {
    case Convert(celcius) =>
      Convert(celcius)
      context.stop(self)
  }

  def Convert(celcius: Double) = {
    log.info("Converting Celcius: {}", celcius)

    val faren = celcius * (9/5) + 32
    requestContext.complete(faren.toString)
  }
}