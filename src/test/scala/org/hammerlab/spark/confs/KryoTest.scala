package org.hammerlab.spark.confs

import org.apache.spark.serializer.KryoRegistrator
import org.hammerlab.spark.SparkConfBase
import org.hammerlab.test.Suite

class KryoTest
  extends Suite {
  test("override registration requirement") {
    val conf = HasSparkConf.conf
    conf.get("spark.kryo.referenceTracking") should be("true")
    conf.get("spark.kryo.registrationRequired") should be("false")
    conf.get("spark.kryo.registrator") should be("org.hammerlab.spark.confs.TestRegistrator")
  }
}

class TestRegistrator
  extends KryoRegistrator {
  override def registerClasses(kryo: com.esotericsoftware.kryo.Kryo): Unit = ???
}

object HasSparkConf
  extends SparkConfBase
    with Kryo {
  val conf = makeSparkConf
  override def registrationRequired = false
  override def referenceTracking = true
  override def registrar = classOf[TestRegistrator]
}
