import java.util.Random;

public class main {

    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;
    Population bestGeneration=new Population();
    int bestGenerationNumber;
    public static void main(String[] args) {

        Random rn = new Random();

        main supremacy = new main();

        //Initialize population
        supremacy.population.newPopulation(10);

        //Calculate fitness of each individual
        supremacy.population.calculateFitness();

        System.out.println("Generation: " + supremacy.generationCount + " Fittest: " + supremacy.population.fittest);

        //While population gets an individual with maximum fitness
        while (supremacy.population.fittest < 5 && supremacy.generationCount<500) {
            ++supremacy.generationCount;

            //Do selection
            supremacy.selection();

            //Do crossover
            supremacy.crossover();

            //Do mutation under a random probability
            if (rn.nextInt()%7 < 5) {
                supremacy.mutation();
            }

            //Add fittest offspring to population
            supremacy.addFittestOffspring();

            //Calculate new fitness value
            supremacy.population.calculateFitness();

            System.out.println("Generation: " + supremacy.generationCount + " Fittest: " + supremacy.population.fittest);
            if (supremacy.population.fittest> supremacy.bestGeneration.fittest) {
                supremacy.bestGeneration = supremacy.population;
                supremacy.bestGenerationNumber= supremacy.generationCount;
            }
        }
        if (supremacy.population.fittest == 5) {
            System.out.println("\nSolution found in generation " + supremacy.generationCount);
            System.out.println("Fitness: " + supremacy.population.getBetter().fitness);
            System.out.print("Genes: ");
            for (int i = 0; i < 5; i++) {
                System.out.print(supremacy.population.getBetter().genes[i]);
            }

        }
        else {
            System.out.println("\nNearest solution found in generation " + supremacy.bestGenerationNumber);
            System.out.println("Fitness: " + supremacy.bestGeneration.getBetter().fitness);
            System.out.print("Genes: ");
            for (int i = 0; i < 5; i++) {
                System.out.print(supremacy.bestGeneration.getBetter().genes[i]);
            }
        }
        System.out.println("");

    }

    //Selection
    void selection() {

        //Select the most fittest individual
        fittest = population.getBetter();

        //Select the second most fittest individual
        secondFittest = population.getSecondBest();
    }

    //Crossover
    void crossover() {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(population.individuals[0].geneLength);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;

        }

    }

    //Mutation
    void mutation() {
        Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        //Flip values at the mutation point
        if (fittest.genes[mutationPoint] == 0) {
            fittest.genes[mutationPoint] = 1;
        } else {
            fittest.genes[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        if (secondFittest.genes[mutationPoint] == 0) {
            secondFittest.genes[mutationPoint] = 1;
        } else {
            secondFittest.genes[mutationPoint] = 0;
        }
    }

    //Get fittest offspring
    Individual getFittestOffspring() {
        if (fittest.fitness > secondFittest.fitness) {
            return fittest;
        } else {
            return secondFittest;
        }
    }


    //Replace least fittest individual from most fittest offspring
    void addFittestOffspring() {

        //Update fitness values of offspring
        fittest.calcFitness();
        secondFittest.calcFitness();

        //Get index of least fit individual
        int leastFittestIndex = population.getWorstIndex();

        //Replace least fittest individual from most fittest offspring
        population.individuals[leastFittestIndex] = getFittestOffspring();
    }

}

