#include <SoftwareSerial.h>

SoftwareSerial myBluetooth(2, 3); // RX, TX

void setup() {
  myBluetooth.begin(115200); // Initialise la communication Bluetooth à 9600 bauds
  Serial.begin(115200);      // Pour le débogage, affiche les données sur le moniteur série de l'IDE
}

void loop() {
  // Exemple d'envoi de données
  myBluetooth.println("Hello from Arduino!");

  // Pour le débogage, affiche aussi sur le moniteur série de l'IDE Arduino
  Serial.println("Message sent: Hello from Arduino!");

  delay(2000); // Attendre 2 secondes avant de renvoyer une donnée
}
