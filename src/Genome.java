import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Genome {

    private double fitness;

    //imagem que eu gero com o meu genoma
    private BufferedImage phenotype;

    //tamanho do meu genoma
    private int genomeSize;

    //conjunto de genes, o genoma propriamente dito
    ArrayList<Gene> genes;

    //chance de mutação
    private double mutationChance;

    //número máximo de vértices
    private int maxVertices;
    //limites de coordenadas
    private int minX, maxX;
    private int minY, maxY;

    /**
     * Construtor de cópia
     * @param another Genome do qual vou copiar
     */
    public Genome (Genome another) {

        this.fitness = another.getFitness();
        this.phenotype = another.getPhenotype();
        this.genomeSize = another.getGenomeSize();
        this.genes = another.getGenes();
        this.mutationChance = another.getMutationChance();
        this.maxVertices = another.getMaxVertices();
        this.minX = another.getMinX();
        this.maxX = another.getMaxX();
        this.minY = another.getMinY();
        this.maxY = another.getMaxY();

        this.phenotype = null;

    }

    /**
     * Construtor para iniciar um genoma aleatório
     * @param genomeSize tamanho do genoma, número de polígonos
     * @param mutationChance taxa de mutação
     * @param maxVertices número máximo de vértices
     * @param minX número mínimo de x
     * @param maxX número máximo de x
     * @param minY número mínimo de y
     * @param maxY número máximo de y
     */
    public Genome (int genomeSize, double mutationChance, int maxVertices, int minX, int maxX, int minY, int maxY) {

        this.genomeSize = genomeSize;
        genes = new ArrayList<Gene>();

        this.mutationChance = mutationChance;

        this.maxVertices = maxVertices;

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY= maxY;

        this.phenotype = null;


        for (int i = 0; i < genomeSize; i++)
            genes.add(new Gene(this.maxVertices, this.minX, this.maxX, this.minY, this.maxY));

    }

    /**
     * Construtor pra ser usado quando eu já tenho um arraylist com um conjunto de genes
     * @param genes conjunto de genes
     * @param mutationChance taxa de mutação
     * @param maxVertices número máximo de vértices
     * @param minX número mínimo de x
     * @param maxX número máximo de x
     * @param minY número mínimo de y
     * @param maxY número máximo de y
     */
    public Genome (ArrayList<Gene> genes, double mutationChance, int maxVertices, int minX, int maxX, int minY, int maxY) {

        this.mutationChance = mutationChance;

        this.maxVertices = maxVertices;

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY= maxY;

        this.genomeSize = genes.size();
        this.genes = genes;

        this.phenotype = null;


    }

    /**
     * retorna o gene de uma certa posição do genoma
     * @param pos posição do gene desejado
     * @return gene desejado
     */
    public Gene getGene (int pos) {
        return genes.get(pos);
    }

    /**
     * Faz o crossover.
     * Tô meio suspeito com essa função
     * @param anotherParent o outro pai com o objeto vou transar
     * @return o filho desse objeto com quem ele transou
     */
    public Genome crossover (Genome anotherParent) {

        //resutado do sexo
        ArrayList <Gene> babyGenome = new ArrayList<Gene>();

        //ponto de divisão. Antes desse ponto do vetor ele pegará o genoma de um pai
        //Após esse ponto, do outro
        int divisionPoint = ThreadLocalRandom.current().nextInt(this.genomeSize);

        for (int i = 0; i < divisionPoint; i++)
            babyGenome.add(this.getGene(i));

        for(int i = divisionPoint; i < this.genomeSize; i++)
            babyGenome.add(anotherParent.getGene(i));

        if (ThreadLocalRandom.current().nextFloat() < mutationChance) {
            System.out.println("ocorreu mutação!");
            babyGenome.get(ThreadLocalRandom.current().nextInt(this.genomeSize)).mutate();
        }


        return new Genome(babyGenome, this.mutationChance, this.maxVertices, this.minX, this.maxX, this.minY, this.maxY);

    }

    /**
     * eu me recuso a comentar o que essa merda faz
     * @return sua mãe
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * calcula o fitness desse objeto
     * @param target imagem que eu tenho como objetivo
     */
    public void calculateFitness(BufferedImage target) {

        generatePhenotype();

        final int width = maxX - minX;
        final int heigth = maxY - minY;

        this.fitness = 0;
        double sum = 0;

        for (int i = 0; i < width; i++) {

            for (int j = 0; j < heigth; j++) {

                int targetColor = target.getRGB(i, j);

                int targetRed = (targetColor & 0x00ff0000) >> 16;
                int  targetGreen = (targetColor & 0x0000ff00) >> 8;
                int targetBlue = targetColor & 0x000000ff;

                int myColor = phenotype.getRGB(i, j);

                int myRed = (myColor & 0x00ff0000) >> 16;
                int  myGreen = (myColor & 0x0000ff00) >> 8;
                int myBlue = myColor & 0x000000ff;



                sum += (targetRed-myRed)*(targetRed-myRed) +
                        (targetGreen-myGreen)*(targetGreen-myGreen) +
                        (targetBlue-myBlue)*(targetBlue-myBlue);

            }

        }

        System.out.println("A fitness desse deu " + 1/sum);

        this.fitness = 1/sum;

    }

    /**
     * retorna o a imagem que eu gero
     * @return imagem gerada pelos polígonos
     */
    public BufferedImage getPhenotype() {
        if (phenotype == null)
            generatePhenotype();
        return phenotype;
    }

    /**
     * Gera a imagem baseado no genoma
     */
    private void generatePhenotype() {

        final int width = maxX - minX;
        final int heigth = maxY - minY;

        this.phenotype = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_ARGB);

        Graphics g = phenotype.createGraphics();

        final int alpha = 127;

        for (int i = 0; i < genes.size(); i++) {

            int red = genes.get(i).getR();
            int green = genes.get(i).getG();
            int blue = genes.get(i).getB();

            Color c = new Color(red, green, blue, alpha);
            g.setColor(c);

            g.fillPolygon(genes.get(i).getPolygon());

        }

        g.dispose();


    }

    //------------------------------------------------métodos acessores

    public int getGenomeSize() {
        return genomeSize;
    }

    public ArrayList<Gene> getGenes() {
        return genes;
    }

    public double getMutationChance() {
        return mutationChance;
    }

    public int getMaxVertices() {
        return maxVertices;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }
}
