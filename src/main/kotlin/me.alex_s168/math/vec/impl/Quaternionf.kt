package me.alex_s168.math.vec.impl

import me.alex_s168.math.Anglef
import me.alex_s168.math.mat.impl.Mat3f
import me.alex_s168.math.vec.FloatVecLike
import me.alex_s168.math.vec.NumVecLike
import me.alex_s168.math.vec.VecF
import me.alex_s168.math.vec.VecLike
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Quaternionf(
    val vec: Vec4f
): FloatVecLike<Quaternionf> {
    override fun toArray(): FloatArray =
        vec.toArray()

    override fun asArray(): FloatArray =
        vec.asArray()

    override fun writeTo(buffer: FloatBuffer) =
        vec.writeTo(buffer)

    override fun writeTo(arr: FloatArray, offset: Int) =
        vec.writeTo(arr, offset)

    override fun writeTo(buf: ByteBuffer) =
        vec.writeTo(buf)

    override fun from(other: FloatArray) =
        vec.from(other)

    override fun from(other: FloatBuffer) =
        vec.from(other)

    override fun from(buf: ByteBuffer) =
        vec.from(buf)

    override fun new(): Quaternionf =
        Quaternionf()

    override fun zeroSelf(): Quaternionf =
        this.also { vec.zeroSelf() }

    override fun get(index: Int): Float =
        vec[index]

    override fun set(index: Int, value: Float) =
        vec.set(index, value)

    override fun copy(): Quaternionf =
        Quaternionf(vec.copy())

    override fun from(other: VecLike<Float, Quaternionf>) {
        vec.x = other[0]
        vec.y = other[1]
        vec.z = other[2]
        vec.w = other[3]
    }

    override val size: Int get() = 4

    constructor(x: Float, y: Float, z: Float, w: Float):
            this(Vec4f(x, y, z, w))

    constructor():
            this(Vec4f())

    constructor(other: Quaternionf):
            this(other.vec)

    var x
        get() = vec.x
        set(value) { vec.x = value }

    var y
        get() = vec.y
        set(value) { vec.y = value }

    var z
        get() = vec.z
        set(value) { vec.z = value }

    var w
        get() = vec.w
        set(value) { vec.w = value }

    val conj: Quaternionf get() =
        Quaternionf(-x, -y, -z, w)

    override fun timesAssign(other: Float) {
        x *= other
        y *= other
        z *= other
        w *= other
    }

    operator fun timesAssign(other: Quaternionf) {
        val x = this.x
        val y = this.y
        val z = this.z
        val w = this.w
        this.x = w * other.x + x * other.w + y * other.z - z * other.y
        this.y = w * other.y + y * other.w + z * other.x - x * other.z
        this.z = w * other.z + z * other.w + x * other.y - y * other.x
        this.w = w * other.w - x * other.x - y * other.y - z * other.z
    }

    operator fun divAssign(other: Quaternionf) {
        val x = this.x
        val y = this.y
        val z = this.z
        val w = this.w
        val d = 1f / (other.x * other.x + other.y * other.y + other.z * other.z + other.w * other.w)
        this.x = (w * other.x - x * other.w - y * other.z + z * other.y) * d
        this.y = (w * other.y - y * other.w - z * other.x + x * other.z) * d
        this.z = (w * other.z - z * other.w - x * other.y + y * other.x) * d
        this.w = (w * other.w + x * other.x + y * other.y + z * other.z) * d
    }

    fun clone(): Quaternionf =
        Quaternionf().also { it.from(this) }

    operator fun times(other: Quaternionf): Quaternionf =
        clone().also { it *= other }

    operator fun div(other: Quaternionf): Quaternionf =
        clone().also { it /= other }

    fun rotMat3(): Mat3f {
        val x2 = x * x
        val y2 = y * y
        val z2 = z * z
        val xy = x * y
        val xz = x * z
        val yz = y * z
        val wx = w * x
        val wy = w * y
        val wz = w * z
        return Mat3f(
            1f - 2f * (y2 + z2), 2f * (xy - wz), 2f * (xz + wy),
            2f * (xy + wz), 1f - 2f * (x2 + z2), 2f * (yz - wx),
            2f * (xz - wy), 2f * (yz + wx), 1f - 2f * (x2 + y2)
        )
    }

    fun slerp(other: Quaternionf, t: Float): Quaternionf =
        slerp(this, other, t)

    infix fun slerp(a: Pair<Float, Quaternionf>): Quaternionf =
        slerp(this, a.second, a.first)

    companion object {
        fun wrap(data: FloatArray, offset: Int = 0) =
            Quaternionf(Vec4f.wrap(data, offset))

        fun wrap(vec: VecF<*>): Quaternionf =
            Quaternionf(Vec4f.wrap(vec))

        fun fromAxisAngle(axis: Vec3f, angle: Anglef): Quaternionf {
            val halfAngle = angle.radians / 2
            val sin = sin(halfAngle.toDouble()).toFloat()
            val cos = cos(halfAngle.toDouble()).toFloat()
            return Quaternionf(axis.x * sin, axis.y * sin, axis.z * sin, cos)
        }

        fun fromEuler(x: Float, y: Float, z: Float): Quaternionf {
            val c1 = cos(x * 0.5f)
            val c2 = cos(y * 0.5f)
            val c3 = cos(z * 0.5f)
            val s1 = sin(x * 0.5f)
            val s2 = sin(y * 0.5f)
            val s3 = sin(z * 0.5f)
            return Quaternionf(
                s1 * c2 * c3 + c1 * s2 * s3,
                c1 * s2 * c3 - s1 * c2 * s3,
                c1 * c2 * s3 + s1 * s2 * c3,
                c1 * c2 * c3 - s1 * s2 * s3
            )
        }

        fun fromEuler(euler: Vec3f): Quaternionf =
            fromEuler(euler.x, euler.y, euler.z)

        fun from(vec: Vec3af) =
            fromEuler(vec.yaw.radians, vec.pitch.radians, vec.roll.radians)

        fun from(mat: Mat3f): Quaternionf {
            val w = sqrt(1.0 + mat[0, 0] + mat[1, 1] + mat[2, 2]) * 0.5f
            val w4 = 4f * w
            val x = (mat[2, 1] - mat[1, 2]) / w4
            val y = (mat[0, 2] - mat[2, 0]) / w4
            val z = (mat[1, 0] - mat[0, 1]) / w4
            return Quaternionf(
                x.toFloat(),
                y.toFloat(),
                z.toFloat(),
                w.toFloat()
            )
        }

        fun rotateVecByQuat(vec: Vec3f, quat: Quaternionf): Vec3f {
            val qvec = Vec3f(quat.x, quat.y, quat.z)
            val uv = qvec cross vec
            val uuv = qvec cross uv
            uv *= (2f * quat.w)
            uuv *= 2f
            return vec + uv + uuv
        }

        private fun slerp(a: Quaternionf, b: Quaternionf, t: Float): Quaternionf {
            var t = t
            var flip = false
            var cosine = a.dot(b)
            if (cosine < 0) {
                cosine = -cosine
                flip = true
            }
            if (cosine > 1 - 1e-6) {
                val interp = t
                t = 1 - interp
            }
            val theta = acos(cosine.toDouble()).toFloat()
            val st0 = sin((1 - t) * theta)
            val st1 = sin(t * theta)
            val quat = Quaternionf()
            if (flip) {
                quat.x = -a.x * st0 + b.x * st1
                quat.y = -a.y * st0 + b.y * st1
                quat.z = -a.z * st0 + b.z * st1
                quat.w = -a.w * st0 + b.w * st1
            } else {
                quat.x = a.x * st0 + b.x * st1
                quat.y = a.y * st0 + b.y * st1
                quat.z = a.z * st0 + b.z * st1
                quat.w = a.w * st0 + b.w * st1
            }
            return quat
        }
    }
}