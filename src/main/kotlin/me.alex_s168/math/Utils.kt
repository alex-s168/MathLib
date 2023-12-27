package me.alex_s168.math

import me.alex_s168.math.vec.impl.Vec3f

val Iterable<Vec3f>.bb: AABB3f get() =
    fold(AABB3f(Vec3f(), Vec3f())) { acc, vec ->
        AABB3f(
            Vec3f(
                minOf(acc.min.x, vec.x),
                minOf(acc.min.y, vec.y),
                minOf(acc.min.z, vec.z)
            ),
            Vec3f(
                maxOf(acc.max.x, vec.x),
                maxOf(acc.max.y, vec.y),
                maxOf(acc.max.z, vec.z)
            )
        )
    }