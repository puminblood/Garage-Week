import pygame
import time
import sys
 
# Initialiser pygame mixer
 
pygame.mixer.init()
 
# Charger le fichier audio
pygame.mixer.music.load(sys.argv[1])
 
# Jouer le fichier audio
pygame.mixer.music.play()
 
# Attendre que la musique se termine
while pygame.mixer.music.get_busy():
    time.sleep(1)