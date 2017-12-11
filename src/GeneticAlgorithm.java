import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {

    //população
    private ArrayList<Genome> AlGenomes;

    //melhor de todos
    private Genome theBestEver;

    //fitness do melhor de todos
    private double theBestEverFitness;

    //atual geração
    private int currentGeneration;

    //tamanho da população
    private int populationSize;

    //tamanho do genoma (quantos polígonos essa joça vai ter)
    private int genomeSize;

    //taxa te mutação
    private double mutationRate;

    //numero maximo de vertices no poligono
    private int maxVertices;
    //dimensões maximas da imagem
    private int minX, maxX;
    private int minY, maxY;

    BufferedImage target;

    /**
     * Essa função aqui inicializa uma população full aleatória
     * Acho que tá funcionando direitinho
     */
    private void inicializeRandomPopulation () {

        AlGenomes = new ArrayList<Genome>();

        for (int i = 0; i < populationSize; i++)
            AlGenomes.add(new Genome(genomeSize, mutationRate, this.maxVertices,
                    this.minX, this.maxX, this.minY, this.maxY));

    }


    /**
     * Retorna todas as imagens dessa geração.
     * Nunca usei essa porra na minha vida.
     * @return todas as imagens dessa geração.
     */
    private ArrayList<BufferedImage> getPhenotypes() {

        ArrayList<BufferedImage> phenotypes = new ArrayList<BufferedImage>();

        for (int i = 0; i < this.AlGenomes.size(); i++){

            phenotypes.add(AlGenomes.get(i).getPhenotype());

        }



        return phenotypes;
    }

    /**
     * Torneio de dois mó simples
     * @param candidate1 primeiro candidato pra transar (deve ser escolhido aleatoriamente)
     * @param candidate2 segundo candidato pra transar (deve ser escolhido aleatoriamente)
     * @return candidato com maior fitness
     */
    private Genome tournamentOfTwo(Genome candidate1, Genome candidate2) {

       // System.out.println("fitness do 1: " + candidate1.getFitness());
        //System.out.println("fitness do 2: " + candidate2.getFitness());

        if (candidate1.getFitness() > candidate2.getFitness()) {
          //  System.out.println("Escolhi o 1");
            return candidate1;
        }
        else {
            //System.out.println("escolhi o 2");
            return candidate2;
        }

    }

    /**
     * Retorna um candidato aleatório da população
     * @return um candidato aleatório da população
     */
    private Genome getRandomCandidate() {

        return AlGenomes.get(ThreadLocalRandom.current().nextInt(AlGenomes.size()));

    }


    /**
     * Executa uma geração
     */
    public void runGeneration () {

        //calculando a fitness de todos os membros
        for (int i = 0; i < AlGenomes.size(); i++) {
            AlGenomes.get(i).calculateFitness(this.target);

            if (AlGenomes.get(i).getFitness() > theBestEverFitness) {

                theBestEverFitness = AlGenomes.get(i).getFitness();
                theBestEver = new Genome(AlGenomes.get(i), target);
            }

        }

        ArrayList<Genome> offspring = new ArrayList<Genome>();

        while (offspring.size() < this.populationSize - 1){

            Genome parent1 = null;
            Genome parent2 = null;

            do {

                parent1 = tournamentOfTwo(getRandomCandidate(), getRandomCandidate());
                parent2 = tournamentOfTwo(getRandomCandidate(), getRandomCandidate());


            } while (parent1 == parent2);

            Genome baby = parent1.crossover(parent2);

            offspring.add(baby);

        }

        offspring.add(new Genome(theBestEver, target));

        this.AlGenomes.clear();

        this.AlGenomes = offspring;

        this.currentGeneration++;



    }

    /**
     * Construtor
     * @param populationSize tamanho da população
     * @param genomeSize tamanho do genome (número de polígonos)
     * @param mutationRate taxa de mutação
     * @param maxVertices número máximo de vertices em cada polígono
     * @param minX coordenada mínima do x
     * @param maxX coordenada máxima do x
     * @param minY coordenada mínima do y
     * @param maxY coordenanda máxima no y
     * @param target imagem que quero gerar com os polígonos
     */
    public GeneticAlgorithm (int populationSize, int genomeSize, double mutationRate,
                             int maxVertices, int minX, int maxX, int minY, int maxY, BufferedImage target) {

        this.theBestEverFitness = 0;
        this.populationSize = populationSize;
        this.genomeSize = genomeSize;
        this.mutationRate = mutationRate;

        this.maxVertices = maxVertices;

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY= maxY;

        this.currentGeneration = 0;

        this.target = target;

        inicializeRandomPopulation();

    }

    //-------------------------------------métodos acessores

    public Genome getTheBestEver() {
        return theBestEver;
    }

    public double getTheBestEverFitness() {
        return theBestEverFitness;
    }

    public int getCurrentGeneration() {
        return currentGeneration;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getGenomeSize() {
        return genomeSize;
    }

    public double getMutationRate() {
        return mutationRate;
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

    public BufferedImage getTarget() {
        return target;
    }

    public void setTheBestEver(Genome theBestEver) {
        this.theBestEver = theBestEver;
    }

    public void setAlGenomes(ArrayList<Genome> alGenomes) {
        AlGenomes = alGenomes;
    }
}
