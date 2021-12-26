package it.unicam.cs.pa2021.formulaUno;

import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;
import java.util.stream.Collectors;

public class GraphCircuit implements Circuit<Node>{

    /**
     * Coda per gestire le Macchine presenti sul Circuito.
     * L'ordine delle Macchine all'interno &grave; inverso rispetto a quello del movimento.
     */
    private List<Car<Node>> cars;

    final private GraphCircuit_DS circuit;

    private CircuitUpdateSupport<Node> circuitUpdateSupporter;

    private static final Logger logger = Logger.getLogger("it.unicam.cs.pa2021.formulaUno.GraphCircuit");

    private static final Formatter formatter = new SimpleFormatter();

    private static final ConsoleHandler consoleHandler = new ConsoleHandler();



    public GraphCircuit (GraphCircuit_DS circuit, List<Car<Node>> carsToAdd) throws IllegalArgumentException{
        if (circuit == null){
            logger.severe("Null circuit passed as argument");
            throw new NullPointerException("Null circuit is not valid");
        }
        logger.setLevel(Level.INFO);
        consoleHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler);
        this.circuit = circuit;
        cars = new ArrayList<>();
        for (Car<Node> aCar: carsToAdd) {
            if (cars.contains(aCar) || (aCar == null)){
                logger.severe("Wrong cars passed as argument");
                throw new IllegalArgumentException("Cars are not valid: multiple insertions or null values");
            }
            cars.add(aCar);
            logger.info("Car " + aCar.getName() + " added");
        }
        this.circuitUpdateSupporter = null;
    }

    @Override
    public Optional<Car<Node>> carAt(Node node) throws NullPointerException{
        Optional<Node> circuitNode = this.circuit.getNode(node);
        if (circuitNode.isEmpty()){
            logger.severe("Wrong Node passed as argument");
            throw new IllegalArgumentException("Node is not present on the Circuit" +
                                                " or the passed argument was null");
        }
        if (circuitNode.get().getCar() == null)
            return Optional.empty();
        else
            return Optional.of(circuitNode.get().getCar());
    }

    @Override
    public boolean didCarWin(Car<Node> car) throws NullPointerException{
        if (!this.cars.contains(car)){
            logger.severe("Wrong car passed as argument");
            throw new NullPointerException("Car is not present in the Circuit");
        }
        Node mainPosition = this.calculateMainPoint(car);
        List<Node> nodesToCheck = this.getNodesToCheck(car.getPosition(), mainPosition);
        if (!nodesToCheck.stream().parallel().filter(Node::isEndNode).findAny().isEmpty()){
            if (this.circuitUpdateSupporter != null)
                this.circuitUpdateSupporter.aCarWon();
            return true;
        }
        return false;
    }

    /**
     * Calcola il punto principale di un Macchina.
     */
    private Node calculateMainPoint(Car<Node> car){
        return car.calculateNextPosition();
    }

    /**
     *Racchiude i Nodi del movimento supposto che vanno controllati.
     */
    private LinkedList<Node> getNodesToCheck (Node carPosition, Node supposedMovement){
        double xStart = Math.min(carPosition.getCardinalPoint().getX(), supposedMovement.getCardinalPoint().getX());
        double xBound = Math.max(carPosition.getCardinalPoint().getX(), supposedMovement.getCardinalPoint().getX());
        double yStart = Math.min(carPosition.getCardinalPoint().getY(), supposedMovement.getCardinalPoint().getY());
        double yBound = Math.max(carPosition.getCardinalPoint().getY(), supposedMovement.getCardinalPoint().getY());
        LinkedList<Node> wantedNodes = new LinkedList<>();
        for (double x = xStart; x <= xBound; ++x){
            for (double y = yStart; y <= yBound; ++y){
                this.circuit.getNode(new BidimentionalCardinalPoint(x, y)).ifPresent(wantedNodes::add);
            }
        }
        return wantedNodes;
    }

    /**
     * Elimina dal tratto di pista analizzato tutti i nodi esterni al Circuito.
     * Se non sono presenti Nodi esterni lo spostamento &grave; sicuro.
     */
    private LinkedList<Node> getOutsideNodesAndRemoveThemFrom (LinkedList<Node> nodesToCheck){
        LinkedList<Node> outsideNodes = new LinkedList<>();
        outsideNodes.addAll(nodesToCheck.stream().parallel().filter(Node::isOutsideNode).collect(Collectors.toList()));
        nodesToCheck.removeAll(outsideNodes);
        return outsideNodes;
    }

    /**
     * Crea le coppie tra nodi esterni ed interni che identificano i bordi del Circuito.
     */
    private LinkedList<LinkedList<Node>> createCouples(LinkedList<Node> outsideNodes, LinkedList<Node> nodesToCheck) {
        LinkedList<LinkedList<Node>> couples = new LinkedList<>();
        for (Node outsideNode : outsideNodes) {
            //tra i vicini dell'outsideNode seleziono quelli contenuti nel tratto di pista controllato
            List<Node> neighbors = this.circuit.getNeighborNodes(outsideNode).
                                                get().
                                                stream().
                                                parallel().
                                                filter(nodesToCheck::contains).
                                                collect(Collectors.toList());
            this.selectInNeighbors(neighbors, outsideNode, couples);
        }
        return couples;
    }

    /**
     * Seleziono per creare le coppie solo i Nodi che hanno stesse ascisse oppure stesse ordinate
     * dei nodi esterni:
     * sono quelli in particolare che descrivono un tratto del bordo.
     */
    private void selectInNeighbors (List<Node> neighbors, Node outsideNode, LinkedList<LinkedList<Node>> couples) {
        for (Node insideNode : neighbors) {
            if (this.haveSameX(outsideNode, insideNode)) {
                LinkedList<Node> aCouple = new LinkedList<>();
                aCouple.add(outsideNode);
                aCouple.add(insideNode);
                couples.add(aCouple);
            }
            if (this.haveSameY(outsideNode, insideNode)) {
                LinkedList<Node> aCouple = new LinkedList<>();
                aCouple.add(outsideNode);
                aCouple.add(insideNode);
                couples.add(aCouple);
            }
        }
    }

    /**
     * Verifica che due nodi abbiano la stessa ascissa.
     */
    private boolean haveSameX (Node outsideNode, Node insideNode){
        return outsideNode.getCardinalPoint().getX() == insideNode.getCardinalPoint().getX();
    }

    /**
     * Verifica che due Nodi abbiano le stesse ordinate.
     */
    private boolean haveSameY (Node outsideNode, Node insideNode){
        return outsideNode.getCardinalPoint().getY() == insideNode.getCardinalPoint().getY();
    }

    /**
     * Trova il parametro "a" nella funzione che descrive la retta passante per i due punti
     * che individuano i nodi argomenti.
     */
    private double find_A_ofStraightLineBetween (Node node1, Node node2){
        return -(node2.getCardinalPoint().getY()-node1.getCardinalPoint().getY());
    }

    /**
     * Trova il parametro "b" nella funzione che descrive la retta passante per i due punti
     * che individuano i nodi argomenti.
     */
    private double find_B_ofStraightLineBetween (Node node1, Node node2){
        return node2.getCardinalPoint().getX() - node1.getCardinalPoint().getX();
    }

    /**
     * Trova il parametro "c" nella funzione che descrive la retta passante per i due punti
     * che individuano i nodi argomenti.
     */
    private double find_C_ofStraightLineBetween (Node node1, Node node2){
        return (-(node2.getCardinalPoint().getX()*node1.getCardinalPoint().getY()))+
                (node2.getCardinalPoint().getY()*node1.getCardinalPoint().getX());
    }

    /**
     * Una volta trovata la retta passante per le coordinate dei due nodi che delineano uno spostamento,
     * controlla che le rette (della forma x=k oppure y=k) che dividono i Nodi interni ed esterni e che rappresentano
     * i bordi, non siano intercettate dalla retta dello spostamento nel range che racchiude gli spigoli laterali
     * dei nodi che formano le coppie nel caso in cui essi siano uno sopra l'altro,
     * o in quello che racchiude gli spigoli sopra e sotto della coppia, nel caso in cui i suoi due nodi
     * siano uno affiancato all'altro.
     * @apiNote
     *          L'algoritmo si rifà alla formula della geometria analitica che individua una retta passante per
     *          due punti:
     *          (y - y') / (y''-y') = (x - x') / (x'' -x');
     *          sviluppata diventa --> (x'' - x')y -(y'' - y')x + ( -(x'' * y') + (y'' * x')) = 0;
     *                                      ^--- A       ^--- B                 ^--- C
     */
    private  boolean checkIfcouplesbordersNotIntersectMovement(Node carPosition, Node supposedMovement,  LinkedList<LinkedList<Node>> couplesInsideOutsideNodesThatDefineBorders) {
        double a = this.find_A_ofStraightLineBetween(carPosition, supposedMovement);
        double b = this.find_B_ofStraightLineBetween(carPosition, supposedMovement);
        double c = this.find_C_ofStraightLineBetween(carPosition, supposedMovement);
        double straigh_y;
        double straigh_x;
        boolean flag = true;
        for(LinkedList<Node> aCouple: couplesInsideOutsideNodesThatDefineBorders){
            if (flag) {
                Node outsideNode = aCouple.pollFirst();
                Node insideNode = aCouple.pollFirst();
                double[] negatedRange = new double[2];
                straigh_y = tryToFindY(outsideNode, insideNode, negatedRange);
                straigh_x = tryToFindX(outsideNode, insideNode, negatedRange);
                if (straigh_x == 0) {
                    double intersectionPoint_x = ((-b * straigh_y) + (-c)) / a;
                    flag = !((negatedRange[0] <= intersectionPoint_x) && (intersectionPoint_x <= negatedRange[1]));
                } else {
                    double intersectionPoint_y = ((-a * straigh_x) + (-c)) / b;
                    flag = !((negatedRange[0] <= intersectionPoint_y) && (intersectionPoint_y <= negatedRange[1]));
                }
            }
            else break;
        }
        return flag;
    }

    /**
     * Verifica che la retta del bordo sia del tipo y = K,
     * in tal caso trova anche il range effittivo del bordopista.
     * In caso negativo essegue l'assegnamento y = 0 per indicare che la retta
     * &grave; invece del tipo x = k.
     */
    private double tryToFindY(Node outsideNode, Node insideNode, double[] negatedRange) {
        if (this.circuit.getNodesAdjacentyRelationship(outsideNode, insideNode).
                equals(Optional.of(AdjacentLocation.TOP))) {
            negatedRange[0] = outsideNode.getCardinalPoint().getX()-0.5;
            negatedRange[1] = outsideNode.getCardinalPoint().getX()+0.5;
            return outsideNode.getCardinalPoint().getY() - 0.5;
        } else if (this.circuit.getNodesAdjacentyRelationship(outsideNode, insideNode).
                equals(Optional.of(AdjacentLocation.BOTTOM))) {
            negatedRange[0] = outsideNode.getCardinalPoint().getX()-0.5;
            negatedRange[1] = outsideNode.getCardinalPoint().getX()+0.5;
            return outsideNode.getCardinalPoint().getY() + 0.5;
        } else
            return 0;
    }

    /**
     * Verifica che la retta del bordo sia del tipo x = K,
     * in tal caso trova anche il range effittivo del bordopista.
     * In caso negativo essegue l'assegnamento x = 0 per indicare che la retta
     * &grave; invece del tipo y = k.
     */
    private double tryToFindX(Node outsideNode, Node insideNode, double[] negatedRange) {
        if (this.circuit.getNodesAdjacentyRelationship(outsideNode, insideNode).
                equals(Optional.of(AdjacentLocation.RIGHT))){
            negatedRange[0] = outsideNode.getCardinalPoint().getY()-0.5;
            negatedRange[1] = outsideNode.getCardinalPoint().getY()+0.5;
            return outsideNode.getCardinalPoint().getX()-0.5;
        } else if (this.circuit.getNodesAdjacentyRelationship(outsideNode, insideNode).
                equals(Optional.of(AdjacentLocation.LEFT))){
            negatedRange[0] = outsideNode.getCardinalPoint().getY()-0.5;
            negatedRange[1] = outsideNode.getCardinalPoint().getY()+0.5;
            return outsideNode.getCardinalPoint().getX()+0.5;
        } else
            return 0;
    }

    /**
     * Sequenza di passi per verificare che un supposto spostamento non tagli la pista
     * passando al di fuori del Circuito.
     */
    private boolean checkMovement (Car<Node> nodeCar, Node supposedMovement) {
        LinkedList<Node> nodesToCheck = getNodesToCheck(nodeCar.getPosition(), supposedMovement);
        LinkedList<Node> outsideNodes = getOutsideNodesAndRemoveThemFrom(nodesToCheck);
        if (outsideNodes.isEmpty())
            return true;
        LinkedList<LinkedList<Node>> couplesInsideOutsideNodesThatDefineBorders = createCouples (outsideNodes, nodesToCheck);
        return checkIfcouplesbordersNotIntersectMovement (nodeCar.getPosition(), supposedMovement, couplesInsideOutsideNodesThatDefineBorders);
    }

    @Override
    public Set<Node> possibleMovements(Car<Node> car) throws NullPointerException{
        if (!cars.contains(car) || car == null) {
            logger.severe("Wrong car passed as argument");
            throw new NullPointerException ("Car is not valid");
        }
        Node mainPoint = this.calculateMainPoint(car);
        Set<Node> possibleMovements = new HashSet<>();
        if (car.getPreviousPosition() == null){
            possibleMovements.add(mainPoint);
            return possibleMovements;
        }
        if (this.circuit.getNeighborNodes(mainPoint).isPresent()) {
            //Al Set di tutti i possibili nodi (nodo posizione principale e rispettivi nodi adiacenti),
            //escludo i nodi esterni e i nodi che tagliano la pista.
            possibleMovements.addAll(this.circuit.getNeighborNodes(mainPoint).get());
            possibleMovements =
            possibleMovements.stream().parallel().filter(node -> !(node.isOutsideNode())).filter((node -> this.checkMovement(car, node))).collect(Collectors.toSet());
        }
        return possibleMovements;
    }

    @Override
    public void updateCarPosition(Car<Node> car, Node node) throws NullPointerException, IllegalArgumentException{
        if (!cars.contains(car) || car == null)
            throw new NullPointerException ("Car is not valid");
        if (this.possibleMovements(car).isEmpty()){
            this.cars.remove(car);
            if (this.circuitUpdateSupporter != null)
                this.circuitUpdateSupporter.aCarWentOutorCrashed(car);
            logger.warning("Excursion for " + car.getName() + ": it is not able to continue the race!");
            return;
        }
        if (!this.possibleMovements(car).contains(node))
            throw new IllegalArgumentException("Impossible Movement");
        if (!this.positionIsFree(node)) {
            this.cars.remove(car);
            if (this.circuitUpdateSupporter != null)
                this.circuitUpdateSupporter.aCarWentOutorCrashed(car);
            logger.warning(car.getName() + " crashed to avoid Car " + this.carAt(node).get() + ":"
                            + " it is out of the game");
            return;
        }
        Node previousPosition = car.getPosition();
        car.setPosition(node);
        car.setPreviousPosition(previousPosition);
        node.setCar(car);
        car.getPreviousPosition().setCar(null);
        if (this.circuitUpdateSupporter != null)
            this.circuitUpdateSupporter.aCarMoved(car);
    }

    @Override
    public boolean carIsInRace(Car<Node> car) throws NullPointerException{
        if (car == null) {
            logger.severe("Null Car passed as argument");
            throw new NullPointerException("Null value passed as argument");
        }
        return this.cars.contains(car);
    }

    @Override
    public void setCircuitUpdateSupporter(CircuitUpdateListener<Node> circuitUpdateListener) {
        this.circuitUpdateSupporter = new CircuitUpdateSupport<>();
        this.circuitUpdateSupporter.setCircuitUpdateListener(circuitUpdateListener);
    }

    @Override
    public List<Car<Node>> getClassification(){
        ArrayList<Car<Node>> classification = new ArrayList<>();
        for (Car<Node> aCar: cars) {
            if (aCar.getPreviousPosition() == null){
                logger.info("Classification is not available at 1° turn");
                return null;
            }
            classification.add(aCar);
        }
        this.selectionSort(classification);
        return classification;
    }

    private void selectionSort(ArrayList<Car<Node>> classfication){
        for (int i = 0; i < classfication.size()-1; ++i) {
            int min = i;
            for (int j = i+1; j < classfication.size(); ++j){
                if (this.calculateRoute(classfication.get(min).getRoute()) >=
                        this.calculateRoute(classfication.get(j).getRoute())) {
                    if (this.calculateRoute(classfication.get(min).getRoute()) ==
                            this.calculateRoute(classfication.get(j).getRoute())) {
                        if (cars.indexOf(classfication.get(min)) >
                                cars.indexOf(classfication.get(j))) {
                            min = j;
                            this.scambia(classfication, min, j);
                        }
                    } else {
                        min = j;
                        this.scambia(classfication, min, j);
                    }
                }
            }
        }
    }

    private void scambia (ArrayList<Car<Node>> classification, int min, int j) {
        Car<Node> temp = classification.get(min);
        classification.set(min, classification.get(j));
        classification.set(j, temp);
    }

    private double calculateRoute(List<Node> carRoute){
        double route = 0;
        for (int i = 0; i < carRoute.size()-1; ++i){
            route += this.calculateDistanceBetweenTwoPoint(carRoute.get(i), carRoute.get(i+1));
        }
        return route;
    }

    private double calculateDistanceBetweenTwoPoint (Node node1, Node node2) {
        double squaredDiffence_X = Math.pow(node1.getCardinalPoint().getX() -
                node2.getCardinalPoint().getX(), 2);
        double squaredDiffence_Y = Math.pow(node1.getCardinalPoint().getY() -
                node2.getCardinalPoint().getY(), 2);
        return Math.sqrt(squaredDiffence_X+squaredDiffence_Y);
    }

}
