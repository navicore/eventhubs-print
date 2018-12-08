package navicore.akka.eventhubs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.{Done, NotUsed}
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import navicore.akka.eventhubs.models.JsonSupport
import onextent.akka.eventhubs.Connector.AckableOffset
import onextent.akka.eventhubs.EventHubConf
import onextent.akka.eventhubs.Eventhubs._

import scala.concurrent.{ExecutionContextExecutor, Future}

object Main extends LazyLogging with JsonSupport with ErrorSupport {

  def main(args: Array[String]) {

    implicit val system: ActorSystem = ActorSystem("EventhubsPrint-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

//    val route =
//      HealthCheck ~
//      ApiRoute.apply ~
//      ApiSegmentRoute.apply
//
//    Http().bindAndHandle(route, "0.0.0.0", port)

    val consumer: Sink[(String, AckableOffset), Future[Done]] =
      Sink.foreach(m => {
        //println(s"SUPER SOURCE:\n${m._1.substring(0, 160)}")
        println(s"SOURCE:\n${m._1}")
        m._2.ack()
      })

    val toConsumer = createToConsumer(consumer)

    val cfg: Config = ConfigFactory.load().getConfig("eventhubs-1")

    for (pid <- 0 until EventHubConf(cfg).partitions) {

      val src: Source[(String, AckableOffset), NotUsed] =
        createPartitionSource(pid, cfg)

      src.runWith(toConsumer)

    }

  }
}
