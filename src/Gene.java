import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Gene {

    //polígono gerado por esse gene
    private Polygon polygon;

    //número máximo de vértices permitido
    private int maxVertices;

    //número de vertices desse polígono
    private int numVertices;

    //limites do plano cartesiano
    private int minX, maxX;
    private int minY, maxY;

    //O RGB (de cores) do polígono
    private int r, g ,b;

    //vetores com as coordenadas do polígono
    private ArrayList <Integer> xPoints;
    private ArrayList<Integer> yPoints;

    private int[] toArray (ArrayList<Integer> aL) {

        int[] integerArray = new int[aL.size()];

        for (int i = 0; i < aL.size(); i++) {
            integerArray[i] = aL.get(i);
        }

        return integerArray;
    }

    /**
     * gera um polígono aleatório e salva em polygon
     */
    private void iniciateRandomPolygon () {

        this.r = ThreadLocalRandom.current().nextInt(256);
        this.g = ThreadLocalRandom.current().nextInt(256);
        this.b = ThreadLocalRandom.current().nextInt(256);

        this.numVertices = ThreadLocalRandom.current().nextInt(3, maxVertices);

        this.xPoints = new ArrayList<Integer> ();
        this.yPoints = new ArrayList<Integer> ();

        for (int i = 0; i < this.numVertices; i++) {
            xPoints.add(ThreadLocalRandom.current().nextInt(minX, maxX));
            yPoints.add(ThreadLocalRandom.current().nextInt(minY, maxY));
        }

        polygon = new Polygon(toArray(this.xPoints), toArray(this.yPoints), numVertices);

    }

    /**
     * faz a mutação desse gene, que é trocar completamente o polígono dele
     */
    public void mutate () {

        double dice = ThreadLocalRandom.current().nextDouble();

        //mutação pra trocar a cor
        if (dice < 0.45) {

            dice = ThreadLocalRandom.current().nextDouble();

            if (dice < 0.33)
                this.r = ThreadLocalRandom.current().nextInt(256);
            else if (dice < 0.66)
                this.g = ThreadLocalRandom.current().nextInt(256);
            else
                this.b = ThreadLocalRandom.current().nextInt(256);


        }

        //mutação pra adicionar/remover um vértice
        else if (dice < 0.90) {

            dice = ThreadLocalRandom.current().nextDouble();

            if ( (dice < 0.5 && this.xPoints.size() > 3) ) {

                int removedIndex = ThreadLocalRandom.current().nextInt(this.xPoints.size());

                this.xPoints.remove(removedIndex);
                this.yPoints.remove(removedIndex);

                this.numVertices--;

            }

            else if (this.xPoints.size() < this.maxVertices) {

                this.xPoints.add(ThreadLocalRandom.current().nextInt(minX, maxX));
                this.yPoints.add(ThreadLocalRandom.current().nextInt(minY, maxY));

                this.numVertices++;


            }

            polygon = new Polygon(toArray(this.xPoints), toArray(this.yPoints), numVertices);

        }

        //mutação pra gerar um novo polígono
        else
            iniciateRandomPolygon();
    }

    public Gene(Gene another) {

        this.maxVertices = another.getMaxVertices();
        this.numVertices = another.getNumVertices();
        this.minX = another.getMinX();
        this.maxX = another.getMaxX();
        this.minY = another.getMinY();
        this.maxY = another.getMaxY();
        this.r = another.getR();
        this.g = another.getG();
        this.b = another.getB();

        this.xPoints = new ArrayList<Integer> ();
        this.yPoints = new ArrayList<Integer> ();

        for (int i = 0; i < another.getNumVertices(); i++) {

            xPoints.add(ThreadLocalRandom.current().nextInt(minX, maxX));
            yPoints.add(ThreadLocalRandom.current().nextInt(minY, maxY));

        }

        this.polygon = new Polygon(toArray(this.xPoints), toArray(this.yPoints), this.numVertices);
    }

    /**
     * Construtor. Já gera um polígono aleatório
     * @param maxVertices número máximo de vértices permitido
     * @param minX número mínimo de x
     * @param maxX número máximo de x
     * @param minY número mínimo de y
     * @param maxY número máximo de y
     */
    public Gene (int maxVertices,int minX, int maxX, int minY, int maxY) {

        this.maxVertices = maxVertices;

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY= maxY;

        iniciateRandomPolygon();


    }

    public boolean equals (Gene another) {

        int anotherNumVertices = another.getNumVertices();

        if (this.numVertices != anotherNumVertices)
            return false;

        for (int i = 0; i < this.numVertices; i++) {

            if (this.getXPointsPosition(i) != another.getXPointsPosition(i) ||
                    this.getYPointsPosition(i) != another.getYPointsPosition(i))
                return false;

        }

        return true;


    }

    //-------------------------------------------------------------métodos acessores

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public int getMaxVertices() {
        return maxVertices;
    }

    public void setMaxVertices(int maxVertices) {
        this.maxVertices = maxVertices;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getXPointsPosition(int i) {
        return xPoints.get(i);
    }

    public int getYPointsPosition(int i) {
        return yPoints.get(i);
    }
}
