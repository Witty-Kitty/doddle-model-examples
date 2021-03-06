package com.picnicml.doddlemodel.examples.modelselection

import breeze.stats.distributions.Gamma
import com.picnicml.doddlemodel.data.{loadBreastCancerDataset, splitDataset}
import com.picnicml.doddlemodel.linear.LogisticRegression
import com.picnicml.doddlemodel.metrics.accuracy
import com.picnicml.doddlemodel.modelselection.{CrossValidation, HyperparameterSearch}
import com.picnicml.doddlemodel.syntax.ClassifierSyntax._

import scala.util.Random

object RandomSearchExample extends App {
  val (x, y) = loadBreastCancerDataset
  println(s"number of examples: ${x.rows}, number of features: ${x.cols}")

  val (xTr, yTr, xTe, yTe) = splitDataset(x, y)
  println(s"training set size: ${xTr.rows}, test set size: ${xTe.rows}")

  val numSearchIterations = 100
  implicit val cv: CrossValidation = CrossValidation(metric = accuracy, folds = 5)
  val search = HyperparameterSearch(numSearchIterations)

  implicit val rand: Random = new Random(42)
  val gamma = Gamma(shape = 2, scale = 2)
  val bestModel = search.bestOf(xTr, yTr) {
    LogisticRegression(lambda = gamma.draw())
  }

  val score = accuracy(yTe, bestModel.predict(xTe))
  println(f"test accuracy: $score%1.4f")
}
