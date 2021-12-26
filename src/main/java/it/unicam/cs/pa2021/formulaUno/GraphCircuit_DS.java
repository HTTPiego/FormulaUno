package it.unicam.cs.pa2021.formulaUno;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Data Structure che gestisce un GraphCircuit.
 */
public class GraphCircuit_DS{

    private HashMap<Node, HashSet<Node>> circuitGraph;

    public GraphCircuit_DS(){
        this.circuitGraph = new HashMap<>();
    }

    public int getSize(){
        return this.circuitGraph.size();
    }

    public void clear(){
        this.circuitGraph.clear();
    }

    private void updateOldNodesNeighbors(Node oldNode,
                                         HashSet<Node> oldNodeNeighbors,
                                         Node nodeToInsert){
        if (nodeToInsert.getCardinalPoint().isNearTo(oldNode.getCardinalPoint()))
            oldNodeNeighbors.add(nodeToInsert);
    }

    private void setNewNodeNeightbors (Node oldNode,
                                       HashSet<Node> oldNodeNeighbors,
                                       Node newNode,
                                       HashSet<Node> newNodeNeighbors) {
        if (oldNodeNeighbors.contains(newNode))
            newNodeNeighbors.add(oldNode);
    }

    /**
     * Aggiunge un nodo al GraphCircuit_DS stabilendo le connessioni tra i nodi adiacenti se
     * non ancora contenuto nel GraphCircuit_DS.
     *
     * @param newNode nodo da inserire.
     * @return true se il nodo &grave; stato aggiunto, false altrimenti (argomento nullo o già presente).
     */
    public boolean addNode (Node newNode) {
        if (newNode == null)
            return false;
        if (this.circuitGraph.containsKey(newNode))
            return false;
        HashSet<Node> newNodeNeighbors = new HashSet<>();
        circuitGraph.put(newNode, newNodeNeighbors);
        this.circuitGraph.forEach((mapNode, mapNodeNeighbors) ->
                this.updateOldNodesNeighbors(mapNode, mapNodeNeighbors, newNode));
        this.circuitGraph.forEach((mapNode, mapNodeNeighbors) ->
                this.setNewNodeNeightbors(mapNode, mapNodeNeighbors, newNode, newNodeNeighbors));
        return true;
    }

    /**
     * Restituisce un Set con tutti i nodi presenti nel GraphCircuit_DS.
     *
     * @return il Set con tutti i nodi
     */
    public Set<Node> nodeSet(){
        return this.circuitGraph.keySet().stream().collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Restituisce il nodo richiesto se presente.
     *
     * @param node nodo richiesto.
     * @return restituisce il nodo richiesto,
     * Optional.empty() se non presente oppure se il nodo richiesto &grave; null.
     */
    public Optional<Node> getNode (Node node){
        return this.circuitGraph.keySet().
                parallelStream().
                filter((mapNode -> mapNode.equals(node))).
                findAny();
    }

    /**
     * Restituisce il nodo con le coordinate volute.
     *
     * @param cardinalPoint coordinate richieste.
     * @return restituisce il nodo con il punto cardinale richiesto,
     * Optional.empty() se non presente oppure se le coordinate sono null.
     */
    public Optional<Node> getNode (CardinalPoint cardinalPoint){
        return this.circuitGraph.keySet().
                parallelStream().
                filter((mapNode -> mapNode.getCardinalPoint().equals(cardinalPoint))).
                findAny();
    }

    /**
     * Restituisce un Set contenente i nodi adiacenti rispetto al nodo
     * passato come parametro.
     *
     * @param node  nodo di cui si vogliono conoscere i nodi adiacenti.
     * @return un Optional.of del set con i vicini del nodo parametro,
     * Optional.empty() se questo non &egrave; presente o il parametro &grave; nullo.
     */
    public Optional<Set<Node>> getNeighborNodes (Node node){
        if (this.circuitGraph.get(node) == null)
            return Optional.empty();
        else
            return Optional.of(this.circuitGraph.get(node));
    }

    /**
     * Restituisce la relazione di adiacenza tra due Nodi.
     *
     * @param nodeOfWantedPosition Nodo di cui si vuole sapere la posizione rispetto ad un altro.
     * @param aNode Nodo usato per il confronto.
     * @return la posizione del primo nodo rispetto al secondo argomento,
     *  se i nodi non sono adiacenti restituisce Optional.empty().
     * @throws NullPointerException se i Nodi non sono all'interno del circuito o sono nulli.
     */
    public Optional<AdjacentLocation> getNodesAdjacentyRelationship (Node nodeOfWantedPosition, Node aNode) throws NullPointerException{
        if (this.getNode(nodeOfWantedPosition).isEmpty() || this.getNode(aNode).isEmpty())
            throw new IllegalArgumentException("Nodes are not valid");
        return nodeOfWantedPosition.getCardinalPoint().getAdjacencyRelationship(aNode.getCardinalPoint());
    }

    /**
     * Elimina il collegamento da un nodo ad un altro in senso unico.
     *
     * @param fromNode nodo di partenza.
     * @param toNode nodo verso il quale si vuole eliminare il collegamento.
     * @return true se il collegamento &grave; stato eliminato,
     * false altrimenti.
     */
    public boolean deleteNearNodesLinking (Node fromNode, Node toNode){
        if (this.getNode(fromNode).isEmpty())
            return false;
        if (this.getNode(toNode).isEmpty())
            return false;
        if (!fromNode.getCardinalPoint().isNearTo(toNode.getCardinalPoint()))
            return false;
        this.getNeighborNodes(fromNode).get().remove(toNode);
        return true;
    }



}
