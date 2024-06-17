import serial
import time

# Configuration du port série pour le module Bluetooth
ser = serial.Serial('COM9', 9600)
print("Connected to", ser.portstr)

try:
    total_bytes = 0  # Total des bytes reçus
    start_time = time.time()  # Heure de début pour le calcul de la vitesse

    with open("received.wav", "wb") as f:
        print("Receiving data...")
        while True:  # Boucle infinie pour recevoir les données
            if ser.in_waiting > 0:
                data = ser.read(ser.in_waiting)
                f.write(data)
                total_bytes += len(data)
                print(f"Received {total_bytes} bytes", end='\r')  # Affiche les bytes reçus et revient au début de la ligne

except KeyboardInterrupt:
    print("\nInterrupted by user")

finally:
    ser.close()
    elapsed_time = time.time() - start_time
    print(f"\nConnection closed. Total bytes received: {total_bytes}")
    if elapsed_time > 0:
        print(f"Average speed: {total_bytes / elapsed_time:.2f} bytes/second")
