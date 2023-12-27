package me.alex_s168.math

import me.alex_s168.math.vec.impl.Vec3f

/**
 * A ray is a line with an origin and a direction.
 * @param origin The origin of the ray.
 * @param direction The direction of the ray (should be normalized).
 */
data class Ray3f (
    val origin: Vec3f,
    val direction: Vec3f
)