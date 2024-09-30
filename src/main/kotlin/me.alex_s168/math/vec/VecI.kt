package me.alex_s168.math.vec

import java.nio.ByteBuffer
import java.nio.IntBuffer
import java.util.Arrays

abstract class VecI<S: VecI<S>>(
    final override val size: Int,
    val data: IntArray = IntArray(size),
    val offset: Int = 0
): IntVecLike<S> {

    override fun toArray(): IntArray {
        return data.copyOfRange(offset, size + offset)
    }

    override fun asArray(): IntArray {
        if (offset == 0 && data.size == size) {
            return data
        }
        throw UnsupportedOperationException("Cannot return backing array")
    }

    override fun writeTo(buffer: IntBuffer) {
        for (i in offset until size + offset) {
            buffer.put(data[i])
        }
    }

    override fun writeTo(arr: IntArray, offset: Int) {
        var writer = offset
        for (i in this.offset until size + this.offset) {
            arr[writer++] = data[i]
        }
    }

    override fun writeTo(buff: ByteBuffer) {
        for (i in offset until size + offset) {
            buff.putInt(data[i])
        }
    }

    override fun from(other: VecLike<Int, S>) {
        if (other.size != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        var reader = 0
        for (i in offset until size + offset) {
            data[i] = other[reader ++]
        }
    }

    override fun from(other: IntArray) {
        if (other.size != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        var reader = 0
        for (i in offset until size + offset) {
            data[i] = other[reader ++]
        }
    }

    override fun from(other: IntBuffer) {
        if (other.remaining() != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other.get()
        }
    }

    override fun from(other: ByteBuffer) {
        if (other.remaining() != size * 4) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other.getInt()
        }
    }

    override fun get(index: Int): Int {
        return data[index + offset]
    }

    override fun set(index: Int, value: Int) {
        data[index + offset] = value
    }

    override fun zeroSelf(): S {
        Arrays.fill(data, offset, size + offset, 0)
        return this as S
    }
}