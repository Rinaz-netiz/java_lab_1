#!/bin/bash
mkdir -p bin
javac -d bin -sourcepath src src/Main.java
echo "Compilation completed!"