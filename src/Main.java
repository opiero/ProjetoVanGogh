import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main (String [] args) throws Exception {

        System.out.printf("Tecle o nome do arquivo da imagem: ");

        Scanner input = new Scanner(System.in);
        String imageName = input.nextLine();

        System.out.printf("Quantas gerações gostaria de rodar? ");

        int numGenerations = input.nextInt();

        /*System.out.printf("Há algum fenótipo inicial?(y/n) ");

        String yesOrNo = input.nextLine();

        String starterName = null;

        if (yesOrNo == "y") {

            System.out.printf("Qual? ");
            starterName = input.nextLine();

        }*/

        BufferedImage image = null;


        image = ImageIO.read(new File(imageName));

        GeneticAlgorithm GA = new GeneticAlgorithm(3, 100, 0.7, 15,
                0, image.getWidth(), 0, image.getHeight(), image);

        for (int i = 0; i < numGenerations; i++) {

            System.out.println("Geração " + GA.getCurrentGeneration() + " de " + numGenerations);
            GA.runGeneration();
            System.out.println("Fitness do melhor de todos: " + GA.getTheBestEverFitness());

            if (i % 100 == 0){
                File output = new File (imageName + i + ".jpg");

                ImageIO.write(GA.getTheBestEver().getPhenotype(), "jpg", output);

            }


        }

        BufferedImage result = GA.getTheBestEver().getPhenotype();

        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(result)));

        frame.setVisible(true);

    }

}
