# Bataille navale

## Remarques générales

Mode multijoueur pensé pour UNIX. L'effacement du terminal et les caractères gras ne fonctionneront pas sous Windows.

## Questions Implémentées

Toutes les questions ont été implémentées

## Difficultés rencontrées

### Distinction entre les coordonnées de l'utilisateur et celles de l'ordinateur

### Représentation (x,y) de la grille

Inversion du code

### Façon propre de commencer par le bon joueur

### Petite erreur de doublon de ships qui posait des problèmes de référence

utilisation du débugger pour pour la trouver

## Utilisation

### Initalement

mvn clean install

### Pour 1 joueur

mvn exec:java

### Pour 2 joueurs

mvn exec:java -Dexec.args=2p
