
package com.documentmatrix.joins

import com.documentmatrix.constants.Constants
import org.apache.spark.{HashPartitioner, SparkContext}
import org.apache.spark.SparkContext._

/**
 * Created by Vipin Kumar on 18/4/15.
 */

class SimpleInner(firstFile: String, secondFile: String, firstPos: Int, secPos: Int, columnCount: Int) {

  def remove(num: Int, list: List[Int]) = list diff List(num)

  def joinFile() {
    val sc = new SparkContext("local", this.getClass.getName)
    val file = sc.textFile("hdfs://" + Constants.HADOOP_HOST + ":8020/" + firstFile)

    val reg1 = file.map(_.split(",")).map(

      r =>
        (r(firstPos), r.drop(firstPos).mkString(","))

    )

    val reg2 = file.map(_.split(",")).map(
      c =>
        (c(secPos), c.drop(secPos).mkString(","))

    )

    val kk = reg1.join(reg2, new HashPartitioner(100))

    kk.saveAsTextFile("hdfs://" + Constants.HADOOP_HOST + ":8020/" + secondFile)

  }
}

object SimpleInnerTest {
  def main(args: Array[String]): Unit = {
    val sm = new SimpleInner("tmp/tt3.txt", "tmp/tt14.txt", 1, 1, 3)
    sm.joinFile()
  }
}