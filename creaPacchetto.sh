#!/bin/bash

# ATTENZIONE: questo script è stato riadattato da un altro che usava Maven
#             per questo alcuni nomi di direcotory o impostazioni potrebbero
#             risultare ridondanti
#
# linux: nessuna ric hiesta particolare
# macOS: devono esserci i file per firmare le applicazioni
# 
# - le icone sono nella cartella icone
# - su windows deve essere installato 7-Zip
#
# - la variabile JAVA_HOME deve essere impostata 
#   manualmente facendo una cosa del genere "export JAVA_HOME=/c/java/sdk" 
#   usando sdkman con qualcosa tipo "sdk use java 21.0.5.fx-librca"
#
# - va creata la cartella target con dentro le cartelle jars e lavoro
# - il file jar si può creare da Eclipse oppure
#   "jar --create --file target/jars/lovelace.jar -C bin it"


if [ -z "$JAVA_HOME" ]; then
    echo "la variabile JAVA_HOME non è stata impostata"
    exit 0
fi

# comando per jpackage
JPACKAGE=$JAVA_HOME/bin/jpackage
# dove stanno i jar (cartella impostata nel file pom.xml)
CARTELLA_JARS=target/jars
# cartella di lavoro
CARTELLA_LAVORO=target/lavoro
# dove mettere il file compilato
DESTINAZIONE=target
# nome applicazione
NOME_APPLICAZIONE=TheLovelaceGame
# versione
VERSIONE=1.0
# nome del jar principale (contiene anche il numero di versione)
JAR_PRINCIPALE="lovelace.jar"
# la classe che contiene il metodo main()
CLASSE_PRINCIPALE="it.edu.iisgubbio.lovelace.Menu"
# nome dell'archivio da creare
CPU=$(uname -m)
# nome icona, dipende dal sistema operativo
# windows meglio lasciarlo in else (perché?)
if [[ "$OSTYPE" == "darwin"*  ]]; then
    # icona per macOS
    ICONA=icone/icona.icns
    TIPO_PACCHETTO="dmg"
    COMANDO="$JPACKAGE --name $NOME_APPLICAZIONE --app-version $VERSIONE --icon $ICONA --type $TIPO_PACCHETTO \
    --input $CARTELLA_LAVORO --dest $DESTINAZIONE \
    --add-modules javafx.controls,javafx.media,javafx.fxml,javafx.web,jdk.charsets \
    --main-class $CLASSE_PRINCIPALE --main-jar $JAR_PRINCIPALE \
    --mac-package-name theLovelaceGame \
    --mac-sign \
    --mac-package-identifier it.edu.iisgubbio.lovelace"
    BUNDLE_NAME="$NOME_APPLICAZIONE-macOS-$CPU.dmg"
elif [[ "$OSTYPE" == "linux"* ]]; then
    # icona le Linux (l'unico normale visto il tipo del file!)
    ICONA=icone/icona.png
    TIPO_PACCHETTO="app-image"
    COMANDO="$JPACKAGE --name $NOME_APPLICAZIONE --app-version $VERSIONE --icon $ICONA --type $TIPO_PACCHETTO \
    --input $CARTELLA_LAVORO --dest $DESTINAZIONE \
    --add-modules javafx.controls,javafx.media,javafx.fxml,javafx.web,jdk.charsets \
    --main-class $CLASSE_PRINCIPALE --main-jar $JAR_PRINCIPALE"
    BUNDLE_NAME="$NOME_APPLICAZIONE-linux-$CPU.tgz"
else
    # icona per Windows
    ICONA=icone/icona.ico
    TIPO_PACCHETTO="app-image"
    COMANDO="$JPACKAGE --name $NOME_APPLICAZIONE --app-version $VERSIONE --icon $ICONA --type $TIPO_PACCHETTO \
    --input $CARTELLA_LAVORO --dest $DESTINAZIONE \
    --add-modules javafx.controls,javafx.media,javafx.fxml,javafx.web,jdk.charsets \
    --main-class $CLASSE_PRINCIPALE --main-jar $JAR_PRINCIPALE"
    BUNDLE_NAME="$NOME_APPLICAZIONE-win-$CPU.zip"
fi

echo "----- ambiente di lavoro -------------------------------------"
echo "JAVA_HOME        : $JAVA_HOME"
echo "JPACKAGE         : $JPACKAGE"
echo "MAVEN            : $(which mvn)"
echo "OSTYPE           : $OSTYPE"
echo ""
echo "----- cartelle -----------------------------------------------"
echo "CARTELLA_JARS  : $CARTELLA_JARS"
echo "JAR_PRINCIPALE : $JAR_PRINCIPALE"
echo "CARTELLA_LAVORO: $CARTELLA_LAVORO"
echo "DESTINAZIONE   : $DESTINAZIONE"
echo "TIPO_PACCHETTO : $TIPO_PACCHETTO"
echo ""
echo "----- artefatto -----------------------------------------------"
echo "ICONA          : $ICONA"
echo "VERSIONE       : $VERSIONE"
echo "COMANDO        : $COMANDO"
echo "BUNDLE_NAME    : $BUNDLE_NAME"
echo "DESTINAZIONE   : $DESTINAZIONE"
echo ""
echo "--------------------------------------------------------------"

rm -rf target
mkdir target
mkdir target/jars
jar --create --file $CARTELLA_JARS/lovelace.jar -C bin it
rm -rf $CARTELLA_LAVORO
rm -rf target/$NOME_APPLICAZIONE*
mkdir $CARTELLA_LAVORO
cp $CARTELLA_JARS/* $CARTELLA_LAVORO

$COMANDO

# piccoli aggiustamenti che dipendono dal sistema operativo
if [[ "$OSTYPE" == "darwin"*  ]]; then
    mv "target/$NOME_APPLICAZIONE-$VERSIONE.dmg" "target/$BUNDLE_NAME"
elif [[ "$OSTYPE" == "linux"* ]]; then
    cd target
    tar -cvzf $BUNDLE_NAME $NOME_APPLICAZIONE/
    cd ..
else
    cd target
    cd $NOME_APPLICAZIONE
    /c/Program\ Files/7-Zip/7z a -tzip ../$BUNDLE_NAME *
    cd ..
fi
