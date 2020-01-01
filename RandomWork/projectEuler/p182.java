import org.jetbrains.annotations.NotNull;

import static java.lang.Integer.MAX_VALUE;

public final class p182 extends EulerSolution {
	private static final int P = 1009;
	private static final int Q = 3643;
	private static final int TOTIENT = (P - 1) * (Q - 1);

	public static void main(String[] args) {
		System.out.println(new p182().run());
	}

	@NotNull
	private static int[] countAllUnconcealed(int prime) {
		int[] numUnconcealed = new int[prime - 1];
		for (int e = 0; e < numUnconcealed.length; e++) {
			if (Library.gcd(e, prime - 1) == 1) numUnconcealed[e] = countUnconcealed(prime, e);
			else numUnconcealed[e] = MAX_VALUE;
		}
		return numUnconcealed;
	}

	private static int countUnconcealed(int modulus, int e) {
		int count = 0;
		for (int m = 0; m < modulus; m++) if (Library.powMod(m, e, modulus) == m) count++;
		return count;
	}

	@NotNull String run() {
		int[] numUnconcealedP = countAllUnconcealed(P);
		int[] numUnconcealedQ = countAllUnconcealed(Q);
		int minUnconcealedP = MAX_VALUE;
		for (int x : numUnconcealedP) minUnconcealedP = Math.min(x, minUnconcealedP);
		int minUnconcealedQ = MAX_VALUE;
		for (int x : numUnconcealedQ) minUnconcealedQ = Math.min(x, minUnconcealedQ);
		long sum = 0;
		for (int e = 0; e < TOTIENT; e++)
			if ((numUnconcealedP[e % (P - 1)] == minUnconcealedP) && (numUnconcealedQ[e % (Q - 1)] == minUnconcealedQ))
				sum += e;
		return Long.toString(sum);
	}
}