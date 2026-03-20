# Graph-Based Recipes Recommendation System

A Java application that recommends recipes based on your available ingredients using a graph data structure and Breadth-First Search (BFS).

## Overview

This system models ingredients as nodes and their co-occurrence in recipes as edges. Given a set of ingredients you have on hand, it can:

- **Complete matches** – find recipes you can make right now with your available ingredients.
- **Partial matches** – find recipes that require only 1–2 extra ingredients, where each missing ingredient is reachable in the ingredient graph from at least one ingredient you already have.

## Project Structure

```
Graph-Based-Recipes-Recommendation-System/
├── src/
│   ├── Main.java                   # Entry point (reads user input, triggers recommendations)
│   ├── RecomendaReceitas.java      # Core recommendation logic
│   ├── Graph.java                  # Undirected graph data structure with BFS
│   ├── Edge.java                   # Weighted edge between two ingredient nodes
│   ├── RecomendaReceitasTest.java  # JUnit tests
│   ├── receitas.txt                # Main dataset (~350 recipes)
│   └── receitasteste.txt           # Smaller dataset used in tests (15 recipes)
└── TP2AED.iml                      # IntelliJ IDEA project file
```

## How It Works

### Graph Construction

When the application starts it reads `receitas.txt` and builds an undirected graph:

- Each **ingredient** becomes a **node**.
- For every recipe, an **edge** is created between every pair of ingredients in that recipe (complete sub-graph per recipe).
- Each edge stores the ID of the recipe that links those two ingredients.

### Recommendation Algorithm

**Complete recipes** (`encotraReceitascompletas`):  
A recipe is returned if its ingredient set is a subset of (or equal to) the available ingredients.

**Incomplete recipes** (`encotraReceitasincompletas`):  
A recipe is a candidate if:
1. It has at most **2 missing ingredients**.
2. Every missing ingredient is **reachable via BFS** from at least one available ingredient in the graph.

This graph-based reachability check enables the system to surface recipes where the missing items are closely related to what you already have.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- JUnit 4 (for running tests)

## Running the Application

### 1. Compile

```bash
javac -d bin src/Main.java src/Graph.java src/Edge.java src/RecomendaReceitas.java
```

### 2. Run

```bash
java -cp bin Main
```

You will be prompted to enter your available ingredients as a space-separated list:

```
Quais os ingredientes disponiveis?
butter cinnamon onion yeast lemon wheat chicken
```

### 3. Sample Output

```
Com esses ingrediente pode fazer: r013 [chicken, onion]
Com esses ingrediente pode fazer: r021 [butter, wheat]

Se comprar [cassava] pode fazer r006:[wheat, cassava, butter]
Se comprar [egg] pode fazer r018:[chicken, onion, egg]
```

The first block lists recipes you can make now. The second block lists recipes you could make by purchasing one or two additional items.

## Running the Tests

```bash
javac -cp junit-4.x.jar:bin src/RecomendaReceitasTest.java -d bin
java  -cp junit-4.x.jar:bin org.junit.runner.JUnitCore RecomendaReceitasTest
```

Tests use `receitasteste.txt` (15 recipes) and cover both complete-match and partial-match scenarios.

## Dataset Format

Both `receitas.txt` and `receitasteste.txt` use a simple CSV format:

```
<recipe_id>,<ingredient1>,<ingredient2>,...
```

Example:
```
r001,chicken,cinnamon,soy_sauce,onion,ginger
r010,banana
r042,butter,wheat,egg,sugar,vanilla
```

- Recipe IDs follow the pattern `r001`, `r002`, …
- Ingredient names are lowercase and use underscores in place of spaces (e.g. `soy_sauce`).

## Key Classes

| Class | Responsibility |
|---|---|
| `Main` | Reads ingredient list from stdin and calls recommendation methods |
| `RecomendaReceitas` | Loads recipes, builds the graph, implements recommendation logic |
| `Graph` | Adjacency-list graph with `addEdge` and `temCaminho` (BFS path check) |
| `Edge` | Stores source node, destination node, and the recipe ID that created the edge |

## IDE Setup (IntelliJ IDEA)

1. Open the project using `TP2AED.iml`.
2. Configure a JDK (File → Project Structure → SDKs).
3. Add JUnit 4 to the project libraries to compile the tests.
4. Run `Main` or `RecomendaReceitasTest` directly from the IDE.
