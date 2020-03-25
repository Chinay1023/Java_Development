package projectEuler.programs;

import org.jetbrains.annotations.NotNull;

import static java.util.Arrays.sort;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;


public final class p032 extends EulerSolution {
	public static void main(String[] args) {
		System.out.println(new p032().run());
	}

	private static boolean hasPandigitalProduct(int n) {
		// Find and examine all factors of n
		return rangeClosed(1, n).anyMatch(i -> ((n % i) == 0) && isPandigital("" + n + i + n / i));
	}

	private static boolean isPandigital(@NotNull String s) {
		if (s.length() != 9) return false;
		char[] temp = s.toCharArray();
		sort(temp);
		return new String(temp).equals("123456789");
	}

	/*
	 * For contradiction suppose a candidate (x, y, z) has z >= 10000.
	 * Then x*y consumes at least 5 digits. With the 4 (or fewer)
	 * remaining digits, even the upper bound of x=99 and y=99
	 * produces a product of x*y < 10000, which is unequal to z.
	 *
	 * Therefore we need the product z < 10000 to be able to find
	 * possible x and y values.
	 */
	@NotNull String run() {
		int sum = range(1, 10000).filter(p032::hasPandigitalProduct).sum();
		return Integer.toString(sum);
	}
}