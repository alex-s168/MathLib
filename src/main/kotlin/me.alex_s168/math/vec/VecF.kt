package me.alex_s168.math.vec

import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.util.*

abstract class VecF<S: VecF<S>>(
    final override val size: Int,
    val data: FloatArray = FloatArray(size),
    val offset: Int = 0
): FloatVecLike<S> {

    override fun toArray(): FloatArray {
        return data.copyOfRange(offset, size + offset)
    }

    override fun asArray(): FloatArray {
        if (offset == 0 && data.size == size) {
            return data
        }
        throw UnsupportedOperationException("Cannot return backing array")
    }

    override fun writeTo(buffer: FloatBuffer) {
        for (i in offset until size + offset) {
            buffer.put(data[i])
        }
    }

    override fun writeTo(arr: FloatArray, offset: Int) {
        var writer = offset
        for (i in this.offset until size + this.offset) {
            arr[writer++] = data[i]
        }
    }

    override fun writeTo(buff: ByteBuffer) {
        for (i in offset until size + offset) {
            buff.putFloat(data[i])
        }
    }

    override fun from(other: VecLike<Float, S>) {
        if (other.size != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        var reader = 0
        for (i in offset until size + offset) {
            data[i] = other[reader ++]
        }
    }

    override fun from(other: FloatArray) {
        if (other.size != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        var reader = 0
        for (i in offset until size + offset) {
            data[i] = other[reader ++]
        }
    }

    override fun from(other: FloatBuffer) {
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
            data[i] = other.getFloat()
        }
    }

    override fun get(index: Int): Float {
        return data[index + offset]
    }

    override fun set(index: Int, value: Float) {
        data[index + offset] = value
    }

    override fun zeroSelf(): S {
        Arrays.fill(data, offset, size + offset, 0f)
        return this as S
    }

}