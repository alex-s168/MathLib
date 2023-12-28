package me.alex_s168.math.mat.stack

import me.alex_s168.math.Anglef
import me.alex_s168.math.mat.impl.Mat4f
import me.alex_s168.math.vec.impl.Quaternionf
import me.alex_s168.math.vec.impl.Vec3af
import me.alex_s168.math.vec.impl.Vec3f

class Mat4fStack(
    val stack: MutableList<Mat4f> = mutableListOf(Mat4f())
) {
    fun push() {
        stack += stack.last().copy()
    }

    fun pop() {
        stack.removeLast()
    }

    fun top() =
        stack.last()

    fun translate(a: Vec3f): Mat4fStack {
        stack.last().translateSelf(a)
        return this
    }

    fun rotate(a: Vec3f, angle: Anglef): Mat4fStack {
        stack.last().rotateSelf(a, angle)
        return this
    }

    fun rotate(by: Vec3af): Mat4fStack {
        stack.last().rotateSelf(by)
        return this
    }

    fun rotate(by: Quaternionf): Mat4fStack {
        stack.last().rotateSelf(by)
        return this
    }

    fun scale(by: Vec3f): Mat4fStack {
        stack.last().scaleSelf(by)
        return this
    }

    fun scale(by: Float): Mat4fStack {
        stack.last().scaleSelf(Vec3f(by, by, by))
        return this
    }
}