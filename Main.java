import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ComplexMatrix lastResult = null;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    doAdd();
                    break;
                case "2":
                    doMultiply();
                    break;
                case "3":
                    doDivide();
                    break;
                case "4":
                    doTranspose();
                    break;
                case "5":
                    doDeterminant();
                    break;
                case "6":
                    showLastResult();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Неверный пункт меню");
            }
        }
        System.out.println("Работа завершена");
    }

    static void printMenu() {
        System.out.println();
        System.out.println("Меню:");
        System.out.println("1 - сложить матрицы");
        System.out.println("2 - перемножить матрицы");
        System.out.println("3 - разделить матрицы");
        System.out.println("4 - транспонировать матрицу");
        System.out.println("5 - вычислить определитель");
        System.out.println("6 - показать последний результат");
        System.out.println("0 - выход");
        System.out.print("Выбор: ");
    }

    static void doAdd() {
        try {
            System.out.println("Введите первую матрицу");
            ComplexMatrix result = readMatrix();
            boolean more = true;
            while (more) {
                System.out.print("Прибавить ещё одну матрицу? (да/нет): ");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("да")) {
                    System.out.println("Введите следующую матрицу");
                    ComplexMatrix next = readMatrix();
                    result = result.add(next);
                } else {
                    more = false;
                }
            }
            lastResult = result;
            System.out.println("Результат сложения:");
            result.print();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    static void doMultiply() {
        try {
            System.out.println("Введите первую матрицу");
            ComplexMatrix result = readMatrix();
            boolean more = true;
            while (more) {
                System.out.print("Умножить ещё на одну матрицу? (да/нет): ");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("да")) {
                    System.out.println("Введите следующую матрицу");
                    ComplexMatrix next = readMatrix();
                    result = result.multiply(next);
                } else {
                    more = false;
                }
            }
            lastResult = result;
            System.out.println("Результат умножения:");
            result.print();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    static void doDivide() {
        try {
            System.out.println("Введите первую матрицу (делимое)");
            ComplexMatrix result = readMatrix();
            boolean more = true;
            while (more) {
                System.out.print("Разделить ещё на одну матрицу? (да/нет): ");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("да")) {
                    System.out.println("Введите матрицу делитель");
                    ComplexMatrix next = readMatrix();
                    result = result.divide(next);
                } else {
                    more = false;
                }
            }
            lastResult = result;
            System.out.println("Результат деления:");
            result.print();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    static void doTranspose() {
        try {
            System.out.println("Введите матрицу");
            ComplexMatrix matrix = readMatrix();
            ComplexMatrix result = matrix.transpose();
            lastResult = result;
            System.out.println("Транспонированная матрица:");
            result.print();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    static void doDeterminant() {
        try {
            System.out.println("Введите матрицу");
            ComplexMatrix matrix = readMatrix();
            Complex det = matrix.determinant();
            System.out.println("Определитель: " + det);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    static void showLastResult() {
        if (lastResult == null) {
            System.out.println("Результатов пока нет");
        } else {
            System.out.println("Последний результат:");
            lastResult.print();
        }
    }

    static ComplexMatrix readMatrix() {
        System.out.print("Введите источник (1 - ввести новую матрицу, 2 - взять последний результат): ");
        String src = scanner.nextLine().trim();
        if (src.equals("2")) {
            if (lastResult == null) {
                throw new IllegalStateException("Последнего результата ещё нет");
            }
            return lastResult;
        }
        System.out.print("Количество строк: ");
        int rows = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Количество столбцов: ");
        int cols = Integer.parseInt(scanner.nextLine().trim());
        ComplexMatrix matrix = new ComplexMatrix(rows, cols);
        System.out.println("Введите элементы построчно в формате: вещественная_часть мнимая_часть");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Элемент [" + i + "][" + j + "]: ");
                String line = scanner.nextLine().trim();
                String[] parts = line.split("\\s+");
                double re = Double.parseDouble(parts[0]);
                double im = parts.length > 1 ? Double.parseDouble(parts[1]) : 0;
                matrix.set(i, j, new Complex(re, im));
            }
        }
        return matrix;
    }
}
