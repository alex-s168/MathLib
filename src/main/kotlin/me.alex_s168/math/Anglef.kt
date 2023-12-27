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

    companion object {
        fun fromRadians(radians: Float) =
            Anglef(radians)

        fun fromDegrees(degrees: Float) =
            Anglef(degrees * Math.PI.toFloat() / 180f)

        fun fromGradians(gradians: Float) =
            Anglef(gradians * Math.PI.toFloat() / 200f)
    }
}
