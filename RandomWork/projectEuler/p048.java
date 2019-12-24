import java.math.BigInteger;

48

public final class p048 extends EulerSolution {
	public static void main(String[] args) {
		System.out.println(new p048().run());
	}

	String run() {
		BigInteger modulus = BigInteger.TEN.pow(10);
		BigInteger sum = BigInteger.ZERO;
		for (int i = 1; i <= 1000; i++)
			sum = sum.add(BigInteger.valueOf(i).modPow(BigInteger.valueOf(i), modulus));
		return sum.mod(modulus).toString();
	}
}