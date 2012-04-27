


abstract class Rainbow {
    self: HashAndReg =>

    val table: Map[Hash, Raw]

    def lookup(h: Hash): Raw {
        // TODO implement logic here
    }
}


trait RegFun {
    type Hash
    type Raw

    def r: (Hash => Raw)
}

trait HashFun {
    type Hash
    type Raw

    def h: (Raw => Hash)
}

trait HashAndReg extends RegFun with HashFun


object CharRules {
    val a = 'a'.toByte

    implicit def char2int (c: Char) : Int = {
       c.toByte - a
    }
    implicit def int2char (i: Int) : Char = {
       i + a
    }

    implicit def itInt2SeqChar(c: Iterator[Int]): Seq[Char] = {
        c.map(_ % 26 + a toChar) toSeq
    }

    val b = 'b' - a
}

trait FooBar extends HashAndReg {
    import CharRules._

    type Hash = Seq[Char]
    type Raw  = Seq[Char]

    def h(hash: Hash): Raw = {
        hash
        .sliding(2, 2)
        .map(_.foldLeft(0)(_+_))
    }

    def r(raw: Raw): Hash = {
        raw ++ (raw.reverse.map(_+b % 26 + a toChar))
    }
}


