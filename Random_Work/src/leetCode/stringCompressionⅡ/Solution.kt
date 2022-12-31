package leetCode.`stringCompressionⅡ`

import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.min

class Solution {
	/*
	Run-length encoding is a string compression method that works by replacing consecutive identical characters (repeated 2 or more times) with the concatenation of the character and the number marking the count of the characters (length of the run). For example, to compress the string "aabccc" we replace "aa" by "a2" and replace "ccc" by "c3". Thus, the compressed string becomes "a2bc3".

	Notice that in this problem, we are not adding '1' after single characters.

	Given a string s and an integer k. You need to delete at most k characters from s such that the run-length encoded version of s has minimum length.

	Find the minimum length of the run-length encoded version of s after deleting at most k characters.
	*/
	fun getLengthOfOptimalCompression(s: String, k: Int): Int {
//		First, compress the string with run-length encoding, removing at most k characters. Then, return the length of the compressed string.

	}
}
