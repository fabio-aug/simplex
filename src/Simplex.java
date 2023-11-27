import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Simplex {
    private boolean isMax;
    private int numVariables;
    private int numRestrictions;
    private float[][] matrix;
    private int[] base;

    public Simplex(String fileName) {
        readFile(fileName);
    }

    public void readFile(String fileName) {
        Scanner reader;
        try {
            reader = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado!");
            e.printStackTrace();
            return;
        }

        this.isMax = Integer.parseInt(reader.nextLine()) > 0;
        this.numVariables = Integer.parseInt(reader.nextLine());
        this.numRestrictions = Integer.parseInt(reader.nextLine());
        this.base = new int[this.numVariables + this.numRestrictions];
        for (int i = 0; i < this.base.length; i++)
            this.base[i] = (i < this.numVariables) ? 0 : 1;

        this.matrix = new float[this.numRestrictions + 1][this.numVariables + this.numRestrictions + 1];

        for (int i = 0; i < this.numRestrictions + 1; i++) {
            String data = reader.nextLine();
            String[] numeros = data.split(" ");

            for (int j = 0; j < this.matrix[i].length; j++) {
                if (j < this.numVariables) {
                    this.matrix[i][j] = Float.parseFloat(numeros[j]);
                    if (i == 0)
                        this.matrix[i][j] = this.matrix[i][j] * -1;
                } else {
                    if (i != 0)
                        this.matrix[i][this.numVariables + i - 1] = 1;
                    this.matrix[i][j] = 0;
                }
                if (i != 0)
                    this.matrix[i][this.matrix[i].length - 1] = Float.parseFloat(numeros[numeros.length - 1]);
            }
        }
        reader.close();
    }

    public int pickMinColumn() {
        int column = -1;
        float valor = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            if (isMax) {
                if (matrix[0][i] < 0 && i == 0) {
                    column = i;
                    valor = matrix[0][i];
                }

                if (column != -1 && matrix[0][i] < valor) {
                    column = i;
                    valor = matrix[0][i];
                }
            } else {
                if (matrix[0][i] < 0 && i == 0) {
                    column = i;
                    valor = matrix[0][i];
                }

                if (column != -1 && matrix[0][i] > valor && matrix[0][i] < 0) {
                    column = i;
                    valor = matrix[0][i];
                }
            }
        }
        return column;
    }

    @Override
    public String toString() {
        String print = "";
        for (int i = 0; i < this.matrix.length; i++) {
            print += Arrays.toString(this.matrix[i]) + "\n";
        }
        return print;
    }

    public static void main(String[] args) {
        Simplex simplex = new Simplex(args[0]);
        System.out.print(simplex);
    }
}
