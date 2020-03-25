package projectEuler.programs;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.min;

public final class p150 extends EulerSolution {
	private static final int ROWS = 1000;

	public static void main(String[] args) {
		System.out.println(new p150().run());
	}

	@NotNull String run() {
		// Generate the triangle
		LcgRandom rand = new LcgRandom();
		int[][] triangle = new int[ROWS][];
		for (int i = 0; i < triangle.length; i++) {
			triangle[i] = new int[i + 1];
			for (int j = 0; j <= i; j++) triangle[i][j] = rand.next();
		}
		// Calculate cumulative sums for each row
		int[][] rowSums = new int[triangle.length][];
		for (int i = 0; i < rowSums.length; i++) {
			rowSums[i] = new int[triangle[i].length + 1];
			rowSums[i][0] = 0;
			for (int j = 0; j <= i; j++) rowSums[i][j + 1] = rowSums[i][j] + triangle[i][j];
		}
		// Calculate minimum subtriangle sum for each apex position
		long minSum = 0;
		for (int i = 0; i < triangle.length; i++)
			for (int j = 0; j < triangle[i].length; j++) {
				// Apex element selected at triangle[i][j]
				long curSum = 0;
				for (int k = i; k < triangle.length; k++) { // Ending row (inclusive)
					curSum += rowSums[k][k - i + 1 + j] - rowSums[k][j];
					minSum = min(curSum, minSum);
				}
			}
		return Long.toString(minSum);
	}

	private static final class LcgRandom {
		private int state;

		@Contract(pure = true)
		LcgRandom() {
			state = 0;
		}

		int next() {
			state = ((615949 * state) + 797807) & (1048575);
			return state - (524288);
		}
	}
}