#!/bin/bash
 
nomFichier="../../BDD/SourcesSonores/liste.txt"
if [ ! -f "$nomFichier" ]; then
    exit 1
fi
 
declare -a lignes
echo "INITIALISATION . . ."
 
while IFS= read -r ligne || [[ -n "$ligne" ]]
do
    ligne=$(echo "$ligne" | tr -d '\r' | sed 's/[[:space:]]*$//')
    lignes+=("$ligne")
done < "$nomFichier"
 
for ligne in "${lignes[@]}"
do
    echo ""
    java TestSon $ligne
    py "../../Python/lecture.py" "$ligne"
    py "../../Python/envoiBluetoothVersArduino.py"
    py "../../Interface/interface.py"
done