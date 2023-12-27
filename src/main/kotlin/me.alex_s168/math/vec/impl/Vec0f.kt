package me.alex_s168.math.vec.impl

import me.alex_s168.math.vec.VecF

class Vec0f(
    size: Int,
    data: FloatArray = FloatArray(size),
    offset: Int = 0
): VecF<Vec0f>(size, data, offset) {
    override fun new(): Vec0f =
        Vec0f(size)

    override fun copy(): Vec0f =
        Vec0f(size).also {
            it.from(this)
        }
}