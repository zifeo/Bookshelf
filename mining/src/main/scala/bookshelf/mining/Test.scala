package bookshelf.mining

/**
  * Created by asoccard on 29/02/16.
  */
class Test {

  def testArabic = {
    println (1990 == toArabic ("MCMXC"))
    println (1991 == toArabic ("	MCMXCI"))
    println (1992 == toArabic ("	MCMXCII"))
    println (1993 == toArabic ("	MCMXCIII"))
    println (1994 == toArabic ("	MCMXCIV"))
    println (1995 == toArabic ("MCMXCV"))
    println (1996 == toArabic ("MCMXCVI"))
    println (1997 == toArabic ("MCMXCVII"))
    println (1998 == toArabic ("MCMXCVIII"))
    println (1999 == toArabic ("MCMXCIX"))
    println (2000 == toArabic ("	MM"))
    println (2001 == toArabic ("	MMI"))
    println (2002 == toArabic ("MMII"))
    println (2003 == toArabic ("MMIII"))
    println (2004 == toArabic ("MMIV"))
    println (2005 == toArabic ("	MMV"))
    println (2006 == toArabic ("MMVI"))
    println (2007 == toArabic ("	MMVII"))
    println (2008 == toArabic ("	MMVIII"))
    println (2009 == toArabic ("	MMIX"))
    println (2010 == toArabic ("	MMX"))
    println (2011 == toArabic ("MMXI"))
    println (2012 == toArabic ("MMXII"))
    println (2013 == toArabic ("	MMXIII"))
    println (2014 == toArabic ("	MMXIV"))
    println (2015 == toArabic ("	MMXV"))
    println (2016 == toArabic ("	MMXVI"))
    println (2017 == toArabic ("MMXVII"))
    println (2018 == toArabic ("	MMXVIII"))
    println (2019 == toArabic ("	MMXIX"))
    println (2020 == toArabic ("	MMXX"))
  }

}
