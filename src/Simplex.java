import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Simplex {
    private boolean isMax;
    private int numVariables;
    private int numRestrictions;
    private float[][] matrix;
    private int lines;
    private int columns;

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
        this.lines = this.numRestrictions + 1;
        this.columns = this.numVariables + this.numRestrictions + 1;
        this.matrix = new float[this.lines][this.columns];

        for (int i = 0; i < this.lines; i++) {
            String data = reader.nextLine();
            String[] numbers = data.split(" ");

            for (int j = 0; j < this.columns; j++) {
                if (j < this.numVariables) {
                    this.matrix[i][j] = Float.parseFloat(numbers[j]);
                    if (i == 0 && this.matrix[i][j]>0)
                        this.matrix[i][j] = this.matrix[i][j] * -1;
                    else{
                        this.matrix[i][j] = this.matrix[i][j];
                    }
                } else {
                    if (i != 0)
                        this.matrix[i][this.numVariables + i - 1] = 1;
                    this.matrix[i][j] = 0;
                }
                if (i != 0)
                    this.matrix[i][this.matrix[i].length - 1] = Float.parseFloat(numbers[numbers.length - 1]);
            }
        }
        reader.close();
    }

    public int pickColumn() {
        int column = 0;
        float value = 0;
        for (int i = 0; i < this.columns - 1; i++) {
            if (i == 0) {
                column = i;
                value = matrix[0][i];
            }
            if (isMax) {
                if (matrix[0][i] < value && matrix[0][i] < 0) {
                    column = i;
                    value = matrix[0][i];
                }
            } else {
                if (matrix[0][i] > value && matrix[0][i] < 0) {
                    column = i;
                    value = matrix[0][i];
                }
            }
        }
        return (value < 0) ? column : -1;
    }

    public int pickRow(int column) {
        int row = 1;
        float value = (matrix[1][this.columns - 1] / matrix[1][column]);
        for (int i = 2; i < this.lines; i++) {
            float newValue = (matrix[i][this.columns - 1] / matrix[i][column]);
            if (value > newValue && newValue > 0) {
                value = newValue;
                row = i;
            }
        }
        return row;
    }

    public void solve() {
        int column = pickColumn();
        while (column != -1) {
            int line = pickRow(column);
            float pivotValue = matrix[line][column];
            for (int i = 0; i < this.columns; i++) {
                matrix[line][i] = matrix[line][i] / pivotValue;
            }

            for (int i = 0; i < this.lines; i++) {
                float firstElement = matrix[i][column];
                for (int j = 0; j < this.columns; j++) {
                    if (i != line) {
                        matrix[i][j] = matrix[i][j] - (firstElement * matrix[line][j]);
                    }
                }
            }
            column = pickColumn();
        }
    }

    public void showBase() {
        int aux = 1;
        String base = "";
        boolean isS = false;
        for (int i = 0; i < this.columns; i++) {
            if (this.matrix[0][i] == 0) {
                if (aux <= this.numVariables && !isS) {
                    base += "X" + aux + " ";
                    if (aux == this.numVariables) {
                        aux = 0;
                        isS = true;
                    }
                } else {
                    base += "S" + aux + " ";
                }
            }
            aux++;
        }
        System.out.println(base);
    }

    public void result() {
        showBase();
        System.out.println(this);
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
        simplex.solve();
        simplex.result();
    }
}
