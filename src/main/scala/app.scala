package me.lessis

import xsbti.{ AppMain, AppConfiguration }

import com.jayway.jsonpath._

import java.net.URL

object Jack {
  def main(args: Array[String]) {
    run(args)
  }

  def usage = {
    System.err.println("jack <path> [-s jsonstring|-u url]")
    1
  }

  def run(args: Array[String]): Int = {
    args.toList match {
      case List(path, extras @ _*) =>
        extras.toList match {
          case List("-s", str) =>
            println(read(path, str))
            0
          case List("-u", url) =>
            println(read(path, new URL(url)))
            0
          case _ =>
            usage
        }
      case _ =>
        usage        
    }
  }

  def path(query: String) =
    new JsonPath(query, Array.empty[Filter[_]])

  def read(query: String, in: URL) =
    path(query).read(in).toString

  def read(query: String, in: String) =
    path(query).read(in).toString
}

class Script extends AppMain {
  def run(conf: AppConfiguration) =
    Exit(Jack.run(conf.arguments))
}

case class Exit(val code: Int) extends xsbti.Exit
