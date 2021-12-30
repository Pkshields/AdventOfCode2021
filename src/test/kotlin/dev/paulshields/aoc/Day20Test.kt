package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day20Test {
    private val sampleImageEnhancementAlgorithm = "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##" +
            "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###" +
            ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#." +
            ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#....." +
            ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.." +
            "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#....." +
            "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#"

    private val sampleImage = listOf(
        "#..#.",
        "#....",
        "##..#",
        "..#..",
        "..###")

    @Test
    fun `should correctly enhance the sample image once`() {
        val result = enhanceImage(sampleImage, sampleImageEnhancementAlgorithm, 1)

        assertThat(result).isEqualTo(listOf(
            ".##.##.",
            "#..#.#.",
            "##.#..#",
            "####..#",
            ".#..##.",
            "..##..#",
            "...#.#."))
    }

    @Test
    fun `should correctly enhance the sample image twice`() {
        val result = enhanceImage(sampleImage, sampleImageEnhancementAlgorithm, 2)

        assertThat(result).isEqualTo(listOf(
            ".......#.",
            ".#..#.#..",
            "#.#...###",
            "#...##.#.",
            "#.....#.#",
            ".#.#####.",
            "..#.#####",
            "...##.##.",
            "....###.."))
    }

    @Test
    fun `should correctly count all lit pixels in image`() {
        val result = sumOfLitPixels(sampleImage)

        assertThat(result).isEqualTo(10)
    }

    @Test
    fun `should correctly count all lit pixels in enhanced image`() {
        val enhancedImage = enhanceImage(sampleImage, sampleImageEnhancementAlgorithm, 2)
        val result = sumOfLitPixels(enhancedImage)

        assertThat(result).isEqualTo(35)
    }
}
