<table>
 <tr style="text-align: center">
  <td>License</td>
  <td>Master</td>
  <td>Version</td>
 </tr>
 <tr>
  <td>
   <a href="https://gitlab.com/lemilliard/DecisionTree/blob/master/LICENSE">
    <img src="https://img.shields.io/badge/License-MIT-yellow.svg"/>
   </a>
  </td>
  <td>
   <a href="https://gitlab.com/lemilliard/DecisionTree/commits/master">
    <img alt="master status" src="https://gitlab.com/lemilliard/DecisionTree/badges/master/pipeline.svg"/>
   </a>
  </td>
  <td>
   <a href='https://bintray.com/lemilliard/maven/DecisionTree/_latestVersion'>
    <img src='https://api.bintray.com/packages/lemilliard/maven/DecisionTree/images/download.svg'>
   </a>
  </td>
 </tr>
</table>

# DecisionTree

[![pipeline status](https://gitlab.com/LeMilliard/DecisionTree/badges/master/pipeline.svg)](https://gitlab.com/LeMilliard/DecisionTree/commits/master)

Librairie qui permet de générer et utiliser un arbre de décision, basé sur l'algorithme ID3.

### Sommaire

* [Configuration](#configuration)
  * [Configuration d'une version en cours de développement](#configuration-dune-version-en-cours-de-développement)
* [Utilisation](#utilisation)

### Configuration

Avant toute chose, il est conseillé de consulter le fichier [LEMILLIARD.md](LEMILLIARD.md)

Veillez à bien utiliser Maven comme gestionnaire de dépendances.

Il vous suffit alors d'ajouter la dépendance suivante à votre `pom.xml`:

```xml
<dependencies>
    <!-- Other dependencies -->
    <dependency>
        <groupId>com.lemilliard</groupId>
        <artifactId>decisiontree</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```

Remplacez `${version}` par le tag de la version désirée.

##### Configuration d'une version en cours de développement

Afin d'utiliser la dernière version de cette librairie, 

```sh
mvn install
```

### Utilisation

// TODO