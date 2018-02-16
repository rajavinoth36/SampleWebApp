package conversion.cf

import akka.actor.Actor
import akka.event.Logging
import spray.routing.RequestContext

object FtoCActor {
  case class Convert(faren: Double)
}

class FtoCActor(requestContext: RequestContext) extends Actor {
  import FtoCActor._

  implicit val system = context.system
  val log = Logging(system, getClass)

  def receive = {
    case Convert(faren) =>
      Convert(faren)
      context.stop(self)
  }

  def Convert(faren: Double) = {
    log.info("Converting Farenheit: {}", faren)

    val celcius = (faren - 32) * 5/9
    requestContext.complete(celcius.toString)
  }
}