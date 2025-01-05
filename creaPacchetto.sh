#!/bin/bash

# ATTENZIONE: 
# - questo script cancella e poi ricrea la cartella target
# - i binari (classi, immagini, ...) devono essere nella cartella bin
#
# questo script è stato riadattato da un altro che usava Maven
# per questo alcuni nomi di direcotory o impostazioni potrebbero
# risultare ridondanti
#
# linux: bisogna aver installato binutils
# macOS: devono esserci i file per firmare le applicazioni
# windows: serve avere installato 7zip, gitbash
#
# - la variabile JAVA_HOME deve essere impostata 
#   manualmente facendo una cosa del genere "export JAVA_HOME=/c/java/sdk" 
#   usando sdkman con qualcosa tipo "sdk use java 21.0.5.fx-librca"
#


if [ -z "$JAVA_HOME" ]; then
    echo "la variabile JAVA_HOME non è stata impostata"
    exit 0
fi

# comando per jpackage
JPACKAGE=$JAVA_HOME/bin/jpackage
# dove stanno i jar (uno solo in questo caso, lo crea questo script)
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
    ICONA=icone/icona.png
    TIPO_PACCHETTO="app-image"
    COMANDO="$JPACKAGE --name $NOME_APPLICAZIONE --app-version $VERSIONE --icon $ICONA --type $TIPO_PACCHETTO \
    --input $CARTELLA_LAVORO --dest $DESTINAZIONE \
    --add-modules javafx.controls,javafx.media,javafx.fxml,javafx.web,jdk.charsets \
    --main-class $CLASSE_PRINCIPALE --main-jar $JAR_PRINCIPALE"
    BUNDLE_NAME="$NOME_APPLICAZIONE-linux-$CPU.tgz"
else
    # https://github.com/gluonhq/scenebuilder/pull/358/files per l'opzione java.library.path
    # in alcune installazioni su windows pare che le librerie vengano altrimenti cercarte 
    # fuori dal pacchetto
    # NON sembrano servire: --java-options -Dsun.java2d.d3d=false --java-options -Dsun.java2d.noddraw=true
    ICONA=icone/icona.ico
    TIPO_PACCHETTO="app-image"
    COMANDO="$JPACKAGE --name $NOME_APPLICAZIONE --app-version $VERSIONE --icon $ICONA --type $TIPO_PACCHETTO \
    --input $CARTELLA_LAVORO --dest $DESTINAZIONE \
    --add-modules javafx.controls,javafx.media,javafx.fxml,javafx.web,jdk.charsets \
    --main-class $CLASSE_PRINCIPALE --main-jar $JAR_PRINCIPALE \
    --java-options \"-Djava.library.path=runtime\bin;runtime\lib\""
    BUNDLE_NAME="$NOME_APPLICAZIONE-win-$CPU.zip"
fi

echo "----- ambiente di lavoro -------------------------------------"
echo "JAVA_HOME        : $JAVA_HOME"
echo "JPACKAGE         : $JPACKAGE"
echo "OSTYPE           : $OSTYPE"
echo ""
echo "----- cartelle -----------------------------------------------"
echo "CARTELLA_JARS    : $CARTELLA_JARS"
echo "JAR_PRINCIPALE   : $JAR_PRINCIPALE"
echo "CLASSE_PRINCIPALE: $CLASSE_PRINCIPALE"
echo "CARTELLA_LAVORO  : $CARTELLA_LAVORO"
echo "DESTINAZIONE     : $DESTINAZIONE"
echo "TIPO_PACCHETTO   : $TIPO_PACCHETTO"
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
mkdir $CARTELLA_JARS
mkdir $CARTELLA_LAVORO
jar --create --file $CARTELLA_JARS/lovelace.jar -C bin it
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
