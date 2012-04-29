
val HASH = "xbdz"
val FILE = "rainbow.tbl"


abstract class Rainbow {
    self: HashAndReg =>

    val table: Map[Hash, Seq[Key]]

    def walk (from: Key, to: Hash, limit: Hash): Option[Key] = {
      var _k = from

      println("walk: %s" format from)
      h(from) match {
        case hash if hash == to => Some(from)
        case hash if hash == limit => None
        case hash => walk( r(hash), to, limit)
      }
    }

    def lookup(pwhash: Hash): Option[Key] = {
      var _hash = pwhash
      var _tbl  = table
      var _key: Option[Key] = None

      while (_key == None && !_tbl.isEmpty) {
        println("III continue chain selection")

        _tbl.get(_hash) match {
          case Some(keys) => {
            println("III chain match on hash [%s] -> (%d)"
              .format (_hash, keys.size))
            val p = keys
            .map(walk(_, pwhash, _hash))
            .filter(_ != None)

            if (p isEmpty) {
                // update map: remove chains
                println("WWW dismiss chain(s): %s" format _hash)
                _tbl -= _hash
            } else {
              // we have finished
              _key = p.head
            }
          }
          case _ =>
        }
        _hash = h(r(_hash))
      }
      _key
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


trait FooBar extends HashAndReg {

    type Hash = Seq[Char]
    type Key  = Seq[Char]

    private val b = 1
    private val a = 'a'.toInt

    implicit def seqInt2str(c: Seq[Int]): Seq[Char] = {
      c.map { case (i) => (i%26 + a) toChar}
    }

    def h(key: Key) : Hash = {
        val hash: Hash = key
        .sliding(2, 2)
        .map(_.foldLeft(0)(_+_-a))
        .map(_%26).toSeq
        println("  h(key): %s -> %s" format (key.mkString, hash.mkString))
        hash
    }

    def r(hash: Hash) : Key = {
        val part1 = hash
        val part2: Seq[Char] = hash.reverse.map(_+b-a).map(_%26)
        val key: Key = part1 ++ part2
        println("  r(hash): %s -> %s" format (hash.mkString, key.mkString))
        key
    }
}

/**
 *
 */
object Run {

    type A = Seq[Char]

    val file_map: Map[A, Seq[A]] = {
      val lmap = io.Source.fromFile(FILE)
        .getLines()
        .map {(l:String) =>
          val p = l.splitAt(8)
          val h = p._2.tail.toSeq // remove leading whitespace
          val k = p._1.toSeq
          (h -> k)
        }
      lmap.toSeq
      .groupBy (_._1)
      .map { case (k,v) => (k -> v.map(_._2)) } toMap
    }

    object rainbow extends Rainbow with FooBar {
      override val table = file_map
    }

    def apply(s: String) = {
        rainbow.lookup(s.toSeq)
    }

}

Run("xbdz") match {
  case None => println("no password found")
  case Some(p) => println("retrieved pass word: %s" format p.mkString)
}
