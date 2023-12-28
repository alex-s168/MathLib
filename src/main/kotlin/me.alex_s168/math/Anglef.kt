package me.alex_s168.math

data class Anglef(
    internal val valRadians: Float
) {
    val radians = valRadians

    val degrees get() =
        valRadians * 180f / Math.PI.toFloat()

    val gradians get() =
        valRadians * 200f / Math.PI.toFloat()

    val normalized by lazy {
        Anglef(valRadians % (2 * Math.PI.toFloat()))
    }

    operator fun plus(other: Anglef) =
        Anglef(valRadians + other.valRadians)

    operator fun minus(other: Anglef) =
        Anglef(valRadians - other.valRadians)

    operator fun times(other: Anglef) =
        Anglef(valRadians * other.valRadians)

    operator fun div(other: Anglef) =
        Anglef(valRadians / other.valRadians)

    operator fun times(other: Float) =
        Anglef(valRadians * other)

    operator fun div(other: Float) =
        Anglef(valRadians / other)

    operator fun unaryMinus() =
        Anglef(-valRadians)

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
