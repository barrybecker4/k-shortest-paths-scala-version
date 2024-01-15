import org.scalatest.FunSuite

import scala.collection.mutable

import algorithm.graph._
import algorithm.graph.INode


/**
 * Created by yqi on 5/6/2015.
 */
class GraphSuite extends FunSuite {

  test("Validate the usage of set in Graph") {
    type Path = IPath[Int]

    val vertexSet = Set(1, 2, 3)
    assert(!vertexSet(10))

    val g = new WeightedDirectedGraph[Int]
    val n1 = g.addNode(1)
    n1.setWeight(1.1)
    val n2 = g.addNode(2)
    n2.setWeight(0.2)
    val n3 = g.addNode(3)
    n3.setWeight(0.03)
    g.addEdge(n1, n2, 7)

    val pq = new mutable.PriorityQueue[INode[Int]]()
    pq += n1
    pq += n2
    pq += n3
    println(pq)
    println(pq.dequeue())
    println(pq)

    val p1 = new Path(List(n1, n2))
    p1.setWeight(0.3)
    val p2 = new Path(List(n1, n3, n2))
    p2.setWeight(0.53)
    val ppq = new mutable.PriorityQueue[Path]()
    ppq += p2
    ppq += p1
    println(ppq)

    val shortestPath = new ShortestPath(g)
    println(shortestPath.getShortestPath(n1, n2))
  }

  test("Find the top k shortest paths in a graph") {
    val graph = TopKShortestPaths.importGraph("data/test_6_2")
    val paths: List[IPath[Int]] = TopKShortestPaths.find(graph, 4, 5, 100)

    val expPaths = mutable.ListBuffer[IPath[Int]]()
    expPaths.append(
      new IPath[Int](List(new INode(4), new INode(3), new INode(5))),
      new IPath[Int](List(new INode(4), new INode(1), new INode(5))),
      new IPath[Int](List(new INode(4), new INode(1), new INode(2), new INode(5))),
      new IPath[Int](List(new INode(4), new INode(0), new INode(1), new INode(5))),
      new IPath[Int](List(new INode(4), new INode(1), new INode(3), new INode(5))),
      new IPath[Int](List(new INode(4), new INode(0), new INode(1), new INode(2), new INode(5))),
      new IPath[Int](List(new INode(4), new INode(0), new INode(1), new INode(3), new INode(5)))
    )

    assertResult(expPaths) { paths }
  }

}
