import algorithm.graph._
import org.scalatest.FunSuite


/**
 * Created by yqi on 5/6/2015.
 */
class TopKShortestPathsSuite extends FunSuite {

  type Path = IPath[Int]

  test("Find the shortest paths in a graph 'network' from 0 -> 7") {

    val path1 = new IPath[Int](List(new INode(0), new INode(1), new INode(7)))
    path1.setWeight(2.0)
    val path2 = new IPath[Int](List(new INode(0), new INode(2), new INode(1), new INode(7)))
    path2.setWeight(3.0)
    val expPaths = List(path1, path2)
    verify("network", 0, 7, 2, expPaths)
  }

  test("Find the shortest paths in a graph 'network' from 4 -> 6") {
    val path1 = new IPath[Int](List(new INode(4), new INode(6)))
    path1.setWeight(1.0)
    val path2 = new IPath[Int](List(new INode(4), new INode(5), new INode(2),new INode(1), new INode(7), new INode(6)))
    path2.setWeight(5.0)
    val expPaths = List(path1, path2)
    verify("network", 4, 6, 2, expPaths)
  }

  test("Find the shortest paths in a graph 'test_5' from 0 -> 2") {
    val path1 = new IPath[Int](List(new INode(0), new INode(1), new INode(2)))
    path1.setWeight(2.0)
    val path2 = new IPath[Int](List(new INode(0), new INode(2)))
    path2.setWeight(7.0)
    val expPaths = List(path1, path2)
    verify("test_5", 0, 2, 2, expPaths)
  }

  test("Find the shortest paths in a graph 'test_6' from 0 -> 4") {
    val path1 = new IPath[Int](List(new INode(0), new INode(4)))
    path1.setWeight(1.0)
    val path2 = new IPath[Int](List(new INode(0), new INode(1), new INode(2), new INode(4)))
    path2.setWeight(1.0)
    val expPaths = List(path1, path2)
    verify("test_6", 0, 4, 2, expPaths)
  }

  test("Find the shortest paths in a graph 'test_6_1' from 0 -> 3") {
    val path1 = new IPath[Int](List(new INode(0), new INode(1), new INode(3)))
    path1.setWeight(2.0)
    val expPaths = List(path1)
    verify("test_6_1", 0, 3, 2, expPaths)
  }
  test("Find the shortest paths in a graph 'test_6_1' from 0 -> 4 which is None") {
    verify("test_6_1", 0, 4, 2, List())
  }

  test("Find the shortest paths in a graph 'test_6_2' from 0 -> 5") {
    val path1 = new IPath[Int](List(new INode(0), new INode(1), new INode(5)))
    path1.setWeight(1.0)
    val path2 = new IPath[Int](List(new INode(0), new INode(1), new INode(2), new INode(5)))
    path2.setWeight(2.0)
    val expPaths = List(path1, path2)
    verify("test_6_2", 0, 5, 2, expPaths)
  }

  test("Find the shortest paths in a graph 'test_7' from 0 -> 5") {
    val path1 = new IPath[Int](List(new INode(0), new INode(1), new INode(4), new INode(5)))
    path1.setWeight(5.0)
    val path2 = new IPath[Int](List(new INode(0), new INode(1), new INode(4), new INode(3), new INode(5)))
    path2.setWeight(10.0)
    val expPaths = List(path1, path2)
    verify("test_7", 0, 5, 2, expPaths)
  }

  // Should get:
  // 15.0 3 3
  // 4.0 0 1 3
  // 4.0 0 1 4 3
  // 7.0 0 1 2 4 3
  test("Find the shortest paths in a graph 'test_7' from 0 -> 3, k=3") {
    val path1 = new IPath[Int](List(new INode(0), new INode(1), new INode(3)))
    path1.setWeight(4.0)
    val path2 = new IPath[Int](List(new INode(0), new INode(1), new INode(4), new INode(3)))
    path2.setWeight(4.0)
    val path3 = new IPath[Int](List(new INode(0), new INode(1), new INode(2), new INode(4), new INode(3)))
    path3.setWeight(7.0)
    val expPaths = List(path1, path2, path3)
    verify("test_7", 0, 3, 3, expPaths)
  }

  // Should get:
  // 15.0 5 2
  // 5.0 0 1 4 5
  // 10.0 0 1 4 3 5
  test("Find the shortest paths in a graph 'test_7' from 0 -> 5, k=2") {
    val path1 = new IPath[Int](List(new INode(0), new INode(1), new INode(4), new INode(5)))
    path1.setWeight(5.0)
    val path2 = new IPath[Int](List(new INode(0), new INode(1), new INode(4), new INode(3), new INode(5)))
    path2.setWeight(10.0)
    val expPaths = List(path1, path2)
    verify("test_7", 0, 5, 2, expPaths)
  }

  test("Find the shortest paths in a graph 'test_8' from 0 -> 5") {
    val path1 = new IPath[Int](List(new INode(0), new INode(6), new INode(7), new INode(5)))
    path1.setWeight(3.0)
    val path2 = new IPath[Int](List(new INode(0), new INode(1), new INode(3), new INode(5)))
    path2.setWeight(3.0)
    val expPaths = List(path1, path2)
    verify("test_8", 0, 5, 2, expPaths)
  }

  test("Find the shortest paths in a graph 'test_50' from 0 -> 5") {
    val path1 = new Path(List(new INode(0), new INode(13), new INode(5)))
    path1.setWeight(0.1549167)
    val path2 = new Path(List(new INode(0), new INode(13), new INode(31), new INode(5)))
    path2.setWeight(0.19805129999999999)
    val expPaths = List(path1, path2)
    verify("test_50", 0, 5, 2, expPaths)
  }

  // Should get:
  // 0.1390212 0 17 7
  // 0.1746318 0 17 25 7
  test("Find 2 the shortest paths in a graph 'test_50_2' from 0 -> 7") {
    val path1 = new Path(List(new INode(0), new INode(23), new INode(10), new INode(35), new INode(39), new INode(25), new INode(7)))
    path1.setWeight(0.3396084)
    val path2 = new Path(List(new INode(0), new INode(23), new INode(10), new INode(35), new INode(39), new INode(38), new INode(37), new INode(25), new INode(7)))
    path2.setWeight(0.5350616)  // this is not the second shortest
    val expPath = List(path1, path2)
    verify("test_50_2", 0, 7, 2, expPath)
  }

  // Should get:
  // 0.1390212 0 17 7
  // 0.1746318 0 17 25 7
  // 0.1843206 0 17 25 32 7
  // This case fails to get optimal result because there are redundant edges between some nodes, that this algorithm does not necessarily pick the smallest.
  test("Find 3 the shortest paths in a graph 'test_50_2' from 0 -> 7") {
    val path1 = new Path(List(new INode(0), new INode(23), new INode(10), new INode(35), new INode(39), new INode(25), new INode(7)))
    path1.setWeight(0.3396084)
    val path2 = new Path(List(new INode(0), new INode(23), new INode(10), new INode(35), new INode(2), new INode(27), new INode(25), new INode(7)))
    path2.setWeight(0.5078661999999999) // this is not the 2nd shortest
    val path3 = new Path(List(new INode(0), new INode(23), new INode(10), new INode(35), new INode(39), new INode(25), new INode(6), new INode(17), new INode(7)))
    path3.setWeight(0.6347512)
    val expPath = List(path1, path2, path3)
    verify("test_50_2", 0, 7, 3, expPath)
  }

  // Should get:
  // 0.7308888 7 4
  // 0.1390212 0 17 7
  // 0.1746318 0 17 25 7
  // 0.1843206 0 17 25 32 7
  // 0.2329152 0 17 4 25 7
  // This case fails to get optimal result because there are redundant edges between some nodes, that this algorithm does not necessarily pick the smallest.
  test("Find 4 the shortest paths in a graph 'test_50_2' from 0 -> 7") {
    val path1 = new Path(List(new INode(0), new INode(23), new INode(10), new INode(35), new INode(39), new INode(25), new INode(7)))
    path1.setWeight(0.3396084)
    val path2 = new Path(List(new INode(0), new INode(23), new INode(26), new INode(32), new INode(21), new INode(27), new INode(25), new INode(7)))
    path2.setWeight(0.4762675) // this is not the 2nd shortest
    val path3 = new Path(List(new INode(0), new INode(23), new INode(26), new INode(32), new INode(21), new INode(27), new INode(10), new INode(35), new INode(39), new INode(25), new INode(7)))
    path3.setWeight(0.5996489)  // this is not the 4rd shortest
    val path4 = new Path(List(new INode(0), new INode(23), new INode(26), new INode(32), new INode(21), new INode(27), new INode(25), new INode(6), new INode(17), new INode(7)))
    path4.setWeight(0.6347512) // this is not the 4th shortest
    val expPath = List(path1, path2, path3, path4)
    verify("test_50_2", 0, 7, 4, expPath)
  }

  test("Find the shortest paths in a graph 'eclair_6_2' from 0 -> 3") {
    val path1 = new IPath[Int](List(new INode(0), new INode(4), new INode(5), new INode(3)))
    path1.setWeight(100090.01)
    val path2 = new IPath[Int](List(new INode(0), new INode(1), new INode(2), new INode(3)))
    path2.setWeight(100110.02)
    val expPath = List(path1, path2)
    verify("eclair_6_2", 0, 3, 2, expPath)
  }

  def verify(fileName: String, source: Int, dest: Int, k: Int, expectedPath: List[Path]): Unit = {
    val graph = TopKShortestPaths.importGraph("data/" + fileName)
    val shortestPathsFinder = new TopKShortestPaths[Int](graph)
    val path: List[Path] = shortestPathsFinder.findPaths(source, dest, k)
    assertResult(expectedPath) { path }
  }

}
