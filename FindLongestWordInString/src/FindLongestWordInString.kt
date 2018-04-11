fun main(args: Array<String>) {
    //Read arguments
    val S = args[0]
    var D = args.toList().drop(1)

    //Pre-process D
    //1. re-order D by string length
    D = D.sortedWith(compareByDescending { it.length })

    //Pre-process S
    val SMap : HashMap<Char, ArrayList<Int>> = HashMap()
    for ((i, c) in S.withIndex()) {
        if (SMap.containsKey(c))
            //Non-null assert
            SMap[c]!!.add(i)
        else {
            val indexArray: ArrayList<Int> = ArrayList()
            indexArray.add(i)
            SMap[c] = indexArray
        }
    }

    val SMapIndex : HashMap<Char, Int?> = HashMap()
    for (key in SMap.keys)
        SMapIndex[key] = 0

    var previousIndex = -1
    for (s in D) {
        for(c in s) {
            if (!SMap.containsKey(c))
                break
            val index = SMapIndex[c]
            val currentIndex = SMap[c]!!.get(index!!)

            if (currentIndex < previousIndex)
                break

            //update SMapIndex
            index.plus(1)
            SMapIndex.remove(c)
            SMapIndex.put(c, index)

            previousIndex = currentIndex

            if (c == s.last())
                print(s)
        }
    }
}