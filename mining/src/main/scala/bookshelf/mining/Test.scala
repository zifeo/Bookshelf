package bookshelf.mining

/**
  * Created by asoccard on 29/02/16.
  */
class Test {

  def pagesTest() = {
    val values = List(("40", (40, 0)), ("374+[2]", (374, 2)), ("413+[2]", (413, 2)), ("395+[3]", (395, 3)),
      ("208", (208, 0)), ("ix+195", (195, 9)), ("vi+254", (254, 6)), ("127+124+122", (373, 0)), ("xxxii+815", (815, 32)),
      ("[12]+158+vii+182", (340, 19)),("158+vii+[12]+182", (340, 19)),("158+Cvii+33+[12]+182", (373, 119))
    )
    // check this to compare option and not otpion (normally, we get NO option
    println(values.map(x => (getPages(x._1), x._2)).count(x => x._1 != Some(x._2)))
  }

}
