
val HASH = "xbdz"
val FILE = "rainbow.tbl"


abstract class Rainbow {
    self: HashAndReg =>

    val table: Map[Hash, Key]

    def lookup(hash: Hash): Key = {
        // TODO implement logic here

        r(hash)
    }
}


trait RegFun {
    type Hash
    type Key

    def r(h: Hash): Key
}

trait HashFun {
    type Hash
    type Key

    def h(key: Key): Hash
}

trait HashAndReg extends RegFun with HashFun


object CharRules {
    val a = 'a'.toInt

    def char2int (c: Char) : Int = {
        c.toInt - a
    }
    def int2char (i: Int) : Char = {
       (i%26) + a toChar
    }

    implicit def str2seqInt (s: Seq[Char]): Seq[Int] = {
        s.map(char2int)
    }

    implicit def seqInt2str(c: Seq[Int]): Seq[Char] = {
        c.map(int2char)
    }
}

trait FooBar extends HashAndReg {

    type Hash = Seq[Int]
    type Key  = Seq[Int]

    val b = 1

    def h(key: Key) : Hash = {
        key
        .sliding(2, 2)
        .map(_.foldLeft(0)(_+_)) toSeq
    }

    def r(hash: Hash) : Key = {
        hash ++ (hash.reverse.map(_+b))
    }
}

/**
 *
 */
object Run {
    import CharRules._

    def loadMap: Map[Seq[Int], Seq[Int]] = io.Source.fromFile(FILE).getLines().map { (l:String) =>
        val p = l.splitAt(8)
        val h: Seq[Int] = p._2.tail.toSeq
        val k: Seq[Int] = p._1.toSeq
        (h -> k)
    } toMap

    object rainbow extends Rainbow with FooBar {
        override val table = loadMap
    }

    def apply(s: String) = {
        val k: Seq[Char] = rainbow.lookup(s.toSeq)
        k
    }

}

val pass: Seq[Char] = Run("xbdz")

println("retrieved pass word: %s" format pass)
