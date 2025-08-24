package app.util;

import java.util.List;

import scala.jdk.javaapi.CollectionConverters;

public class Scala2Util
{
  private Scala2Util()
  {
    // なし
  }


  public static <T> scala.collection.immutable.List<T> toScalaList(List<T> list)
  {
    scala.collection.mutable.Buffer<T> scalaBuffer = CollectionConverters.asScala(list);
    scala.collection.immutable.List<T> out = scala.collection.immutable.List$.MODULE$.from(scalaBuffer);
    return out;
  }
  
}
