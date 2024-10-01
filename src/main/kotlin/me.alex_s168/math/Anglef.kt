package me.alex_s168.math

@JvmInline
value class Anglef(
    val radians: Float
) {
    val degrees get() =
        radians * 180f / Math.PI.toFloat()

    val gradians get() =
        radians * 200f / Math.PI.toFloat()

    fun normalized() =
        Anglef(radians % (2 * Math.PI.toFloat()))

    operator fun plus(other: Anglef) =
        Anglef(radians + other.radians)

    operator fun minus(other: Anglef) =
        Anglef(radians - other.radians)

    operator fun times(other: Anglef) =
        Anglef(radians * other.radians)

    operator fun div(other: Anglef) =
        Anglef(radians / other.radians)

    operator fun times(other: Float) =
        Anglef(radians * other)

    operator fun div(other: Float) =
        Anglef(radians / other)

    operator fun unaryMinus() =
        Anglef(-radians)

    override fun toString(): String {
        return "$degrees°"
    }

    companion object {
        fun fromRadians(radians: Float) =
            Anglef(radians)

        fun fromDegrees(degrees: Float) =
            Anglef(degrees * Math.PI.toFloat() / 180f)

        fun fromGradians(gradians: Float) =
            Anglef(gradians * Math.PI.toFloat() / 200f)
    }
}
