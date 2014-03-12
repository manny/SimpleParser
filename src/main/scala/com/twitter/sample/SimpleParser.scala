package com.twitter.sample

import org.codehaus.jackson._
import org.codehaus.jackson.JsonToken._


/* case class: plain and immutable data-holding objects that should 
 * depend on their contructor arguments*/
case class SimpleParsed(id: Long, text: String)

class SimpleParser {

  val parserFactory = new JsonFactory()

  def parse(str: String) = {
    val parser = parserFactory.createJsonParser(str)
    var nested = 0
    if (parser.nextToken() == START_OBJECT){
      var token = parser.nextToken()
      var textOpt:Option[String] = None //set up options for Some
      var idOpt:Option[Long] = None     

      while(token !=null) {
        if (token == FIELD_NAME && nested == 0) {
          parser.getCurrentName() match {
            case "text" =>{
              parser.nextToken()
              textOpt = Some(parser.getText()) //set textOpt if not null
            }
            case "id" => {
              parser.nextToken()
              idOpt = Some(parser.getLongValue())
            }
            case _ =>    //or else case
          }
        }else if (token == START_OBJECT) {
          nested +=1
        }else if (token == END_OBJECT){
          nested -= 1
        }
        token = parser.nextToken()
      }
      if (textOpt.isDefined && idOpt.isDefined) {
        Some(SimpleParsed(idOpt.get, textOpt.get))
      }else {
        None
      }
    }
    else {
      None
    }
  }
}
