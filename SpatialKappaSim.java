import org.demonsoft.spatialkappa.model.KappaModel;
import org.demonsoft.spatialkappa.model.IKappaModel;
import org.demonsoft.spatialkappa.tools.TransitionMatchingSimulation;
import org.demonsoft.spatialkappa.tools.Simulation;
import org.demonsoft.spatialkappa.model.SimulationState;
import org.demonsoft.spatialkappa.model.Agent;
import org.demonsoft.spatialkappa.model.AgentDeclaration;
import org.demonsoft.spatialkappa.model.Observation;
import org.demonsoft.spatialkappa.model.Complex;

// import org.antlr.runtime.CharStream;
import org.demonsoft.spatialkappa.model.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpatialKappaSim
{
    private IKappaModel kappaModel;
    private Simulation simulation;

    public SpatialKappaSim() {
    }

    public boolean simulationLoaded() {
        return(simulation != null);
    }

    public void loadFile(String kappaFile) {
        File f = new File(kappaFile);
        try {
            kappaModel = Utils.createKappaModel(f);
            simulation = new TransitionMatchingSimulation(kappaModel);
        } catch (Exception e) {
            System.out.println("loadFile");
            System.out.println("ERROR");
        }
    }

    public void runByTime(float totalTime, float timePerStep) {
        simulation.runByTime(totalTime, timePerStep);
        Observation observation = simulation.getCurrentObservation();
        System.out.println(observation.toString());
        // This allows us to get the value of a particular observable
        System.out.println(observation.observables.get("Ca"));
    }

    public void runByTime2(float stepEndTime) {
        simulation.runByTime2(stepEndTime);
        Observation observation = simulation.getCurrentObservation();
        System.out.println(observation.toString());
        // This allows us to get the value of a particular observable
    }

    public double getObservation(String key) {
        Observation observation = simulation.getCurrentObservation();
        return(observation.observables.get(key).value);
    }

    public void printAgentNames() {
        List<String> agentNames = new ArrayList<String>(kappaModel.getAgentDeclarationMap().keySet());
        for(String agentName : agentNames) {
            System.out.println(agentName + " ");
        }
    }
    
    // value can be negative
    public void addAgent(String key, float value) {
        List<Agent> agents = new ArrayList<Agent>();
        SimulationState state = (SimulationState) simulation;                
        for (Complex complex : kappaModel.getFixedLocatedInitialValuesMap().keySet()) {
            for (Agent currentAgent : complex.agents) {
                System.out.println(currentAgent.name);
                if (key.equals(currentAgent.name)) {
                    System.out.println("ADD STUFF");
                    agents.add(currentAgent);
                    state.addComplexInstances(agents, (int)value);
                    agents.clear();
                }
            }
        }
    }

    public static void main(String[] args)
    {
        SpatialKappaSim sks = new SpatialKappaSim();
        sks.runByTime(10, 1);
        System.out.println("Hello, World!");
    }
}