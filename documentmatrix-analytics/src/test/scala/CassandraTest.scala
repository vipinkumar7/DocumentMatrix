/**
 * Created by Vipin Kumar on 16/4/15.
 */

import com.datastax.spark.connector.toSparkContextFunctions
import org.apache.spark.{SparkConf, SparkContext}

object CassandraTest {

  def main(args: Array[String]) {

    val conf = new SparkConf(true)
      .set("spark.cassandra.connection.host", "127.0.0.1")

    val sc = new SparkContext("spark://hadoopdev01.ev.com:7077", "test", conf)

    val rdd = sc.cassandraTable("musicdb", "album")


    println(rdd.count)

  }
}
