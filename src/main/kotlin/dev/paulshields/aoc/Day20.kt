/**
 * Day 20: Trench Map
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsStringList

fun main() {
    println(" ** Day 20: Trench Map ** \n")

    val trenchMapImage = readFileAsStringList("/Day20TrenchMapImage.txt")
    val imageEnhancementAlgorithm = trenchMapImage[0]
    val image = trenchMapImage.drop(1)

    val enhancedImage = enhanceImage(image, imageEnhancementAlgorithm, 2)
    println("There are ${sumOfLitPixels(enhancedImage)} in the enhanced image")

    val furtherEnhancedImage = enhanceImage(image, imageEnhancementAlgorithm, 50)
    println("There are ${sumOfLitPixels(furtherEnhancedImage)} in the further enhanced image")
}

fun sumOfLitPixels(image: List<String>) = image.sumOf { it.sumOf { if (it == '#') 1L else 0L } }

fun enhanceImage(image: List<String>, imageEnhancementAlgorithm: String, numberOfIterations: Int = 1): List<String> {
    var processedImage = image
    var outOfBoundsPixel = '.'

    for (i in 0 until numberOfIterations) {
        processedImage = processImageEnhancementIteration(processedImage, imageEnhancementAlgorithm, outOfBoundsPixel)
        outOfBoundsPixel = if (outOfBoundsPixel == '#') imageEnhancementAlgorithm.last() else imageEnhancementAlgorithm.first()
    }

    return processedImage
}

private fun processImageEnhancementIteration(image: List<String>, imageEnhancementAlgorithm: String, outOfBoundsPixel: Char) =
    (-1 until image.height + 1).map { y ->
        (-1 until image.width + 1).map { x ->
            enhancePixelFromImage(x, y, image, imageEnhancementAlgorithm, outOfBoundsPixel)
        }.joinToString("")
    }

private fun enhancePixelFromImage(x: Int, y: Int, image: List<String>, imageEnhancementAlgorithm: String, unprocessedPixel: Char): Char {
    val enhancementPositionAsBinaryString = (y - 1..y + 1).joinToString("") { consideredY ->
        (x - 1..x + 1).joinToString("") { consideredX ->
            when (getPixelFromImageOrElse(consideredX, consideredY, image, unprocessedPixel)) {
                '#' -> "1"
                else -> "0"
            }
        }
    }

    return imageEnhancementAlgorithm[enhancementPositionAsBinaryString.toInt(2)]
}

private fun getPixelFromImageOrElse(x: Int, y: Int, image: List<String>, orElse: Char) = image.getOrNull(y)?.getOrNull(x) ?: orElse

private val List<String>.width: Int
    get() = this.first().length

private val List<String>.height: Int
    get() = this.size
