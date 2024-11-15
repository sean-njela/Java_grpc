#!/usr/bin/bash

# Loop through all files with the .java extension
for file in *.java; do 
  # Check if there are any .java files
  if [[ -f $file ]]; then
    echo "File: $file" >> list.txt
    cat "$file" >> list.txt
    echo -e "\n-----------------\n" >> list.txt
  fi
done