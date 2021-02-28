# Bataille navale

## Remarques générales

Le mode multijoueur est pensé pour UNIX. L'effacement du terminal et les caractères gras risquent de ne pas fonctionner sous Windows.

## Questions Implémentées

Toutes les questions, bonus compris, ont été implémentées.

## Difficultés rencontrées

### Distinction entre les coordonnées de l'utilisateur et celles de l'ordinateur

Le choix du système de coordonnées n'était pas explicite dans la définition des fonctions. J'ai perdu pas mal de temps avant de faire l'effort de les définir correctement et ne plus avoir d'Exceptions pour de mauvaises coordonnées.

### Représentation (x,y) de la grille

Ayant choisi x et y différemment, j'ai inversé toutes les fonctions précodées utilisant les coordonnées (notamment dans util).

### Façon propre de commencer par le bon joueur / Coupure avant le coup de l'ordinateur

Pour ce faire, j'ai dû créer une nouvelle classe sérialisable retenant le numéro du joueur dont le tour est en cours. Lors du chargement de la sauvegarde, s'il s'agit du tour du joueur 2, celui-ci devient le joueur n°1. Toutefois, les coups en cours ne sont pas retenus. C'est-à-dire que le résumé du tour de l'adversaire ne sera pas complet si une coupure interromp l'adversaire. La sauvegarde de ces coups n'est aps particulièrement compliquée mais le temps me manque. A noter que la gestion des tours n'est pas effectuée avec l'ordinateur dont on suppose qu'il joue instantanément. Une partie commencée dans un des modes ne peut pas être reprise correctement dans un autre mode.

### Petite erreur de doublon de ships qui posait des problèmes de référence

Une petite erreur qui faisait que les ships de AIPlayer étaient déclarés deux fois m'a posé problème. J'ai dû utiliser le débugger pour la trouver.

## Utilisation

Pour utiliser ce projet, tapez les commandes suivantes dans la racine du projet.

### Initalement

mvn clean install

### Pour 1 joueur

mvn exec:java

### Pour 2 joueurs

mvn exec:java -Dexec.args=2p
