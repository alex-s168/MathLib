package me.alex_s168.math.mat.impl

import me.alex_s168.math.Anglef
import me.alex_s168.math.mat.MatF
import me.alex_s168.math.vec.impl.Quaternionf
import me.alex_s168.math.vec.impl.Vec3af
import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.math.vec.impl.Vec4f
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class Mat4f(
    data: FloatArray = FloatArray(16),
    offset: Int = 0
): MatF<Mat4f>(16, data, offset) {
    init {
        require(offset + 16 <= data.size) {
            "offset + 16 > data.size"
        }
    }

    var m00: Float
        get() = get(0, 0)
        set(value) = set(0, 0, value)

    var m01: Float
        get() = get(0, 1)
        set(value) = set(0, 1, value)

    var m02: Float
        get() = get(0, 2)
        set(value) = set(0, 2, value)

    var m03: Float
        get() = get(0, 3)
        set(value) = set(0, 3, value)

    var m10: Float
        get() = get(1, 0)
        set(value) = set(1, 0, value)

    var m11: Float
        get() = get(1, 1)
        set(value) = set(1, 1, value)

    var m12: Float
        get() = get(1, 2)
        set(value) = set(1, 2, value)

    var m13: Float
        get() = get(1, 3)
        set(value) = set(1, 3, value)

    var m20: Float
        get() = get(2, 0)
        set(value) = set(2, 0, value)

    var m21: Float
        get() = get(2, 1)
        set(value) = set(2, 1, value)

    var m22: Float
        get() = get(2, 2)
        set(value) = set(2, 2, value)

    var m23: Float
        get() = get(2, 3)
        set(value) = set(2, 3, value)

    var m30: Float
        get() = get(3, 0)
        set(value) = set(3, 0, value)

    var m31: Float
        get() = get(3, 1)
        set(value) = set(3, 1, value)

    var m32: Float
        get() = get(3, 2)
        set(value) = set(3, 2, value)

    var m33: Float
        get() = get(3, 3)
        set(value) = set(3, 3, value)

    constructor(
        m00: Float, m01: Float, m02: Float, m03: Float,
        m10: Float, m11: Float, m12: Float, m13: Float,
        m20: Float, m21: Float, m22: Float, m23: Float,
        m30: Float, m31: Float, m32: Float, m33: Float
    ) : this() {
        this.m00 = m00
        this.m01 = m01
        this.m02 = m02
        this.m03 = m03
        this.m10 = m10
        this.m11 = m11
        this.m12 = m12
        this.m13 = m13
        this.m20 = m20
        this.m21 = m21
        this.m22 = m22
        this.m23 = m23
        this.m30 = m30
        this.m31 = m31
        this.m32 = m32
        this.m33 = m33
    }

    override fun toString(): String {
        return "Mat4f($m00, $m01, $m02, $m03, $m10, $m11, $m12, $m13, $m20, $m21, $m22, $m23, $m30, $m31, $m32, $m33)"
    }

    override fun new() =
        Mat4f()

    companion object {
        fun wrap(data: FloatArray, offset: Int = 0) =
            Mat4f(data, offset)

        fun wrap(mat: MatF<*>, offset: Int = 0) =
            Mat4f(mat.data, mat.offset + offset)

        fun identity() =
            Mat4f(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )

        fun perspective(fov: Anglef, aspect: Float, near: Float, far: Float): Mat4f {
            val frustumLength = far - near
            val yScale = ((1 / tan(fov.radians / 2)) * aspect)
            val xScale = yScale / aspect
            val mat = Mat4f()
            mat.m00 = xScale
            mat.m11 = yScale
            mat.m22 = -((far + near) / frustumLength)
            mat.m23 = -1f
            mat.m32 = -((2 * near * far) / frustumLength)
            return mat
        }

        private fun scaleMatrix(sx: Float, sy: Float, sz: Float): Mat4f {
            return Mat4f(
                sx, 0f, 0f, 0f,
                0f, sy, 0f, 0f,
                0f, 0f, sz, 0f,
                0f, 0f, 0f, 1f
            )
        }

        private fun translateMatrix(x: Float, y: Float, z: Float): Mat4f {
            return Mat4f(
                1f, 0f, 0f, x,
                0f, 1f, 0f, y,
                0f, 0f, 1f, z,
                0f, 0f, 0f, 1f
            )
        }

        private fun rotationMatrixX(radians: Float): Mat4f {
            val crad = cos(radians.toDouble()).toFloat()
            val srad = sin(radians.toDouble()).toFloat()
            return Mat4f(
                1f, 0f, 0f, 0f,
                0f, crad, srad, 0f,
                0f, -srad, crad, 0f,
                0f, 0f, 0f, 1f
            )
        }

        private fun rotationMatrixY(radians: Float): Mat4f {
            val crad = cos(radians.toDouble()).toFloat()
            val srad = sin(radians.toDouble()).toFloat()
            return Mat4f(
                crad, 0f, -srad, 0f,
                0f, 1f, 0f, 0f,
                srad, 0f, crad, 0f,
                0f, 0f, 0f, 1f
            )
        }

        private fun rotationMatrixZ(radians: Float): Mat4f {
            val crad = cos(radians.toDouble()).toFloat()
            val srad = sin(radians.toDouble()).toFloat()
            return Mat4f(
                crad, -srad, 0f, 0f,
                srad, crad, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        }

        private fun rotationMatrix(rx: Float, ry: Float, rz: Float): Mat4f {
            return rotationMatrixZ(rz) * rotationMatrixY(ry) * rotationMatrixX(rx)
        }

        fun rotationMatrix(rotMat: Mat3f): Mat4f =
            Mat4f(
                rotMat.m00, rotMat.m01, rotMat.m02, 0f,
                rotMat.m10, rotMat.m11, rotMat.m12, 0f,
                rotMat.m20, rotMat.m21, rotMat.m22, 0f,
                0f, 0f, 0f, 1f
            )

        fun distribute(scalar: Float) =
            Mat4f(scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar)
    }

    fun translateSelf(x: Float, y: Float, z: Float): Mat4f {
        this *= translateMatrix(x, y, z)
        return this
    }

    fun translateSelf(by: Vec3f): Mat4f {
        translateSelf(by.x, by.y, by.z)
        return this
    }

    fun rotateSelf(by: Vec3f, angle: Anglef): Mat4f {
        rotateSelf(by * angle.radians)
        return this
    }

    fun rotateSelf(by: Vec3f): Mat4f {
        this *= rotationMatrix(by.x, by.y, by.z)
        return this
    }

    fun rotateSelf(by: Mat3f): Mat4f {
        this *= rotationMatrix(by)
        return this
    }

    fun rotateSelf(by: Quaternionf): Mat4f {
        this *= rotationMatrix(by.rotMat3())
        return this
    }

    fun rotateSelf(by: Vec3af): Mat4f {
        rotateSelf(Vec3f(by.yaw.radians, by.pitch.radians, by.roll.radians))
        return this
    }

    fun scaleSelf(x: Float, y: Float, z: Float): Mat4f {
        this *= scaleMatrix(x, y, z)
        return this
    }

    fun scaleSelf(by: Vec3f): Mat4f {
        this *= scaleMatrix(by.x, by.y, by.z)
        return this
    }

    operator fun times(vec: Vec4f): Vec4f = Vec4f(
            m00 * vec.x + m01 * vec.y + m02 * vec.z + m03 * vec.w,
            m10 * vec.x + m11 * vec.y + m12 * vec.z + m13 * vec.w,
            m20 * vec.x + m21 * vec.y + m22 * vec.z + m23 * vec.w,
            m30 * vec.x + m31 * vec.y + m32 * vec.z + m33 * vec.w
    )

}