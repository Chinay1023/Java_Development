package projectEuler.programs;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class p039 extends EulerSolution {
	public static void main(String[] args) {
		System.out.println(new p039().run());
	}

	@Contract(pure = true)
	private static int countSolutions(int p) {
		int count = 0;
		for (int a = 1; a <= p; a++)
			for (int b = a; b <= p; b++) {
				int c = p - a - b;
				if ((b <= c) && (((a * a) + (b * b)) == (c * c))) count++;
			}
		return count;
	}

	@NotNull String run() {
		int maxPerimeter = 0;
		int maxTriangles = 0;
		for (int p = 1; p <= 1000; p++) {
			int triangles = countSolutions(p);
			if (triangles > maxTriangles) {
				maxTriangles = triangles;
				maxPerimeter = p;
			}
		}
		return Integer.toString(maxPerimeter);
	}
}