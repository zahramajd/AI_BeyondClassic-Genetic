package genetic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by zahra on 12/15/2016 AD.
 */
public class GA {


    int populationSize;
    ArrayList<Individual> parents = new ArrayList<>();
    ArrayList<Individual> individuals = new ArrayList<Individual>();
    Problem problem;
    int num_of_genarations;

    public GA(Problem problem, int populationSize, int num_of_genarations) {
        this.populationSize = populationSize;
        this.problem = problem;
        this.num_of_genarations = num_of_genarations;
    }

    public void algorithm() {

        make_population();

        int i = 0;
        double old = -1;

//        System.out.println("#\tVariance\t\t\t\t\tBest");
        System.out.println("#\tBest\t\t\t\tWorst\t\t\t\tAverage");


            while (i++ < this.num_of_genarations) {
                select_parent();
                crossover(0.01);
                select_population();

            double best = individuals.get(0).getFitness();
            double data = individuals.get(0).getDouble();

            if (best != old) {
                //System.out.println(i + "\t" + best + "\t" + data);
                System.out.println(i + "\t" + best + "\t" + data);
                old = best;
            }


//            System.out.println(i+"\t"+individuals.get(0).getFitness()+"\t"+individuals.get(this.populationSize-1).getFitness()+"\t"+average_fitness(individuals));
                if (problem.check_fit_enough(individuals.get(0)))
                    break;
            }

    }

    private void make_population() {
        for (int i = 0; i < populationSize; i++)
            individuals.add(this.problem.create());
    }


    private void select_parent() {
        parents.clear();
        double sum = 0;
        for (Individual i : individuals) {
            sum += i.getFitness();
        }

        for (Individual individual : individuals) {
            individual.probabilty = individual.getFitness() / sum;
        }

        for (int i = 0; i < 2; i++) {
            for (Individual x : individuals)
                x.weight = x.probabilty * Math.random();

            Collections.sort(individuals, (o1, o2) -> o1.weight.compareTo(o2.weight));
            Collections.reverse(individuals);

            parents.addAll(individuals.subList(0, this.populationSize / 2));
        }

    }

    private void crossover(double step) {
        for (int i = 0; i < parents.size() / 2; i++) {
            Individual child = problem.crossover(parents.get(i), parents.get(parents.size() - i - 1));
            problem.mutate(child, step);
            individuals.add(child);
        }
    }

    private void select_population() {
        Collections.sort(individuals, (o1, o2) -> o1.getFitness().compareTo(o2.getFitness()));
        Collections.reverse(individuals);

        ArrayList<Individual> x = new ArrayList<>();
        for (int i = 0; i < this.populationSize; i++)
            x.add(individuals.get(i));

        individuals = x;
    }

    private double average_fitness(ArrayList<Individual> individuals) {

        double sum = 0;
        for (Individual i : individuals) {
            sum += i.getFitness();
        }

        return sum / individuals.size();
    }
}
