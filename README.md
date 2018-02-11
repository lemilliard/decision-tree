# DecisionTree

Librairie qui permet de générer et utiliser un arbre de décision

## Sommaire


## Maven Local Repository

Afin de faciliter l'utilisation de la librairie, il est fortement conseillé de la pousser
sur le repository maven local. Pour se faire, il suffit de faire comme suit:

```sh
mvn clean package

cd target

mvn install:install-file -Dfile=decisiontree-1.0-SNAPSHOT.jar -DgroupId=com.lemilliard -DartifactId=decisiontree -Dversion=1.0-SNAPSHOT -Dpackaging=jar
```

Ensuite, il faudra l'inclure dans le projet maven avec la dépendance suivante:

```xml
<dependencies>
    <!-- Other dependencies -->
    <dependency>
        <groupId>com.lemilliard</groupId>
        <artifactId>decisiontree</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```