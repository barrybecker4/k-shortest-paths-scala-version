import algorithm.graph._
import org.scalatest.FunSuite


/**
 * find weights for all shortest paths
 */
class AllShortestPathSuite extends FunSuite {

  test("Find shortest path weights in graph 'network' from 0 ") {
    verify("network", 0, IndexedSeq(0.0, 1.0, 1.0, 1.0, 2.0, 2.0, 3.0, 2.0, 3.0, 3.0, 2.0, 3.0, 3.0, 3.0))
  }

  test("Find shortest path weights in graph 'test_5' from 0 ") {
    verify("test_5", 0, IndexedSeq(0.0, 1.0, 2.0, 4.0, 3.0))
  }

  test("Find shortest path weights in graph 'test_6' from 0 ") {
    verify("test_6", 0, IndexedSeq(0.0, 1.0, 2.0, 3.0, 1.0, 1.0))
  }

  test("Find shortest path weights in graph 'test_6_1' from 0 ") {
    verify("test_6_1", 0, IndexedSeq(0.0, 1.0, 2.0, 2.0, Double.PositiveInfinity, 0.0))
  }

  test("Find shortest path weights in graph 'test_6_2' from 0 ") {
    verify("test_6_2", 0, IndexedSeq(0.0, 1.0, 2.0, 2.0, Double.PositiveInfinity, 1.0))
  }

  // this is 0.0, 1.0, 2.0, 4.0, 3.0, 5.0, 2.0
  // Map(0 -> 0.0)
  // Map(2 -> 7.0, 1 -> 1.0, 0 -> 0.0)
  // Map(2 -> 2.0, 4 -> 3.0, 1 -> 1.0, 3 -> 4.0, 6 -> 2.0, 0 -> 0.0)
  // Map(2 -> 2.0, 5 -> 5.0, 4 -> 3.0, 1 -> 1.0, 3 -> 4.0, 6 -> 2.0, 0 -> 0.0)
  // Map(2 -> 2.0, 4 -> 3.0, 1 -> 1.0, 3 -> 4.0, 6 -> 2.0, 0 -> 0.0)
  // Map(2 -> 2.0, 5 -> 5.0, 4 -> 3.0, 1 -> 1.0, 3 -> 4.0, 6 -> 2.0, 0 -> 0.0)
  // Map(2 -> 2.0, 4 -> 3.0, 1 -> 1.0, 3 -> 4.0, 6 -> 2.0, 0 -> 0.0)
  test("Find shortest path weights in graph 'test_7' from 0 ") {
    verify("test_7", 0, IndexedSeq(0.0, 1.0, 2.0, 4.0, 3.0, 5.0, 2.0))
  }

  test("Find shortest path weights in graph 'test_8' from 0 ") {
    verify("test_8", 0, IndexedSeq(0.0, 1.0, 1.0, 2.0, 2.0, 3.0, 1.0, 2.0))
  }

  test("Find shortest path weights in graph 'test_15' from 0 ") {
    verify("test_15", 0, IndexedSeq(0.0, 0.0791812, 0.0791812, 0.2552722, 0.2552722, 0.3802112, 0.3802112, 0.4313632, 0.3344534, 0.4313632, 0.3344534, 0.5563022, 0.6812412000000001, 0.45939240000000003, 0.5563022))
  }

  test("Find shortest path weights in graph 'test_50' from 0 ") {
    verify("test_50", 0, IndexedSeq(0.0, 0.251671, 0.1088628, 0.103707, 0.2611082, 0.1549167, 0.13054159999999998, 0.3396084, 0.1646639, 0.1312432, 0.1304068, 0.17833670000000001, 0.19699129999999998, 0.0973086, 0.1880636, 0.1846159, 0.1346781, 0.27012759999999997, 0.17443979999999998, 0.1411868, 0.3433722, 0.2415908, 0.22394930000000002, 0.0676023, 0.2176196, 0.2673332, 0.155546, 0.2058208, 0.162432, 0.2121197, 0.19642559999999998, 0.1325142, 0.2046595, 0.1782608, 0.2755802, 0.15034129999999998, 0.07898, 0.1601466, 0.2308513, 0.1792576, 0.23834819999999998, 0.3014059, 0.279221, 0.23799, 0.2250776, 0.29329700000000003, 0.256884, 0.1846753, 0.142653, 0.2729564, 0.183267))
  }

  test("Find shortest path weights in graph 'test_50_2' from 0 ") {
    verify("test_50_2", 0, IndexedSeq(0.0, 0.251671, 0.1088628, 0.103707, 0.2611082, 0.1549167, 0.13054159999999998, 0.3396084, 0.1646639, 0.1312432, 0.1304068, 0.17833670000000001, 0.19699129999999998, 0.0973086, 0.1880636, 0.1846159, 0.1346781, 0.27012759999999997, 0.17443979999999998, 0.1411868, 0.3433722, 0.2415908, 0.22394930000000002, 0.0676023, 0.2176196, 0.2673332, 0.155546, 0.2058208, 0.162432, 0.2121197, 0.19642559999999998, 0.1325142, 0.2046595, 0.1782608, 0.2755802, 0.15034129999999998, 0.07898, 0.1601466, 0.2308513, 0.1792576, 0.23834819999999998, 0.3014059, 0.279221, 0.23799, 0.2250776, 0.29329700000000003, 0.256884, 0.1846753, 0.142653, 0.2729564, 0.183267))
  }


  def verify(fileName: String, source: Int, expectedPathWeights: IndexedSeq[Double]): Unit = {
    val graph = TopKShortestPaths.importGraph("data/" + fileName)
    val shortestPathFinder = new ShortestPath[Int](graph)
    val infNode = new INode()
    infNode.setWeight(Double.PositiveInfinity)

    val pathWeights: IndexedSeq[Double] =
      Range(0, graph.size)
        .map(dest => shortestPathFinder.getShortestPath(new INode(source), new INode(dest)).getOrElse(infNode).getWeight)

    assertResult(expectedPathWeights) { pathWeights }
  }

}
