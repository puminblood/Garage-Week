import serial
import time

def lire_contenu_fichier(nom_fichier):
    with open(nom_fichier, 'r') as fichier:
        contenu = fichier.read().strip()
        return contenu

# Remplacez 'COM_PORT' par le port COM attribué à votre module Bluetooth
ser = serial.Serial('COM9', 9600, timeout=3)
time.sleep(2)  # Laissez le temps à la connexion de s'établir

# Lire la valeur du fichier et l'envoyer
valeur = lire_contenu_fichier("../../BDD/donnes/arduino.txt") #on rajoute un ../ car c'est le script bash qui va utiliser cette fonction
print("Valeur lue du fichier:", valeur)  # Affiche la valeur lue avant de l'envoyer

ser.write(valeur.encode())

ser.close()  # Fermez la connexion série
