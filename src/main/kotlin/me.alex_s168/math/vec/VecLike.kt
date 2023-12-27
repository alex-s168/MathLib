package me.alex_s168.math.vec

import java.nio.ByteBuffer

interface VecLike<T, S>: Collection<T> {
    operator fun get(index: Int): T
    operator fun set(index: Int, value: T)

    override fun iterator(): Iterator<T> =
        VecIterator(this)

    class VecIterator<T, S>(val vec: VecLike<T, S>): Iterator<T> {
        var index = 0
        override fun hasNext(): Boolean =
            index < vec.size
        override fun next(): T =
            vec[index++]
    }

    override fun contains(element: T): Boolean {
        for (e in this) {
            if (e == element) {
                return true
            }
        }
        return false
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            if (!contains(element)) {
                return false
            }
        }
        return true
    }

    override fun isEmpty(): Boolean =
        false

    fun copy(): S

    fun from(buf: ByteBuffer)
    fun writeTo(buf: ByteBuffer)
}