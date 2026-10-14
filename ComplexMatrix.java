public class ComplexMatrix {
    int rows;
    int cols;
    Complex[][] data;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new Complex[rows][cols];
    }

    public void set(int r, int c, Complex value) {
        data[r][c] = value;
    }

    public Complex get(int r, int c) {
        return data[r][c];
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают, сложить нельзя");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j).add(other.get(i, j)));
            }
        }
        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Количество столбцов первой матрицы должно быть равно количеству строк второй");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.get(i, k).multiply(other.get(k, j)));
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        if (other.rows != other.cols) {
            throw new IllegalArgumentException("Делить можно только на квадратную матрицу");
        }
        return this.multiply(other.inverse());
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, this.get(i, j));
            }
        }
        return result;
    }

    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Определитель существует только у квадратной матрицы");
        }
        int n = rows;
        Complex[][] temp = new Complex[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = data[i][j];
            }
        }
        Complex det = new Complex(1, 0);
        for (int i = 0; i < n; i++) {
            int pivot = i;
            while (pivot < n && temp[pivot][i].re == 0 && temp[pivot][i].im == 0) {
                pivot++;
            }
            if (pivot == n) {
                return new Complex(0, 0);
            }
            if (pivot != i) {
                Complex[] tempRow = temp[pivot];
                temp[pivot] = temp[i];
                temp[i] = tempRow;
                det = det.multiply(new Complex(-1, 0));
            }
            det = det.multiply(temp[i][i]);
            for (int k = i + 1; k < n; k++) {
                Complex factor = temp[k][i].divide(temp[i][i]);
                for (int j = i; j < n; j++) {
                    temp[k][j] = temp[k][j].subtract(temp[i][j].multiply(factor));
                }
            }
        }
        return det;
    }

    public ComplexMatrix inverse() {
        Complex det = determinant();
        if (det.re == 0 && det.im == 0) {
            throw new ArithmeticException("Матрица вырождена, обратной матрицы не существует");
        }
        int n = rows;
        ComplexMatrix result = new ComplexMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Complex cofactor = minor(i, j).determinant();
                if ((i + j) % 2 != 0) {
                    cofactor = cofactor.multiply(new Complex(-1, 0));
                }
                result.set(j, i, cofactor.divide(det));
            }
        }
        return result;
    }

    private ComplexMatrix minor(int skipRow, int skipCol) {
        ComplexMatrix result = new ComplexMatrix(rows - 1, cols - 1);
        int ri = 0;
        for (int i = 0; i < rows; i++) {
            if (i == skipRow) {
                continue;
            }
            int rj = 0;
            for (int j = 0; j < cols; j++) {
                if (j == skipCol) {
                    continue;
                }
                result.set(ri, rj, this.get(i, j));
                rj++;
            }
            ri++;
        }
        return result;
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
