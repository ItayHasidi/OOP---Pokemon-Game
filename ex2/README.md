# Ex2

## Part 1 api:

We are building a directed weighted graph implementation with a number of features and classes.
 
We have 4 classes:
- NodeData
- EdgeData
- DWGraph_Algo
- DWGraph_DS

### NodeData:

+	getKey - id of the node.
+	getLocation - location of the node if he exists.
+	setLocation - you could change the location of the node.
+	getWeight - give you the "distance" between this node and his neighbor.
+	setWeight - can change the distance.
+	getInfo - for meta data.
+	setInfo - can change the metadata.
+	getTag - is for calculations made by the program.
+	setTag - change the tag.

### EdgeData:

+	getSrc - the first node of the edge=distance.
+	getDest - the second node of the edge.
+	getWeight - the distance of this edge(positive).
+	getInfo - meta data.
+	setInfo - change the meta data.
+	getTag - is for calculations made by the program.
	setTag - change the tag.
	

### DWGraph_DS:

+	getNode - give you the node id.
+	getEdge - give you the distance between src-dest.
+	addNode - add a new node if he doesn't exist.
+	connect - connects between src to dest with the edge but just to one way!
+	Collection <node_data> getV - give all the nodes in the graph.
+	Collection <edge_data> getE - give all the edge of a node that he is the src.
+	removeNode - delete the node.
+	removeEdge - delete all the edges between this node and the others nodes.
+	nodeSize - how many nodes are.
+	edgeSize - size of all edges.
+	getMC - count all add/delete nodes+edges.


### DWGraph_Algo:


+	init - start the graph with the algo.
+	getGraph - return graph.
+	copy - deep copy of the graph.
+	isConnected - checks if the graph is fully connected.
+	resetTag - reset the tag.
+	shortestPathDist - shorted path between src to dest.
+	shortestPath - give a list of the way from src to dest.
+	reverse graph - make all the edge turning over.
+	save - saves the whole graph on a file, including all nodes and all edges with their weight.
+   load - creates a new graph according to a save file.
+	toString - return graph by string.
	
## Part 2 gameClient:

+ Arena: This class handles with tha placement of all pokemon and setting the arena according to the current level played.
	     The arena updates the location of a new pokemon once one has been caught.


+ CL_Agent:  we set the agent, and he moves to the most valuable pokemon (by a calculation of distance and pokemon value), and catches it.


+ CL_Pokemon: the pokemon has: value - how many points it is worth, type - on which edge it is on,
	          pos - how to find on which edge he exists (x,y,z coordinates).

+ MyFrame: this is the drawing of the game - the size and color of all the pokemons, agents, nodes and edges.

+ Ex2: Using all the other classes, we built this class so that it would be the best way to get most point in minimal moves of the agents.
	
	+ pokeVal - value of the pokemon.
	+ getKey - key of the pokemon.
	+ setKey - set the pokemon.
	+ compareTo - compare the value of the pokemons.
	+ run - build the arena pokemons and agents, and then we start the game.
	+ moveAgents - get the agents to the next nodes.
	+ nextNode - see to which node the agents will move, and give the agent a new current edge to move on.
	+ setNextPok - here we designate each agent with his own pokemon so that no two agents will go to the same pokemon.
	+ bestStart - Here we make an ordered list of the most valuable pokemon, so to know where it is best to set our agents at the start of the game.


