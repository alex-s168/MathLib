package me.alex_s168.math.vec.impl

import me.alex_s168.math.Anglef
import me.alex_s168.math.vec.VecLike
import java.nio.ByteBuffer

class Vec3af(
    private val data: Array<Anglef> = arrayOf(Anglef(0f), Anglef(0f), Anglef(0f))
): VecLike<Anglef, Vec3af> {
    var yaw
        get() = data[0]
        set(value) { data[0] = value }

    var pitch
        get() = data[1]
        set(value) { data[1] = value }

    var roll
        get() = data[2]
        set(value) { data[2] = value }

    val radians: Vec3f get() =
        Vec3f(yaw.radians, pitch.radians, roll.radians)

    val degrees: Vec3f get() =
        Vec3f(yaw.degrees, pitch.degrees, roll.degrees)

    val gradians: Vec3f get() =
        Vec3f(yaw.gradians, pitch.gradians, roll.gradians)

    constructor(yaw: Anglef, pitch: Anglef, roll: Anglef): this(arrayOf(yaw, pitch, roll))

    override fun get(index: Int): Anglef =
        data[index]

    override fun copy(): Vec3af =
        Vec3af(yaw, pitch, roll)

    override fun from(buf: ByteBuffer) {
        for (i in 0 until size) {
            data[i] = Anglef.fromRadians(buf.getFloat())
        }
    }

    override fun writeTo(buf: ByteBuffer) {
        for (i in 0 until size) {
            buf.putFloat(data[i].radians)
        }
    }

    override fun set(index: Int, value: Anglef) {
        data[index] = value
    }

    override val size: Int = 3

    companion object {
        fun wrap(data: Array<Anglef>) =
            Vec3af(data)

        fun fromDegrees(v: Vec3f) =
            Vec3af(Anglef.fromDegrees(v.x), Anglef.fromDegrees(v.y), Anglef.fromDegrees(v.z))

        fun fromRadians(v: Vec3f) =
            Vec3af(Anglef.fromRadians(v.x), Anglef.fromRadians(v.y), Anglef.fromRadians(v.z))

        fun fromGradians(v: Vec3f) =
            Vec3af(Anglef.fromGradians(v.x), Anglef.fromGradians(v.y), Anglef.fromGradians(v.z))

        fun distribute(scalar: Anglef) =
            Vec3af(scalar, scalar, scalar)
    }
}