# EnvahissementArmorique 

## Le Projet : 

Notre projet de simulation d'envahissement armorique est fait en Java, utilise maven pour les dépendances et le build, et il intègre aussi une interface graphique via JavaFX.
Notre jeu, EnvahissementArmorique, permet de créer un grand nombre de zones, que ce soit des zones pour les Romains, les Gaulois, ou même un champ de bataille ou les différents camps peuvent se battre entre eux.

### Lancer le jeu : 
Pour lancer ce jeu via le terminal, il nous suffit donc de se positionner a la racine du projet, d'ouvrir un terminal à cet endroit, et d'effectuer les commandes qui suivent, mais uniquement si l'on a les bons prérequis.

#### Prérequis pour lancer le jeu : 
Pour lancer EnvahissementArmorique, il nous faut avoir ceci :
* JDK 17 ou plus
* Maven
Si apache maven n'est pas installé sur votre ordinateur, il suffit sous linux de taper "sudo apt install maven"


#### Lancement : 
Il faut tout d'abord clôner le répôt et se placer a la racine du projet.
Une fois que l'on a tous les prérequis, il nous faut donc pour lancer le projet :  <br>
##### 1 Un clean du maven : 
Avec la commande  ``` mvn clear ``` <br>
##### 2 La compilation du projet : 
Avec la commande ``` mvn compile ``` <br>
##### 3 Le lancement du jeu : 
Avec la commande ``` mvn exec:java -Dexec.mainClass="org.example.envahissementarmorique.app.ArmoricaApplication" ``` <br>


### Tests

Pour le lancement des tests dans le terminal, on tape <br>
``` mvn test ``` dans le terminal, et les test s'affichent.




