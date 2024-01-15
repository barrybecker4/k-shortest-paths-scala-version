import algorithm.graph._
import org.scalatest.FunSuite

import scala.collection.mutable


/**
 * Created by yqi on 5/6/2015.
 */
class ShortestPathSuite extends FunSuite {

  test("Find the shortest path in a graph 'network' from 0 -> 7") {
    val expPath = new IPath[Int](List(new INode(0), new INode(1), new INode(7)))
    expPath.setWeight(2.0)
    verify("network", 0, 7, Some(expPath))
  }

  test("Find the shortest path in a graph 'network' from 4 -> 6") {
    val expPath = new IPath[Int](List(new INode(4), new INode(6)))
    expPath.setWeight(1.0)
    verify("network", 4, 6, Some(expPath))
  }

  test("Find the shortest path in a graph 'test_5' from 0 -> 2") {
    val expPath = new IPath[Int](List(new INode(0), new INode(1), new INode(2)))
    expPath.setWeight(2.0)
    verify("test_5", 0, 2, Some(expPath))
  }

  test("Find the shortest path in a graph 'test_6' from 0 -> 4") {
    val expPath = new IPath[Int](List(new INode(0), new INode(4)))
    expPath.setWeight(1.0)
    verify("test_6", 0, 4, Some(expPath))
  }

  test("Find the shortest path in a graph 'test_6_1' from 0 -> 3") {
    val expPath = new IPath[Int](List(new INode(0), new INode(1), new INode(3)))
    expPath.setWeight(2.0)
    verify("test_6_1", 0, 3, Some(expPath))
  }
  test("Find the shortest path in a graph 'test_6_1' from 0 -> 4 which is None") {
    verify("test_6_1", 0, 4, None)
  }

  test("Find the shortest path in a graph 'test_6_2' from 0 -> 5") {
    val expPath = new IPath[Int](List(new INode(0), new INode(1), new INode(5)))
    expPath.setWeight(3.0)
    verify("test_6_2", 0, 5, Some(expPath))
  }

  test("Find the shortest path in a graph 'test_7' from 0 -> 5") {
    val expPath = new IPath[Int](List(new INode(0), new INode(1), new INode(4), new INode(5)))
    expPath.setWeight(5.0)
    verify("test_7", 0, 5, Some(expPath))
  }

  test("Find the shortest path in a graph 'test_8' from 0 -> 5") {
    val expPath = new IPath[Int](List(new INode(0), new INode(6), new INode(7), new INode(5)))
    expPath.setWeight(3.0)
    verify("test_8", 0, 5, Some(expPath))
  }

  test("Find the shortest path in a graph 'test_50' from 0 -> 5") {
    val expPath = new IPath[Int](List(new INode(0), new INode(13), new INode(5)))
    expPath.setWeight(0.1549167)
    verify("test_50", 0, 5, Some(expPath))
  }

  test("Find the shortest path in a graph 'test_50_2' from 0 -> 7") {
    val expPath = new IPath[Int](List(new INode(0), new INode(23), new INode(10), new INode(35), new INode(39), new INode(25), new INode(7)))
    expPath.setWeight(0.3396084)
    verify("test_50_2", 0, 7, Some(expPath))
  }

  def verify(fileName: String, source: Int, dest: Int, expectedPath: Option[IPath[Int]]): Unit = {
    val graph = TopKShortestPaths.importGraph("data/" + fileName)
    val shortestPathFinder = new ShortestPath[Int](graph)
    val path: Option[IPath[Int]] = shortestPathFinder.getShortestPath(new INode(source), new INode(dest))
    assertResult(expectedPath) { path }
  }

}
