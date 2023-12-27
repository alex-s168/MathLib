package me.alex_s168.math

import me.alex_s168.math.vec.impl.Vec3f

/**
 * Axis-aligned bounding box.
 * @property min The minimum point of the box.
 * @property max The maximum point of the box.
 */
data class AABB3f (
    val min: Vec3f,
    val max: Vec3f
): EnclosedVolume3 {

    /**
     * The height of the box.
     */
    val height: Float get() =
        max.y - min.y

    /**
     * The width of the box.
     */
    val width: Float get() =
        max.x - min.x

    /**
     * The depth of the box.
     */
    val depth: Float get() =
        max.z - min.z

    /**
     * A vector representing the size of the box. (width, height, depth)
     */
    val size: Vec3f get() =
        Vec3f(width, height, depth)

    /**
     * Returns a string representation of the box.
     */
    override fun toString(): String =
        "AABB($min, $max)"

    /**
     * Returns true if the given point is inside the box.
     */
    override fun isInside(point: Vec3f): Boolean =
        point.x in min.x..max.x &&
                point.y in min.y..max.y &&
                point.z in min.z..max.z

    override fun isInside(point: Vec3f, tolerance: Float): Boolean =
        point.x in min.x - tolerance..max.x + tolerance &&
                point.y in min.y - tolerance..max.y + tolerance &&
                point.z in min.z - tolerance..max.z + tolerance

    override val bb: AABB3f
        get() = this

    /**
     * Combines this box with another box, returning a new box that contains both.
     */
    infix fun with(other: AABB3f): AABB3f =
        AABB3f(
            min = Vec3f(
                minOf(min.x, other.min.x),
                minOf(min.y, other.min.y),
                minOf(min.z, other.min.z)
            ),
            max = Vec3f(
                maxOf(max.x, other.max.x),
                maxOf(max.y, other.max.y),
                maxOf(max.z, other.max.z)
            )
        )

}