import java.awt.*;
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
    private int [] xPoints;
    private int [] yPoints;

    /**
     * gera um polígono aleatório e salva em polygon
     */
    private void iniciateRandomPolygon () {

        this.r = ThreadLocalRandom.current().nextInt(256);
        this.g = ThreadLocalRandom.current().nextInt(256);
        this.b = ThreadLocalRandom.current().nextInt(256);

        this.numVertices = ThreadLocalRandom.current().nextInt(3, maxVertices);

        this.xPoints = new int[this.numVertices];
        this.yPoints = new int[this.numVertices];

        for (int i = 0; i < this.numVertices; i++) {
            xPoints[i] = ThreadLocalRandom.current().nextInt(minX, maxX);
            yPoints[i] = ThreadLocalRandom.current().nextInt(minY, maxY);
        }

        polygon = new Polygon(xPoints, yPoints, numVertices);

    }

    /**
     * faz a mutação desse gene, que é trocar completamente o polígono dele
     */
    public void mutate () {
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

        this.xPoints = new int[this.numVertices];
        this.yPoints = new int[this.numVertices];

        for (int i = 0; i < another.getNumVertices(); i++) {

            this.xPoints[i] = another.getXPointsPosition(i);
            this.yPoints[i] = another.getYPointsPosition(i);

        }

        this.polygon = new Polygon(this.xPoints, this.yPoints, this.numVertices);
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
        return xPoints[i];
    }

    public int getYPointsPosition(int i) {
        return yPoints[i];
    }
}
