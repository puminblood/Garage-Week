import os
import serial
import time

def lire_contenu_fichier(nom_fichier):
    try:
        script_dir = os.path.dirname(__file__)
        chemin_complet = os.path.join(script_dir, nom_fichier)
        with open(chemin_complet, 'r') as fichier:
            contenu = fichier.read().strip()
            return contenu
    except Exception as e:
        return str(e)

# Configuration du port série
ser = serial.Serial('COM5', 9600)
time.sleep(2)  # Attendez que la connexion série soit établie

# Lire la valeur du fichier et l'envoyer
valeur = lire_contenu_fichier("Projet_GW24_G5\CompteRendu\Resultat.txt")
print(f"Valeur lue du fichier : '{valeur}'")  # Affiche la valeur avant de l'envoyer

# Envoi de la valeur
ser.write(valeur.encode())

# Fermeture du port série
ser.close()