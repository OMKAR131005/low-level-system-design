abstract class ModelTrainerSystem {

    // Template method — fixed algorithm skeleton, cannot be overridden
    final public void trainModel(String modelName, String path) {
        loadData(path);
        preprocessData();
        train();
        if (shouldEvaluate()) {   // hook — subclass can skip this step
            evaluate();
        }
        saveModel();
        System.out.println("---- " + modelName + " pipeline complete ----\n");
    }

    void loadData(String path) {
        System.out.println("[common] Loading data from fileSystem (.csv/.xlsx) from path: " + path);
    }

    void preprocessData() {
        System.out.println("[common] Data is being processed using common algo");
    }

    // no sensible default — force every subclass to define its own training logic
    abstract void train();

    void evaluate() {
        System.out.println("[common] Model is being evaluated using common method");
    }

    void saveModel() {
        System.out.println("[common] Model is saved");
    }

    // Hook: default = always evaluate. Subclass can override to skip this step.
    boolean shouldEvaluate() {
        return true;
    }
}

class DecisionTree extends ModelTrainerSystem {
    @Override
    void loadData(String path) {
        System.out.println("Loading data from fileSystem (.csv/.xlsx) from path: " + path);
    }

    @Override
    void preprocessData() {
        System.out.println("Data is being processed using Decision Tree algo");
    }

    @Override
    void train() {
        System.out.println("Model is being trained using Decision Tree training method");
    }
}

class Forest extends ModelTrainerSystem {
    @Override
    void loadData(String path) {
        System.out.println("Loading data from fileSystem (.csv/.xlsx) from path: " + path);
    }

    @Override
    void preprocessData() {
        System.out.println("Data is being processed using Forest algo");
    }

    @Override
    void train() {
        System.out.println("Model is being trained using Forest training method");
    }

    @Override
    void evaluate() {
        System.out.println("Model is being evaluated using Forest-specific cross-validation");
    }
}

// Example: a fast model that skips evaluation entirely (hook in action)
class QuickPrototype extends ModelTrainerSystem {
    @Override
    void train() {
        System.out.println("Model is being trained using a quick, low-accuracy method");
    }

    @Override
    boolean shouldEvaluate() {
        return false;   // skip evaluation for speed
    }
}

public class TemplateDesignPattern {
    public static void main(String[] args) {
        ModelTrainerSystem tree = new DecisionTree();
        tree.trainModel("DecisionTree", "model2.csv");

        ModelTrainerSystem forest = new Forest();
        forest.trainModel("Forest", "model3.csv");

        ModelTrainerSystem quick = new QuickPrototype();
        quick.trainModel("QuickPrototype", "model4.csv");
    }
}