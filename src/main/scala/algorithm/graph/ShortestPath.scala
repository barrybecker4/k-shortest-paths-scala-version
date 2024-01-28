package algorithm.graph

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer


/**
 * An implementation of Dijkstra Shortest Path Algorithm
 * However it is changed to support an efficient implementation of
 * the top-k shortest paths algorithm
 *
 * @param graph a weighted directed graph
 */
class ShortestPath[T](graph: WeightedDirectedGraph[T]) {

  type Node = INode[T]
  type Path = IPath[T]

  private val determinedVertexSet = collection.mutable.Set[Node]()
  private val vertexCandidateQueue = collection.mutable.PriorityQueue[Node]()
  val startVertexDistanceIndex: mutable.Map[Node, Double] = collection.mutable.Map[Node, Double]()
  val predecessorIndex: mutable.Map[Node, Node] = collection.mutable.Map[Node, Node]()

  def clear(): Unit = {
    determinedVertexSet.clear()
    vertexCandidateQueue.clear()
    startVertexDistanceIndex.clear()
    predecessorIndex.clear()
  }

  def getShortestPath(source: Node, sink: Node): Option[Path] = {

    val isOppositeDirection = false
    determineShortestPaths(source, sink, isOppositeDirection)

    if (startVertexDistanceIndex.contains(sink)) {
      val path = new Path(getPath(source, sink).toList)
      path.setWeight(startVertexDistanceIndex(sink))
      Option(path)
    } else None
  }

  private def getPath(so: Node, si: Node): ListBuffer[Node] = {
    if (so == si) ListBuffer[Node](so) else {
      predecessorIndex.get(si) match {
        case Some(pre) => val rel = getPath(so, pre); rel.append(si); rel
        case None => throw new RuntimeException // should not happen!
      }
    }
  }


  @tailrec
  private def getReversePath(sink: Node, list: ListBuffer[Node]): ListBuffer[Node] = {
    list.append(sink)
    predecessorIndex.get(sink) match {
      case Some(pre) => getReversePath(pre, list)
      case None => list
    }
  }

  @tailrec
  private def updateVertex (end: Node, isOpposite: Boolean): Unit = {
    if (vertexCandidateQueue.isEmpty) return
    val node = vertexCandidateQueue.dequeue()
    if (node != end) {
      determinedVertexSet += node
      val neighborSet = if (isOpposite) graph.fanIn(node) else graph.fanOut(node)

      //println("neighborSet=" + neighborSet + " opp=" + isOpposite)
      neighborSet.diff(determinedVertexSet).foreach(next => {
        val edgeWeight = if (isOpposite) graph.edgeWeight(next, node) else graph.edgeWeight(node, next)
        val curDistance = startVertexDistanceIndex.getOrElse(node, Double.MaxValue - edgeWeight)
        val distance = curDistance + edgeWeight
        //println("updateVertex next=" + next + " startVertexToDistance=" + startVertexDistanceIndex)
        if (!startVertexDistanceIndex.contains(next) || startVertexDistanceIndex(next) > distance) {
          //println("setting: " + next + " d=" + distance + " updateVertex")
          startVertexDistanceIndex.put(next, distance)
          predecessorIndex.put(next, node)
          next.setWeight(distance)
          vertexCandidateQueue += next
        }
      })
      updateVertex(end, isOpposite)
    }
  }

  private def determineShortestPaths (s: Node, e: Node, isOpposite: Boolean): Unit = {
    clear()
    val end = if (isOpposite) s else e
    val start = if (isOpposite) e else s
    startVertexDistanceIndex.put(start, 0d)
    start.setWeight(0d)
    vertexCandidateQueue += start
    updateVertex(end, isOpposite)
  }

  /**
   * Construct a flower rooted at "root" with
   * the shortest paths from the other vertices.
   *
   * @param root the node as the root
   */
  def findShortestPathFlowerRootAt(root: Node): Unit = {
    val isOppositeDirection = true
    determineShortestPaths(null, root, isOppositeDirection)
  }

  /**
   * Correct costs of successors of the input vertex using backward star form.
   * (FLOWER)
   * @param node the input node to start with
   */
  def correctCostBackward(node: Node): Unit = {
    graph.fanIn(node).foreach(pre => {
      val newWeight = graph.edgeWeight(pre, node) + startVertexDistanceIndex(node)
      val oldWeight = startVertexDistanceIndex.getOrElse(pre, Double.MaxValue)
      if (oldWeight > newWeight) {
        //println("setting: " + pre + " d=" + newWeight + " ccb")
        startVertexDistanceIndex.put(pre, newWeight)
        predecessorIndex.put(pre, node)
        correctCostBackward(pre)
      }
    })
  }

  def correctCostForward(node: Node): Double = {
    var cost = Double.MaxValue
    graph.fanOut(node).filter(startVertexDistanceIndex.contains).foreach(next => {
      val newWeight = graph.edgeWeight(node, next) + startVertexDistanceIndex(next)
      if (startVertexDistanceIndex.getOrElse(node, Double.MaxValue) > newWeight) {
        startVertexDistanceIndex.put(node, newWeight)
        predecessorIndex.put(node, next)
        cost = newWeight
      }
    })
    cost
  }

  def getSubShortestPath(source: Node): Option[Path] = {
    correctCostForward(source) match {
      case Double.MaxValue => None
      case cost =>
        val path = new Path(getReversePath(source, ListBuffer()).toList)
        path.setWeight(cost)
        Option(path)
    }
  }

}
