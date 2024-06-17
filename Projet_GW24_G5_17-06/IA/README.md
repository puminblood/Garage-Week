[PROJET GARAGE WEEK 2024 ISEN GROUPE QUALI'SON]
À propos
[Ce projet permet d'analyser les spectres de sons émis par les machines industriels afin d'identifier les pannes éventuelles]

Table des matières
🪧 À propos
📦 Prérequis
🚀 Installation
🛠️ Utilisation
🤝 Contribution
🏗️ Construit avec
📞 Contact
⚒️ Architecture

Prérequis
- module tkinter
- module serial


Installation
- cd ./IA
- make 


Utilisation
- cd ./out (dossier des executables)
- ./script.sh (lancement de l'analyse des sons)
La modification des sons doit se faire dans sources sonores et dans les fichiers TestSon et Apprentissage

Contribution
- Alicia Gerard  
- Fanny Herault
- Elric Andres
- Lucas Gumuchian
- Quentin Faury
- Dorian Wollmann


Construit avec
- python3
- java
- bash


Contact
- alicia.gerard@isen.yncrea.fr
- fanny.herault@isen.yncrea.fr
- elric.andres@isen.yncrea.fr
- lucas.gumuchian@isen.yncrea.fr
- quentin.faury@isen.yncrea.fr
- dorian.wollmann@isen.yncrea.fr


Structure 
PROJET_GW24_G5_16-06
├── .idea
├── Arduino
├── IA
│   ├── .idea
│   ├── out
│   ├── src
│   ├── makefile
│   └── Projet_IA_N3-main.iml
├── Interface
│   ├── LOGS
│   │   └── LOGS.txt
│   └── interface.py
├── Modele_IA
│   ├── Neurone.txt
│   └── test.txt
├── Python
│   ├── envoiBluetoothVersArduino.py
│   ├── envoiSerialVersArduino.py
│   ├── receptionMessageBluetooth.py
│   └── receptionWavBluetooth.py
├── Resultat
│   └── Resultat.txt
├── SourcesSonores
│   ├── bruit
│   │   ├── SonBruite1.wav
│   │   ├── SonBruite2.wav
│   │   ├── ...
│   │   └── SonBruite23.wav
│   └── normal
│       ├── SonNormal1.wav
│       ├── SonNormal2.wav
│   │   ├── ...
│       └── SonNormal30.wav
└── README.md


Gestion des versions
https://github.com/puminblood/Garage-Week


