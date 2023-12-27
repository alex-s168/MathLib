package me.alex_s168.math

import me.alex_s168.math.vec.impl.Vec3f

interface EnclosedVolume3 {
    fun isInside(point: Vec3f): Boolean

    fun isInside(point: Vec3f, tolerance: Float): Boolean

    val bb: AABB3f
}