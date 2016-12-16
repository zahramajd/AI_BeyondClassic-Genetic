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

    public GA(Problem problem, int populationSize) {
        this.populationSize = populationSize;
        this.problem = problem;
    }

    public void algorithm() {
        make_population();

        int i = 0;

        double old = -1;

        System.out.println("#\tFitness\t\t\t\t\tData");

        while (i++ < 1000) {
            select_parent();
            crossover();
            select_population();

            double best = individuals.get(0).getFitness();
            double data = individuals.get(0).getDouble();

            if (best != old) {
                //System.out.println(i + "\t" + best + "\t" + data);
                System.out.println(i + "\t" + Math.round((best * 100) / 92967) + "%\t" + data);
                old = best;
            }
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

    private void crossover() {
        for (int i = 0; i < parents.size() / 2; i++) {
            Individual child = problem.crossover(parents.get(i), parents.get(parents.size() - i - 1));
            problem.mutate(child);
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
}
