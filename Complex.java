public class Complex {
    double re;
    double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex add(Complex other) {
        return new Complex(this.re + other.re, this.im + other.im);
    }

    public Complex subtract(Complex other) {
        return new Complex(this.re - other.re, this.im - other.im);
    }

    public Complex multiply(Complex other) {
        double newRe = this.re * other.re - this.im * other.im;
        double newIm = this.re * other.im + this.im * other.re;
        return new Complex(newRe, newIm);
    }

    public Complex divide(Complex other) {
        double denom = other.re * other.re + other.im * other.im;
        if (denom == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        double newRe = (this.re * other.re + this.im * other.im) / denom;
        double newIm = (this.im * other.re - this.re * other.im) / denom;
        return new Complex(newRe, newIm);
    }

    public String toString() {
        if (im == 0) {
            return format(re);
        }
        if (re == 0) {
            return format(im) + "i";
        }
        if (im > 0) {
            return format(re) + "+" + format(im) + "i";
        }
        return format(re) + "-" + format(-im) + "i";
    }

    private String format(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
