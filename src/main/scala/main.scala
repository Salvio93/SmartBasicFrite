import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext
import scala.io.StdIn


import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

final case class DataResponse(page: Int, dataType: String, frequence: String, values: Seq[Int])
object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val dataResponseFormat: RootJsonFormat[DataResponse] = jsonFormat4(DataResponse.apply)
}

//

object AkkaHttpServer extends App {

  implicit val system: ActorSystem = ActorSystem("akka-http-server")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  import JsonSupport._ 

  def generateData(page: Int, dataType: String, frequence: String): List[Int] = {
    
    frequence match {
      case "yearly" =>
        List(page,2,3,4,5,6,7,8,9,10,11,12)
      case "monthly" =>
        List(page,2,3,4,5,6,7,8,9,10,11,12)
      case "weekly" =>
        List(page,2,3,4,5,6,7,8,9,10,11,12)
      case "dayly" =>
        List(page,2,3,4,5,6,7,8,9,10,11,12)
      case _ =>
        List()
    }


    //getSqlData(page,dataType,frequence)
  }

  def forAllmonth(query_resp_year)-> data : [query_resp_year.filter(janvier).count,query_resp_year.filter(fevrier).count,..]
  def forAllweek(query_resp_month)-> data: [query_resp_month.filter(1-7).count,query_resp_month.filter(8-14).count,..] labels : data.length
  def forAllday(query_resp_week)-> data : [query_resp_month.filter(day=1*week).count,query_resp_month.filter(2*week).count,..query_resp_month.filter(7*week).count] labels : data.length
  def
  
  def forAllmonth2(query_resp_year)-> data : [query_resp_year.filter(janvier).forAlldays(1,janvier.length).diffEndBegin(++)/month.length, query_resp_year.filter(fevrier).forAlldays...]
  def forAllweek2(query_resp_month)-> data : [query_resp_month.filter(1-7).diffEndBegin(++)/7, query_resp_year.filter(8-14)...]
  def forlAllday2(query_resp_week) -> data : [query_resp_month.filter(day=1*week).diffEndBegin(++),query_resp_month.filter(day=2*week).diffEndBegin(++)....query_resp_month.filter(day=7*week).diffEndBegin(++)]
  def 



  def getSqlData(page: Int, dataType: String, frequence: String): List[Int] = {

    if (dataType == "air_quality"){
      val query_air = "SELECT date,temp, humid, particule FROM air_quality WHERE date >= TIMESTAP '"frequence+"';"
      
    }
    
    if (dataType == "num_visits"){
      frequence match {
      case "yearly" =>
        val year = 2024 - page
        val query_year = "SELECT id,begin FROM session AND EXTRACT(YEAR FROM set.begin) = " + year +";"
        val query_year_resp = forAllmonth() // return [?/1/2024.count(),?/2/2024.count(),...?/12/20240.count()]
        

      case "monthly" =>
        val month = page
        val query_month = "SELECT id,begin FROM session AND EXTRACT(YEAR FROM set.begin) = " + year +" AND EXTRACT(MONTH FROM set.begin) = "+month+";"
        val query_month_resp = forAllweek() //return [[1-7].count,[8-14].count,[15-21].count, [22-28].count, [29-31].count] ->  [?,3,?,?,?]
        
      case "weekly" =>
        val week = page
        val query_week = query_month
        val query_week_resp = forAllday() //return [08/11/2024.count(),09/11/2024.count(),..14/11/2024.count()] donc [0,1,0,1,1,0,0]
        
      case "daily" =>
        val day = week*7 + page 
        val query_day = "SELECT id,begin,end,raw_data FROM set WHERE EXTRACT(YEAR FROM set.begin) = "+year+" AND EXTRACT(MONTH FROM set.begin) = "+month+" AND EXTRACT(DAY FROM set.begin) = "+day+";"
        val query_day_rep = //USING RAWDATA temps de seance, temps set1, temps set2, temps pause1, temps pause2, rep set1, rep set2

      }
    }
     if (dataType == "exo_time"){
      frequence match {
      case "yearly" =>
        val year = 2024 - page
        val query_year = "SELECT session.id,session.begin,session.end,set.id,set.begin,set.end FROM set,session WHERE session.id = set.session_id AND EXTRACT(YEAR FROM set.begin) = "+year+";"
        val query_year_resp = forAllmonth2()//[12]

        
      case "monthly" =>

        val month = page//[5]
        val query_month = "SELECT session.id,session.begin,session.end,set.id,set.begin,set.end FROM set,session WHERE session.id = set.session_id AND EXTRACT(YEAR FROM set.begin) = "+year+" AND EXTRACT(month FROM set.begin) ="+month+";" //[5]
        val query_month_resp = forAllweek2() //[1-7],[8-14],..

        
      case "weekly" =>

        val week = page
        val query_week = query_month //[7]
        val query_week_resp = forlAllday2()
        
      case "daily" =>
        val day = week*7 + page// 1:00-0:00 + 4:00-3:00 
        val query_day = "SELECT session.id,session.begin,session.end,set.id,set.begin,set.end,set.rep, set.rawdata, set.poids FROM set,session WHERE session.id = set.session_id AND EXTRACT(YEAR FROM set.begin) = "+year+" AND EXTRACT(month FROM set.begin) = "+month+"  AND EXTRACT(DAY FROM set.begin) = "+day+";" 
        val query_day_resp = label : session.end - session.begin data : rep * set
        //de set1.begin à set1.end =1,2,3,..12 puis 0 pour set2.begin-set1.end puis set2.begin à set2.end = 1,2,3,..10....
        //donnée de poids change parfois


        //graph2 : amplitude pour chaque set grâce à rawdata
        //temps_serie_1 = 15sec, distance [1,2,3,4,5,6,5,4,3,2,1,0,1,2,3,4,5,6,6,6,5,5,4,3,2,1,0,1,2,3,4,5,6,7,7,6,4,3,2,1]
        //LINEGRAPH AMPLITUDE RAW DATA
            
      }
     }
  }
  // Define the route
  val route =
    path("api" / "data") {
      get {
        parameters("page".as[Int], "dataType", "frequence") { (page, dataType, frequence) =>
          val data = generateData(page, dataType, frequence)
          val response = DataResponse(page, dataType, frequence, data) 
          complete(response)
        }
      }
    }

  // Start the server
  val bindingFuture = Http().newServerAt("0.0.0.0", 9090).bind(route)
  println(s"Server online at http://localhost:9090/\nPress RETURN to stop...")
  StdIn.readLine() // Keep the server running until user presses return
  bindingFuture
    .flatMap(_.unbind()) 
    .onComplete(_ => system.terminate()) 
}

/*
        //air_quality
        w = "SELECT date,temp, humid, particule FROM air_quality  WHERE date >= TIMESTAP '"+frequence+"';"
        //num_visits
        x = "SELECT id,begin FROM session WHERE EXTRACT(YEAR FROM session.begin) = " + year +";"
        y =  "SELECT id,begin FROM session WHERE EXTRACT(YEAR FROM session.begin) = " + year +" AND EXTRACT(MONTH FROM set.begin) = "+month+";"
        z = "SELECT id,begin,end,raw_data,poids,rep FROM set WHERE EXTRACT(YEAR FROM set.begin) = "+year+" AND EXTRACT(MONTH FROM set.begin) = "+month+" AND EXTRACT(DAY FROM set.begin) = "+day+";"
        //exo_time
        x2 = "SELECT session.id,session.begin,session.end,set.id,set.begin,set.end FROM set,session WHERE session.id = set.session_id AND EXTRACT(YEAR FROM set.begin) = "+year+";"
        y2=  "SELECT session.id,session.begin,session.end,set.id,set.begin,set.end FROM set,session WHERE session.id = set.session_id AND EXTRACT(YEAR FROM set.begin) = "+year+ " AND EXTRACT(month FROM set.begin) ="+month+";"
        z2 = "SELECT session.id,session.begin,session.end,set.id,set.begin,set.end,set.rep, set.rawdata, set.poids FROM set,session WHERE session.id = set.session_id AND EXTRACT(YEAR FROM set.begin) = "+year+" AND EXTRACT(month FROM set.begin) = "+month+"  AND EXTRACT(DAY FROM set.begin) = "+day+";" 
        //set.rawdata c'est le json contenant les infos recue par le capteur de distance pour 1 set donc [1,2,3,4,5,6,6,5,5,4,3,2,1,0?,1,2,3,4,5,5,5,4,3,3,2,1,0....] ici 2 rep
*/