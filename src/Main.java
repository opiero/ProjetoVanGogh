import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main (String [] args) {

        /*System.out.printf("Tecle o nome do arquivo da imagem: ");

        Scanner input = new Scanner(System.in);
        String imageName = input.nextLine();*/

        BufferedImage image = null;

        try {
            image = ImageIO.read(new File("n.jpg"));
        } catch (IOException e) {
            System.out.println("Erro! Não pude encontrar a imagem");
        }

        GeneticAlgorithm GA = new GeneticAlgorithm(10, 50, 0.15, 500,
                0, image.getWidth(), 0, image.getHeight(), image);

        for (int i = 0; i < 500; i++) {

            GA.runGeneration();
            System.out.println("Executei a geracao " + GA.getCurrentGeneration());
            System.out.println("Fitness do melhor de todos: " + GA.getTheBestEverFitness());


        }

        BufferedImage result = GA.getTheBestEver().getPhenotype();

        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(result)));

        frame.setVisible(true);

    }

}
