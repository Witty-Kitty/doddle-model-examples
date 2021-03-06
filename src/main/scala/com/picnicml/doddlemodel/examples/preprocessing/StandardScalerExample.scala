package com.picnicml.doddlemodel.examples.preprocessing

import breeze.linalg.*
import breeze.stats.{mean, stddev}
import com.picnicml.doddlemodel.data.loadIrisDataset
import com.picnicml.doddlemodel.preprocessing.StandardScaler
import com.picnicml.doddlemodel.syntax.TransformerSyntax._

object StandardScalerExample extends App {
  val (x, _) = loadIrisDataset
  println(s"Mean values before preprocessing: ${mean(x(::, *)).t}")
  println(s"Standard deviations before preprocessing: ${stddev(x(::, *)).t}")

  val scaler = StandardScaler()
  val trainedScaler = scaler.fit(x)
  val xTransformed = trainedScaler.transform(x)
  println(s"Mean values after preprocessing: ${mean(xTransformed(::, *)).t}")
  println(s"Standard deviations after preprocessing: ${stddev(xTransformed(::, *)).t}")
}
