package conversion.cf

import akka.actor.{Actor, ActorRefFactory, Props}
import spray.http.{HttpResponse, StatusCodes}
import spray.routing.HttpService

object ConvertActor {

}

class ConvertActor extends Actor with HttpService {

  val route =
    get {
      path("ctof" / DoubleNumber) { (celcius) =>
        context =>
          val ctofService = actorRefFactory.actorOf(Props(new CtoFActor(context)))
          ctofService ! CtoFActor.Convert(celcius)
      } ~
        path("ftoc" / DoubleNumber) { (farenheit) =>
          context =>
            val ftocService = actorRefFactory.actorOf(Props(new FtoCActor(context)))
            ftocService ! FtoCActor.Convert(farenheit)
        }
    } ~
      complete(HttpResponse(StatusCodes.BadRequest, "Invalid Params"))

  def receive: Receive = runRoute(route)

  def actorRefFactory: ActorRefFactory = context
}