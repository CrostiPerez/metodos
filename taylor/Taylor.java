import java.util.Random;
import java.util.Scanner;
import java.math.BigDecimal;

public class Taylor {

  //Definición de la función factorial
  public static double factorial (double num) {
    double factorial = 1;
    for (double i = num; i > 1; i--) {
      factorial *= i;
    }
    return factorial;
  }
  //Asignación factorial al arreglo
  public static double[] nFactorial (double num) {
    double factoriales[] = new double[(int)num];
    for (double i = num; i >= 0; i--) {
      factoriales[(int)i] = factorial(i);
    }
    return factoriales;
  }
  //Función para obtener los coeficientes mediante la derivada
  public static double[] coefSen (int num, double c) {
    double coeficientes[] = new double[num];
    for (int i = 0; i < num; i++) {
      switch ((i + 1) % 4) {
        case 1: coeficientes[i] = Math.sin(c)/factorial((double)i);
          break;
        case 2: coeficientes[i] = Math.cos(c)/factorial((double)i);
          break;
        case 3: coeficientes[i] = (-1 * Math.sin(c))/factorial((double)i);
          break;
        case 0: coeficientes[i] = (-1 * Math.cos(c))/factorial((double)i);
          break;
      }
    }
    return coeficientes;
  }
  //Definición de la función de Horner
  public static double horner (double polinomio[], double x, double c) {
    int grado = polinomio.length - 1;
    double suma = polinomio[grado];
    for (int i = grado - 1; i >= 0; i--)
      suma = ((x - c) * suma) + polinomio[i];
    return suma;
  }
  //Implementación del método falsa posición
  public static double falsaPosicion (double polinomio[], double c, double tolerancia) {
    double f0, ff, fr, error;
    double x0 = 3, xf = 3.5, xr = 0, xra;
    do {                                                //While para manejar la tolerancia mediante el error relativo
      xra = xr;
      f0 = horner(polinomio, x0, c);                    //Se evalua f(x) en x0 con la función de Horner
      ff = horner(polinomio, xf, c);                    //Se evalua f(x) en xf con la función de Horner
      xr = xf - ((ff * (x0 - xf)) / (f0 - ff));         //Se aplica la fórmula del método de falsa posición
      fr = horner(polinomio, xr, c);                    //Se evalua f(x) en xr con la función de Horner
      error = Math.abs( ( (xr - xra) / xr ) * 100 );    //Cálculo del error relativo
      if (fr * f0 < 0)                                  //Condición para el método de falsa posición
        xf = xr;
      else if (fr * f0 > 0)
        x0 = xr;
    } while(error >= tolerancia);                       
    return xr;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Calcula hasta 16 decimales de PI");
    System.out.print("Centrar la serie en: ");
    double c = scan.nextDouble();

    System.out.print("Sumandos de la serie: ");
    int sumandos = scan.nextInt();

    System.out.print("Cifras significativas: ");
    int cifras = scan.nextInt();
    double tolerancia = 0.5 * Math.pow(10, (2-cifras));    //Cálculo de la tolerancia

    double error, piaprox;
    double[] polinomio;
    do {                                                    //do-while para manejar la toleriancia mediante el valor real de pi
                                                            //Si no se cumple, se irán agregando sumandos al polinomio 
      polinomio = coefSen(sumandos, c);                           //Cargar el polinomio con sus coeficientes
      piaprox = falsaPosicion(polinomio, c, tolerancia);          //Cálculo de pi mediante el método de falsa posición
      error = Math.abs(((Math.PI - piaprox) / Math.PI) * 100);    //Cálculo del error real de pi
      System.out.println("Sumandos");
      for (int i = 0; i < polinomio.length; i ++)
      System.out.println("\t" + polinomio[i] + "\tx^" + i);
      System.out.println("Error para " + polinomio.length + " sumandos: " + error + "\n");
      sumandos ++;                                                //Se agrega un sumando al polinomio 
    } while (error >= tolerancia);
    System.out.print("\t\t\t1 ");
    for (int i = 2; i <= 9; i ++) System.out.print(i);
    for (int i = 0; i <= 8; i ++) System.out.print(i);
    System.out.println("\nValor aprox. de PI:\t" + String.format("%.17f",piaprox));
    System.out.println("Valor real de PI:\t" + String.format("%.17f",Math.PI));
  }
}


//Centrada en 14, necesita al menos 28 sumandos para que arroje un resultado
//Centrada en 12, necesita al menos 23 sumandos para que arroje un resultado
//Centrada en 10, necesita al menos 18 sumandos para que arroje un resultado
//Centrada en 8, necesita al menos 13 sumandos para que arroje un resultado
//Centrada en 6, necesita al menos 7 sumandos para que arroje un resultado

//Centrada en 15 necesita 51 sumandos para 12 decimales
//Centrada en 14 necesita 49 sumandos para 13 decimales
//Centrada en 13 necesita 48 sumandos para 14 decimales
//Centrada en 12 necesita 45 sumandos para 15 decimales
//Centrada en 11 necesita 41 sumandos para 13 decimales
//Centrada en 10 necesita 40 sumandos para 14 decimales
//Centrada en 9 necesita 36 sumandos para 15 decimales
//Centrada en 8 necesita 33 sumandos para 15 decimales
//Centrada en 7 necesita 31 sumandos para 16 decimales
//Centrada en 6 necesita 27 sumandos para 16 decimales
//Centrada en 5 necesita 23 sumandos para 16 decimales
//Centrada en 4 necesita 17 sumandos para 16 decimales
//Centrada en 3 necesita 11 sumandos para 16 decimales
//Centrada en 2 necesita 19 sumandos para 16 decimales
//Centrada en 1 necesita 24 sumandos para 16 decimales
//Centrada en 0 necesita 28 sumandos para 16 decimales
