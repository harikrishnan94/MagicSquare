package magicsquare;

public class MagicSquare {

	private final int size;
	private final long square[][];
	private String rep = "";
	private boolean filled = false, converted = false;
	private final long magicSum;

	public MagicSquare(int n) {
		if (n < 3)
			throw new IllegalArgumentException(
					"Size of a Magic square cannot be < 3 [Received size is "
							+ n + "]");
		size = n;
		square = new long[n][n];
		magicSum = (long) size * ((long) size * (long) size + 1) / 2;
	}

	public static boolean check(long square[][], int size) {
		long magicSum = (long) size * ((long) size * (long) size + 1) / 2;
		long sum_r = 0, sum_c = 0, sum_fd = 0;
		long prev_sum_r = 0, prev_sum_c = 0;
		for (int i = 0; i < size; i++) {
			if (sum_r != prev_sum_r || sum_c != prev_sum_c)
				return false;
			sum_r = sum_c = 0;
			for (int j = 0; j < size; j++) {
				sum_r += square[i][j];
				sum_c += square[j][i];
				if (i == j)
					sum_fd += square[i][j];
			}
			prev_sum_r = sum_r;
			prev_sum_c = sum_c;
		}
		if (magicSum != sum_r || magicSum != sum_c || magicSum != sum_fd)
			return false;
		return true;
	}

	public boolean check() {
		fill();
		long sum_r = 0, sum_c = 0, sum_fd = 0, sum_bd = 0;
		long prev_sum_r = 0, prev_sum_c = 0;
		for (int i = 0; i < size; i++) {
			if (sum_r != prev_sum_r || sum_c != prev_sum_c)
				return false;
			sum_r = sum_c = 0;
			for (int j = 0; j < size; j++) {
				sum_r += square[i][j];
				sum_c += square[j][i];
				if (i == j)
					sum_fd += square[i][j];
			}
			prev_sum_r = sum_r;
			prev_sum_c = sum_c;
		}
		for (int i = size - 1; i >= 0; i--)
			sum_bd += square[i][i];
		System.out.println("RowSum:" + sum_r + " ColSum:" + sum_c
				+ " ForwardDiagSum:" + sum_fd + " BackwardDiagSum:" + sum_bd);
		if (magicSum != sum_r || magicSum != sum_c || magicSum != sum_fd
				|| magicSum != sum_bd)
			return false;
		return true;
	}

	public static long[][] fill(long[][] square, int size) {
		return new MagicSquare(size).getFilledMatrix();
	}

	public void fill() {
		if (!filled) {
			filled = true;
			if (size % 2 == 1) {
				fillOdd();
				return;
			}
			if (size % 4 == 0) {
				fillDoublyEven();
				return;
			}
			fillSinglyEven();
		}
	}

	private void fillOdd() {
		fillOddUtility(0, 0, 0, size);
	}

	private void fillSinglyEven() {
		int sizeBy2 = size >> 1, sizeBy4 = size >> 2;
		int offset = fillOddUtility(0, 0, 0, sizeBy2);
		offset = fillOddUtility(offset - 1, sizeBy2, sizeBy2, sizeBy2);
		offset = fillOddUtility(offset - 1, 0, sizeBy2, sizeBy2);
		offset = fillOddUtility(offset - 1, sizeBy2, 0, sizeBy2);
		int k = 1;
		int i, j;
		for (i = 0, j = size - 1; k < ((size - 2) / 4); k++, i++, j--) {
			for (int r = 0; r < sizeBy2; r++) {
				long t = square[r][i];
				square[r][i] = square[r + sizeBy2][i];
				square[r + sizeBy2][i] = t;
				t = square[r + sizeBy2][j];
				square[r + sizeBy2][j] = square[r][j];
				square[r][j] = t;
			}
		}
		for (int r = 0; r < sizeBy2; r++) {
			if (r != size / 4) {
				long t = square[r][i];
				square[r][i] = square[r + sizeBy2][i];
				square[r + sizeBy2][i] = t;
			}
		}
		long t = square[sizeBy4][k];
		square[sizeBy4][k] = square[sizeBy4 + sizeBy2][k];
		square[sizeBy4 + sizeBy2][k] = t;
	}

	private void fillDoublyEven() {
		for (int i = 0, n = size >> 2; i < n; i++)
			for (int j = 0; j < n; j++)
				drawDiagonalPrimitive(i, j);
		for (int i = 0, numi = 1, numd = size * size; i < size; i++)
			for (int j = 0; j < size; j++) {
				square[i][j] = (square[i][j] > 0 ? numi : numd);
				numd--;
				numi++;
			}
	}

	private void drawDiagonalPrimitive(int m, int n) {
		int four_m = (m << 2), four_n = (n << 2);
		square[four_m + 0][four_n + 0] = 1;
		square[four_m + 0][four_n + 3] = 1;
		square[four_m + 1][four_n + 1] = 1;
		square[four_m + 1][four_n + 2] = 1;
		square[four_m + 2][four_n + 1] = 1;
		square[four_m + 2][four_n + 2] = 1;
		square[four_m + 3][four_n + 0] = 1;
		square[four_m + 3][four_n + 3] = 1;
	}

	private int fillOddUtility(int offset, int r, int c, int size) {
		int i = size / 2, j = size - 1, n = size * size, num = 1;
		while (num <= n) {
			if (i == -1 && j == size) {
				i = 0;
				j = size - 2;
			} else {
				if (i == -1)
					i = size - 1;
				if (j == size)
					j = 0;
			}
			if (square[r + i][c + j] > 0) {
				j -= 2;
				i++;
				continue;
			} else
				square[r + i][c + j] = offset + num++;
			i--;
			j++;
		}
		return offset + num;
	}

	public long getmagicSum() {
		return magicSum;
	}

	public long[][] getFilledMatrix() {
		fill();
		return square;
	}

	private static int numOfDigits(long num) {
		int digits = 0;
		while (num > 0) {
			digits++;
			num /= 10;
		}
		return digits;
	}

	public String toString() {
		if (converted)
			return rep;
		converted = true;
		fill();
		rep = "";
		long maxDigits = numOfDigits(size * size);
		for (int i = 0; i < size; i++) {
			rep += "{";
			for (int j = 0; j < size; j++) {
				rep = String.format(rep + " " + "%" + maxDigits + "d",
						square[i][j]);
			}
			rep += "}\n";
		}
		return rep;
	}
}
