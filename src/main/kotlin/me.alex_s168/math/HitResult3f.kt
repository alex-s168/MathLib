package me.alex_s168.math

import me.alex_s168.math.vec.impl.Vec3f

/**
 * Represents the result of a ray cast.
 * @property hitPoint The point at which the ray hit the object.
 * @property hitNormal The normal of the object at the point of intersection.
 */
data class HitResult3f(
    val hitPoint: Vec3f?,
    val hitNormal: Vec3f?
)