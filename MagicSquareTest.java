import magicsquare.MagicSquare;

public class MagicSquareTest {

	private static void testOddSquare(int n) {
		System.out.println("Odd Magic Square Test:");
		System.out.println(new MagicSquare(n).check());
		System.out.println(new MagicSquare(n * n).check());
		System.out.println(new MagicSquare(n + 10).check());
		System.out.println();
	}

	private static void testSinglyEvenSquare(int n) {
		System.out.println("Singly Even Magic Square Test:");
		System.out.println(new MagicSquare(n).check());
		System.out.println(new MagicSquare(n + 4).check());
		System.out.println(new MagicSquare((n * n) / 2).check());
		System.out.println();
	}

	private static void testDoublyEvenSquare(int n) {
		System.out.println("Doubly Even Magic Square Test:");
		System.out.println(new MagicSquare(n).check());
		System.out.println(new MagicSquare(n * n / 2).check());
		System.out.println(new MagicSquare(4 * n).check());
		System.out.println();
	}

	public static void main(String[] args) {
		testOddSquare(3);
		testDoublyEvenSquare(4);
		testSinglyEvenSquare(6);
	}

}
