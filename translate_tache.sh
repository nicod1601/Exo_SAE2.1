#!/bin/sh

# Usage : ./translate_taches.sh fichier.txt

input="$1"
tmpfile="$(mktemp)"

while IFS= read -r line; do
    nom=$(echo "$line" | cut -d'|' -f1)
    duree=$(echo "$line" | cut -d'|' -f2)
    reste=$(echo "$line" | cut -d'|' -f3-)
    echo "$nom|$duree| |$reste"
done < "$input" > "$tmpfile"

mv "$tmpfile" "$input"
