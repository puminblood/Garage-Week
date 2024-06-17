#include <SoftwareSerial.h>

SoftwareSerial myBluetooth(2, 3); // RX, TX
const int ledPin = 8;

void setup() {
  pinMode(ledPin, OUTPUT);
  Serial.begin(9600);     // Initialise la communication série avec l'ordinateur via l'IDE Arduino
  myBluetooth.begin(9600); // Initialise la communication série avec le module Bluetooth

  Serial.println("Prêt à recevoir des données...");
}

void loop() {
  if (myBluetooth.available()) { // Vérifie si des données sont disponibles sur le port série Bluetooth
    char received = myBluetooth.read(); // Lit un caractère
    Serial.print("Reçu : "); // Affiche ce qui est reçu dans le moniteur série de l'IDE Arduino
    Serial.println(received);

    if (received == '1') {
      digitalWrite(ledPin, HIGH);
      Serial.println("LED allumée");
      delay(2000);
      digitalWrite(ledPin,LOW);
    } else {
      digitalWrite(ledPin, LOW);
      Serial.println("LED éteinte");
    }
  }
}
